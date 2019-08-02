pub fn test() {
    #[derive(Debug)]
    enum Option<T> {
        Some(T),
        None,
    }
    impl<T> Option<T> {
        fn map<U, F: FnOnce(T) -> U>(self, f: F) -> Option<U> {
            match self {
                Option::Some(x) => Option::Some(f(x)),
                Option::None => Option::None,
            }
        }
    }
    let x: Option<i32> = Option::Some(1);
    let x_plus_one = x.map(|x| x + 1);
    println!("{:?}", x_plus_one);

    let x: Option<i32> = Option::None;
    let x_plus_one = x.map(|x| x + 1);
    println!("{:?}", x_plus_one);
}
