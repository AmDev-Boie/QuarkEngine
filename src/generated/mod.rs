pub mod QMaLib_a_2_0;
pub use QMaLib_a_2_0 as QMaLib;
pub mod qterm_a_1_0;
pub use qterm_a_1_0 as qterm;

pub fn init_modules() {
    crate::generated::QMaLib::init();
    crate::generated::qterm::init();
}