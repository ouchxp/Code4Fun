pub fn test() {
    // pass reference, so the function does not take ownership over s1
    let s1 = String::from("hello");
    let len = calculate_length(&s1);
    println!("The length of '{}' is {}.", s1, len);

    // pass mutable reference
    let mut s1 = String::from("hello");
    let len = calculate_length_mut(&mut s1);
    println!("The length of '{}' is {}.", s1, len);
}

fn calculate_length(s: &String) -> usize {
    s.len()
}

fn calculate_length_mut(s: &mut String) -> usize {
    s.push_str(" world");
    s.len()
}