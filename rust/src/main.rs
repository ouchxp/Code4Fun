mod array;
mod guessing;
mod ownership;
mod reference;
mod slice;
mod typeclass;
mod vec;

fn main() {
    if false {
        vec::vec();
        guessing::guessing();
        array::array();
        typeclass::test();
        ownership::test();
        reference::test();
    }
    slice::test();
}
