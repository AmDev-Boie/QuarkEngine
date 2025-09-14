use crate::generated::QMaLib_a_2_0::*;

pub fn init() {
    println!("\ndefinitions:\n");

    let a = Vec4x4_f64::splat(1.0, 4.0, 9.0, 0.0);
    println!("{}", a);
    
    let d = Mat4_f64::new([
        1.0, 0.0, 0.0, 0.0, 
        0.0, 1.0, 0.0, 0.0, 
        0.0, 0.0, 1.0, 0.0, 
        0.0, 0.0, 0.0, 1.0, 
    ]);
    println!("{}", d);

    println!("\nmat * vec:\n");
}
