mod array;
mod closure;
mod enums;
mod errors;
mod generics;
mod guessing;
mod hashmap;
mod iterators;
mod lifetime;
mod module;
mod ownership;
mod reference;
mod slice;
mod string;
mod structs;
mod tests;
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
        traits::test();
        lifetime::test();
        tests::return_10();
        closure::test();
    }
    iterators::test();
}
