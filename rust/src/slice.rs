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
