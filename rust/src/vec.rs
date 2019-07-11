pub fn vec() {
    let v = vec!(1, 2, 3);
    let m1: Vec<i32> = v.iter().map(|x| 1 + *x).collect();

    println!("{:?}", m1);
}