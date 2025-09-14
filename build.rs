use std::fs;
use std::path::{Path, PathBuf};
use dirs::document_dir; // add dirs = "5" to [build-dependencies] in Cargo.toml
use toml::Value;

fn main() {
    // read modules.toml
    let toml_str = fs::read_to_string("modules.toml").expect("Cannot read modules.toml");
    let config: Value = toml::from_str(&toml_str).expect("Invalid TOML");

    // generated modules directory
    let modules_dir = Path::new("src/generated");
    if modules_dir.exists() {
        fs::remove_dir_all(modules_dir).unwrap();
    }
    fs::create_dir_all(modules_dir).unwrap();

    let modules_base: PathBuf = document_dir()
        .expect("Could not find Documents folder")
        .join("QuarkModules");

    let mut mod_decls = String::new();
    let mut init_calls = Vec::new();

    // generate module declarations and init calls in TOML order
    if let Some(mods) = config.get("modules").and_then(|m| m.as_table()) {
        // Iterate over the table in insertion order
        for (key, val) in mods.iter() {
            if let Some(table) = val.as_table() {
                let name = table.get("name").and_then(|v| v.as_str()).unwrap();
                let version = table.get("version").and_then(|v| v.as_str()).unwrap();
                let safe_version = version.replace('.', "_");
                let file_name = format!("{}_{}", name, safe_version);

                let src_path = modules_base.join(&file_name);
                let dest_path = modules_dir.join(&file_name);

                // create symlink
                #[cfg(unix)]
                std::os::unix::fs::symlink(&src_path, &dest_path).unwrap();
                #[cfg(windows)]
                std::os::windows::fs::symlink_dir(&src_path, &dest_path).unwrap();
                #[cfg(mac)]
                std::os::mac::fs::symlink_dir(&src_path, &dest_path).unwrap();

                // module declarations
                mod_decls.push_str(&format!("pub mod {0};\npub use {0} as {1};\n", file_name, name));

                // collect init calls in a vector
                init_calls.push(format!("    crate::generated::{}::init();", name));
            }
        }
    }

    // join the init calls in order
    let init_calls_str = init_calls.join("\n");

    // generate mod.rs
    let full_mod_rs = format!(
        "{mod_decls}\n\
        pub fn init_modules() {{\n\
        {init_calls}\n\
        }}",
        mod_decls = mod_decls,
        init_calls = init_calls_str
    );
    fs::write(modules_dir.join("mod.rs"), full_mod_rs).unwrap();

    // ensure `mod generated;` is in main.rs
    inject_mod_line("src/main.rs");

    println!("cargo:rerun-if-changed=src/modules.toml");
}

fn inject_mod_line(file_path: &str) {
    let path = Path::new(file_path);
    let content = fs::read_to_string(path).expect("Failed to read source file");

    if !content.contains("mod generated;") {
        let new_content = format!("mod generated;\n{}", content);
        fs::write(path, new_content).expect("Failed to write updated source file");
    }
}