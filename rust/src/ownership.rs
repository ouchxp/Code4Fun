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

    // explicitly drop value
    let d = String::from("hello");
    std::mem::drop(d);
    // println!("x = {}", d); // does not work since it has been dropped

    // stack only variable
    let x = 5;
    let y = x;
    println!("x = {}, y = {}", x, y);

    // function call takes ownership
    takes_ownership(s2);
    // println!("{}", s2); // does not work, since s2 been moved to the function

    // function call can take ownership and then give it back
    let mut s3 = String::from("abc");
    s3 = takes_and_gives_back(s3);
    println!("s3 = {}", s3);

    // if you really need the value passed to the function
    let s1 = String::from("hello");
    let (s2, len) = calculate_length(s1);
    println!("The length of '{}' is {}.", s2, len);
}

fn takes_ownership(some_string: String) { // some_string comes into scope
    println!("{}", some_string);
} // Here, some_string goes out of scope and `drop` is called. The backing memory is freed.

// takes_and_gives_back will take a String and return one
fn takes_and_gives_back(a_string: String) -> String { // a_string comes into scope
    a_string  // a_string is returned and moves out to the calling function
}

fn calculate_length(s: String) -> (String, usize) {
    let length = s.len();
    (s, length)
}