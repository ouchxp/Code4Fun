mod a {
    pub mod b {
        pub fn test() {
            println!("{}", "mod B");
        }
    }

    pub mod c {
        pub fn test() {
            println!("{}", "mod C");
        }
    }
}

pub fn test() {
    a::b::test();
    a::c::test();
    // if module not pub, it cannot be accessed from outside
}
