pub fn run() {
    let mut count = 0;
    // Infinite loop
    loop {
        count += 1;
        if count == 20 {
            break;
        }
    }
    println!("{}", count);

    // while loop
    while count <= 25 {
        println!("{}", count);
        count += 1;
    }

    // for loop
    let mut sum = 0;
    for x in 0..101 {
        sum += x;
    }
    println!("sum [0, 100]: {}", sum);
}
