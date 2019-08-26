pub fn test() {
    {
        let b = Box::new(5);
        println!("b = {}", b);
    }
    {
        #[derive(Debug)]
        enum List {
            Cons(i32, Box<List>),
            Nil,
        }
        use List::{Cons, Nil};
        let list = Cons(1, Box::new(Cons(2, Box::new(Cons(3, Box::new(Nil))))));
        println!("{:?}", list);

        // Deref Coercion
        fn f(x: &i32) {
            println!("{:?}", x);
        }
        let b = Box::new(5);
        f(&b);
    }
}
