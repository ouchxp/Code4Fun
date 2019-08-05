mod array;
mod enums;
mod errors;
mod generics;
mod guessing;
mod hashmap;
mod module;
mod ownership;
mod reference;
mod slice;
mod string;
mod structs;
mod traits;
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
        vec::vec();
        string::test();
        hashmap::test();
        errors::test();
        generics::test();
    }
    traits::test();
}
