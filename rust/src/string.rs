pub fn test() {
    {
        let data = "initial contents";
        let s = String::from(data);
        let s1 = data.to_string();
        println!("{}", s == s1);
    }
    {
        let s1 = String::from("tic");
        let s2 = String::from("tac");
        let s3 = String::from("toe");
        let s = s1 + "-" + &s2 + "-" + &s3;
        // Add operator takes ownership of s1, so s1 cannot be used later
        println!("{}", s);
        // this does not compile, since s1 has moved
        // println!("{}", s1);
        println!("{} {}", s2, s3);
    }

    {
        let s1 = String::from("tic");
        let s2 = String::from("tac");
        let s3 = String::from("toe");
        // format does not take ownership
        let s = format!("{}-{}-{}", s1, s2, s3);
        println!("{}", s);
        println!("{} {} {}", s1, s2, s3);
    }

    {
        // len() of string returns number of bytes, not number of unicode chars
        let s1 = String::from("你好");
        println!("{}", s1.len());

        // to get number of chars, use s1.chars().count()
        println!("{}", s1.chars().count());
        let x: Vec<_> = s1.chars().collect();
        println!("{:?}", x);
    }

    {
        // when creating slice from string one way is per bytes
        let s1 = String::from("你好");
        println!("{}", &s1[..3]);
        // this will panic
        // println!("{}", &s1[..2]);

        // we can do this to get a specific char
        let x: Vec<_> = s1.chars().collect();
        println!("{:?}", x[0]);
    }
    {
        let mut s1 = String::from("Hello ");
        // the line below does not work because it is str type, str is immutable
        // let mut s1 = "Hello";
        println!("length {}", s1.len());
        // append a char
        s1.push('W');
        println!("s1: {}", s1);
        // append a string
        s1.push_str("orld");
        println!("s1: {}", s1);
        println!("Capacity: {}", s1.capacity());
        println!("Is Empty: {}", s1.is_empty());
        println!("Contains 'World': {}", s1.contains("World"));

        // loop words
        for word in s1.split_whitespace() {
            println!("{}", word);
        }

        // create string with capacity
        let mut s = String::with_capacity(10);
        s.push_str("0123456789");
        assert_eq!(s.capacity(), 10);
        s.push_str("a");
        // capacity doubled like ArrayList in Java
        assert_eq!(s.capacity(), 20);
    }
}
