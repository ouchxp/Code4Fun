pub fn test() {
    println!("{:?}", "iterators");

    {
        let v1 = vec![1, 2, 3];
        let v1_iter = v1.iter();
        let total: i32 = v1_iter.sum();
        assert_eq!(total, 6);
        // this does not work since sum takes ownership of iterator
        // println!("{:?}", v1_iter);
    }

    {
        struct Counter {
            count: u32,
        }

        impl Counter {
            fn new() -> Counter {
                Counter { count: 0 }
            }
        }

        // implement iterator for counter
        impl Iterator for Counter {
            type Item = u32;
            fn next(&mut self) -> Option<Self::Item> {
                self.count += 1;
                if self.count < 6 {
                    Some(self.count)
                } else {
                    None
                }
            }
        }

        let sum: u32 = Counter::new().filter(|x| x % 3 == 0).sum();
        println!("{:?}", sum);
    }
}
