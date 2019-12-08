pub fn run() {
    let name = "Nan";
    let mut age = 35;
    age = 36;
    println!("My name is {name} and I am {age}", name = name, age = age);

    // const need to be uppercase and have type declared
    const ID: i32 = 001;
    println!("ID: {}", ID);

    // multi assignment
    let (a, b) = ("hello", 42);
    println!("{}, {}", a, b);
}
