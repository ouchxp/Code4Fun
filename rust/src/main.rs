mod array;
mod box_type;
mod cli;
mod closure;
mod enums;
mod errors;
mod generics;
mod guessing;
mod hashmap;
mod iterators;
mod json;
mod lifetime;
mod loops;
mod module;
mod ownership;
mod pointer_ref;
mod print;
mod reference;
mod slice;
mod string;
mod structs;
mod tests;
mod traits;
mod tuple;
mod typeclass;
mod types;
mod vars;
mod vec;

fn main() {
    if false {
        guessing::guessing();
        typeclass::test();
        ownership::test();
        reference::test();
        slice::test();
        structs::test();
        enums::test();
        module::test();
        vec::vec();
        hashmap::test();
        errors::test();
        generics::test();
        traits::test();
        lifetime::test();
        tests::return_10();
        closure::test();
        iterators::test();
        box_type::test();
        json::test();
        print::print();
        vars::run();
        types::run();
        string::test();
        tuple::run();
        array::array();
        vec::vec();
        loops::run();

        pointer_ref::run();
    }
    cli::run();
}
