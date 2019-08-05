pub fn test() {
    {
        #[derive(Debug)]
        struct Point<T> {
            x: T,
            y: T,
        }
        let integer = Point { x: 5, y: 10 };
        let float = Point { x: 1.0, y: 4.0 };
        println!("{:?}, {:?}", integer, float);
        // does not work since you cannot mix types
        // let wont_work = Point { x: 5, y: 4.0 };
    }

    {
        #[derive(Debug)]
        struct Point<T, U> {
            x: T,
            y: U,
        }
        let integer = Point { x: 5, y: 10 };
        let float = Point { x: 1.0, y: 4.0 };
        println!("{:?}, {:?}", integer, float);
        let works_now = Point { x: 5, y: 4.0 };
        println!("{:?}", works_now);
    }

    {
        #[derive(Debug)]
        struct Point<T> {
            x: T,
            y: T,
        }
        // generic in method definition
        impl<T> Point<T> {
            fn x(&self) -> &T {
                &self.x
            }
        }
        // implement method only for specific generic type
        impl Point<f64> {
            fn distance_from_origin(&self) -> f64 {
                (self.x.powi(2) + self.y.powi(2)).sqrt()
            }
        }

        let integer = Point { x: 5, y: 10 };
        println!("{}", integer.x());
        // does not work since distance_from_origin was not implemented for i32
        // integer.distance_from_origin();
        let float = Point { x: 1.0, y: 1.0 };
        println!("{}", float.distance_from_origin());
    }

    {
        struct Point<T, U> {
            x: T,
            y: U,
        }
        // methods takes extra generic types
        impl<T, U> Point<T, U> {
            fn mixup<V, W>(self, other: Point<V, W>) -> Point<T, W> {
                Point {
                    x: self.x,
                    y: other.y,
                }
            }
        }
        let p1 = Point { x: 5, y: 10.4 };
        let p2 = Point { x: "Hello", y: 'c' };
        let p3 = p1.mixup(p2);
        println!("p3.x = {}, p3.y = {}", p3.x, p3.y);
    }
}
