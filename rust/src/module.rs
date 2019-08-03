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

    pub mod d {
        pub fn test() {
            println!("{}", "mod D, relative path to C");
            super::c::test();
        }
    }

    pub mod e {
        // e::test is re-exported from C
        pub use super::c::test;
    }
}

pub fn test() {
    a::b::test();
    a::c::test();
    a::d::test();
    // if module not pub, it cannot be accessed from outside

    {
        // use can be anywhere and only works in scope
        use a::b::test;
        test();
    }

    {
        // use with name alias
        use a::b::test as test_b;
        test_b();
    }

    // e::test is re-exported from C
    a::e::test()
}
