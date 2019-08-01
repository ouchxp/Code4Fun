use rand::Rng;
use std::cmp::Ordering;
use std::io;

pub fn guessing() {
    let r = rand::thread_rng().gen_range(10, 100);
    let mut guessed: i32 = 0;
    while guessed != r {
        let mut guessed_str = String::new();
        io::stdin()
            .read_line(&mut guessed_str)
            .expect("Failed to read line");
        guessed = guessed_str.trim().parse().expect("error parsing");
        match guessed.cmp(&r) {
            Ordering::Less => println!("Too small!"),
            Ordering::Greater => println!("Too big!"),
            Ordering::Equal => println!("You win! the number is {}", r),
        }
    }
}
