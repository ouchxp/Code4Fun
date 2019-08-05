use std::fmt::Debug;

pub fn test() {
    println!("{}", "traits");

    trait Summary {
        fn summarize(&self) -> String;
    }
    #[derive(Debug)]
    struct NewsArticle {
        pub headline: String,
        pub location: String,
        pub author: String,
        pub content: String,
    }
    impl Summary for NewsArticle {
        fn summarize(&self) -> String {
            format!("{}, by {} ({})", self.headline, self.author, self.location)
        }
    }
    #[derive(Debug)]
    struct Tweet {
        pub username: String,
        pub content: String,
        pub reply: bool,
        pub retweet: bool,
    }

    impl Summary for Tweet {
        fn summarize(&self) -> String {
            format!("{}: {}", self.username, self.content)
        }
        // default implementation
        // fn summarize(&self) -> String {
        //     String::from("(Read more...)")
        // }
    }

    let tweet = Tweet {
        username: String::from("horse_ebooks"),
        content: String::from("of course, as you probably already know, people"),
        reply: false,
        retweet: false,
    };

    println!("1 new tweet: {}", tweet.summarize());

    let article = NewsArticle {
        headline: String::from("Penguins win the Stanley Cup Championship!"),
        location: String::from("Pittsburgh, PA, USA"),
        author: String::from("Iceburgh"),
        content: String::from(
            "The Pittsburgh Penguins once again are the best
    hockey team in the NHL.",
        ),
    };

    println!("New article available! {}", article.summarize());

    // type bounds
    fn notify<T: Summary>(item: &T) {
        println!("Breaking news! {}", item.summarize());
    }
    notify(&tweet);
    notify(&article);

    // it could also be written like
    fn notify1(item: &impl Summary) {
        println!("Breaking news! {}", item.summarize());
    }
    notify1(&tweet);
    notify1(&article);

    // specifying multiple trait bounds with the + syntax
    fn notify2(item: &(impl Summary + Debug)) {
        println!("Breaking news! {}, {:?}", item.summarize(), item);
    }
    notify2(&tweet);
    notify2(&article);
    // or
    fn notify3<T: Summary + Debug>(item: &T) {
        println!("Breaking news! {}, {:?}", item.summarize(), item);
    }
    notify3(&tweet);
    notify3(&article);

    // implement a largest function works with both char and i32 vector
    {
        fn largest<T: PartialOrd + Copy>(list: &[T]) -> T {
            let mut largest = list[0];
            for &item in list.iter() {
                if item > largest {
                    largest = item;
                }
            }
            largest
        }
        let number_list = vec![34, 50, 25, 100, 65];
        let result = largest(&number_list);
        println!("The largest number is {}", result);
        let char_list = vec!['y', 'm', 'a', 'q'];
        let result = largest(&char_list);
        println!("The largest char is {}", result);
    }

    {
        use std::fmt::Display;

        struct Pair<T> {
            x: T,
            y: T,
        }

        impl<T> Pair<T> {
            fn new(x: T, y: T) -> Self {
                Self { x, y }
            }
        }
        // conditional implementation based on trait bound
        impl<T: Display + PartialOrd> Pair<T> {
            fn cmp_display(&self) {
                if self.x >= self.y {
                    println!("The largest member is x = {}", self.x);
                } else {
                    println!("The largest member is y = {}", self.y);
                }
            }
        }
        let p = Pair::new(1, 2);
        p.cmp_display();
    }
}
