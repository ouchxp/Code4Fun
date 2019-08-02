pub fn test() {
    // pass reference, so the function does not take ownership over s1
    let s1 = String::from("hello");
    let len = calculate_length(&s1); // which is same as `s1.borrow()`
    println!("The length of '{}' is {}.", s1, len);

    // pass mutable reference
    let mut s1 = String::from("hello");
    let len = calculate_length_mut(&mut s1); // which is the same as `s1.borrow_mut()`
    println!("The length of '{}' is {}.", s1, len);

    println!("{}", no_dangle());
}

fn calculate_length(s: &String) -> usize {
    s.len()
}

fn calculate_length_mut(s: &mut String) -> usize {
    s.push_str(" world");
    s.len()
}
/* This does not work
fn dangle() -> &String { // dangle returns a reference to a String
    let s = String::from("hello"); // s is a new String
    &s // we return a reference to the String, s
} // Here, s goes out of scope, and is dropped. Its memory goes away. Danger!
*/

fn no_dangle() -> String {
    let s = String::from("hello");
    s // give out the ownership, so that s is not getting dropped
}

// The Rules of References
//
// At any given time, you can have either one mutable reference or any number of immutable references.
// References must always be valid.
