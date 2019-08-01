pub fn vec() {
    let v = [1, 2, 3];
    let m1: Vec<i32> = v.iter().map(|x| x + 1).collect();

    println!("{:?}", m1);
}
