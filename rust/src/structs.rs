pub fn test() {
    #[derive(Debug)]
    struct User {
        name: String,
        email: String,
    }
    {
        let name = String::from("Nan");
        let email = String::from("foo@bar.com");
        let u = &mut User { name, email };
        u.name = String::from("Sunny");
        println!("{:?}", u);
    }

    {
        let name = String::from("Nan");
        let email = String::from("foo@bar.com");
        let u = User { name, email };
        let u2 = User {
            email: String::from("bar@baz.com"),
            ..u
        };
        println!("{:?}", u2);
    }

    // you can also define struct without named parameters
    #[derive(Debug)]
    struct Color(i32, i32, i32);
    #[derive(Debug)]
    struct Point(i32, i32, i32);
    let black = Color(0, 0, 0);
    let origin = Point(0, 0, 0);
    println!("{:?}, {:?}", black, origin);

    #[derive(Debug)]
    struct Rectangle {
        width: u32,
        height: u32,
    }

    // define a method for the type
    impl Rectangle {
        fn area(&self) -> u32 {
            self.width * self.height
        }
        fn can_hold(&self, other: &Rectangle) -> bool {
            self.width > other.width && self.height > other.height
        }
        // Associated Functions (static function) that does not take &self
        fn square(size: u32) -> Rectangle {
            Rectangle {
                width: size,
                height: size,
            }
        }
    }

    let rect1 = Rectangle {
        width: 30,
        height: 50,
    };
    println!(
        "The area of the rectangle is {} square pixels.",
        rect1.area()
    );

    let rect1 = Rectangle {
        width: 30,
        height: 50,
    };
    let rect2 = Rectangle {
        width: 10,
        height: 40,
    };
    let rect3 = Rectangle {
        width: 60,
        height: 45,
    };

    println!("Can rect1 hold rect2? {}", rect1.can_hold(&rect2));
    println!("Can rect1 hold rect3? {}", rect1.can_hold(&rect3));

    // Associated function is called using :: operator
    let sq = Rectangle::square(3);
    println!("{:?}", sq);
}
