use std::env;
use core::panicking::panic_fmt;

pub fn run() {
    let args: Vec<String> = env::args().collect();
    let command = args[0].clone();
    // cargo run hello
    println!("command: {}", command);
    println!("concat: {}", args.join(","))
}
