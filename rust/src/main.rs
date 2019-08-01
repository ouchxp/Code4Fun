mod guessing;
mod vec;
mod array;
mod typeclass;
mod ownership;
mod reference;

fn main() {
    if false {
        vec::vec();
        guessing::guessing();
        array::array();
        typeclass::test();
        ownership::test();
    }
    reference::test();

}
