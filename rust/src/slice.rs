use std::iter::FromIterator;

pub fn test() {
    let hw = String::from("hello world");
    let index = first_word(&hw);

    let first_world = hw.chars().into_iter().take_while(|c| *c != ' ').into_iter();
    let s1 = String::from_iter(first_world);
    println!("{}", s1);
    println!("{}", index);

    let hello = &hw[..5]; // [0..5]
    let world = &hw[6..]; // [6..11]
    println!("{}, {}", hello, world);

    // slice with mut string
    let mut s1 = String::from("hello world");
    let hello = &mut s1[..5];
    println!("{}", hello);
    // this does not work since s1 cannot be borrowed as both mutable an immutable
    // or if we borrow it as mutable twice
    // s1.insert(0, '?');
    // println!("{}", hello);
}
fn first_word(s: &String) -> usize {
    let bytes = s.as_bytes();
    for (i, &item) in bytes.iter().enumerate() {
        if item == b' ' {
            return i;
        }
    }
    s.len()
}
