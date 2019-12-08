pub fn run() {
    // tuple holds max 12 values
    let person: (&str, &str, i8) = ("John", "Doe", 33);
    println!("{} is from {} and is {}", person.0, person.1, person.2);
}
