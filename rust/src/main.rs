mod array;
mod enums;
mod guessing;
mod module;
mod ownership;
mod reference;
mod slice;
mod structs;
mod typeclass;
mod vec;

fn main() {
    if false {
        guessing::guessing();
        array::array();
        typeclass::test();
        ownership::test();
        reference::test();
        slice::test();
        structs::test();
        enums::test();
        module::test();
    }
    vec::vec();
}
