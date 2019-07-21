use std::ops::Sub;

pub fn test() {

    // heap variable clone
    let s1 = String::from("hello");
    let s2 = s1;
    println!("s2 = {}", s2);
    // print s1 does not work since "move" occurred
    // println!("s1 = {}", s1);

    // heap variable clone
    let s1 = String::from("hello");
    let mut s2 = s1.clone();
    s2.push_str(" world");
    println!("s1 = {}, s2 = {}", s1, s2);


    // stack only variable
    let x = 5;
    let y = x;
    println!("x = {}, y = {}", x, y);
}