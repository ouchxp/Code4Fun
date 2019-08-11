pub fn vec() {
    let v = [1, 2, 3];
    let mut m1: Vec<_> = v.iter().map(|x| x + 1).collect();
    m1.push(5);
    println!("{:?}", m1);

    let third = &m1[3]; // will panic if index out of range
    println!("{}", third);

    // get returns Option<T>
    if let Some(x) = m1.get(3) {
        println!("{}", x);
    }

    // for each
    let v = vec![1, 2, 3];
    for i in &v {
        println!("{}", i);
    }

    // in place update
    let mut v = vec![1, 2, 3];
    for i in &mut v {
        *i += 50;
    }
    println!("{:?}", v);
}
