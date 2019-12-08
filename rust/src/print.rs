pub fn print() {
    println!("{} is a, {} is b", "a", "b");
    // positional binding
    println!("{0} is a, {1} is b", "a", "b");
    // named binding
    println!("{a} is a, {b} is b", a = "a", b = "b");
    // number formatters
    println!("Binary: {:b}, Hex {:x}, Octal: {:o}", 10, 10, 10);
    // debug trait
    println!("{:?}", (1, 2, true, "hello"));
}
