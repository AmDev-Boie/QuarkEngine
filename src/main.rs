#![feature(portable_simd)]
#[macro_use]
mod generated;
mod program;

// imports
use serde::Deserialize;
use std::fs;

// TOML structs.
#[derive(Debug, Deserialize)]
pub struct CoreVersioning {
    pub release: String,
    pub version: String,
}
#[derive(Debug, Deserialize)]
pub struct DistVersioning {
    pub release: String,
    pub version: String,
}
#[derive(Debug, Deserialize)]
pub struct DistIdentity {
    pub name: String,
    pub author: String,
}

// TOML modules
#[derive(Debug, Deserialize)]
pub struct DistToml {
    pub dist_identity: DistIdentity,
    pub dist_versioning: DistVersioning,
    pub core_versioning: CoreVersioning,
}

// magical function
fn main() {
    // pre-initialization (loading toml for identity n shii)
    let toml_str = fs::read_to_string("dist.toml").expect("Cannot read modules.toml");
    let config: DistToml = toml::from_str(&toml_str).expect("Invalid TOML");

    // core info
    let core_release = config.core_versioning.release;
    let core_version = config.core_versioning.version;
    
    // dist info
    let dist_release = config.dist_versioning.release;
    let dist_version = config.dist_versioning.version;
    let dist_name = config.dist_identity.name;
    let dist_author = config.dist_identity.author;

    // actual logging starts here
    tclear!();
    println!(" '{} {}{}\x1b[0m:{}{}\x1b[0m' by: {}", dist_name, tcol!(225,50,50), dist_release, tcol!(255,245,175), dist_version, dist_author);
    println!("  Using Core: {}{}\x1b[0m:{}{}\x1b[0m", tcol!(225,50,50), core_release, tcol!(255,245,175), core_version);
    println!("[]========>--------------<========[]");

    // module initializations
    generated::init_modules();

    // program initialization
    crate::program::src::init();
}