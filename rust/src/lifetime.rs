use std::fmt::Display;

pub fn test() {
    {
        let string1 = String::from("abcd");
        let string2 = "xyz";
        let result = longest(string1.as_str(), string2);
        println!("The longest string is {}", result);
    }

    {
        let string1 = String::from("abcd");
        {
            let string2 = String::from("xyz");
            let result = longest(string1.as_str(), string2.as_str());
            println!("The longest string is {}", result);
        }
    }
    {
        // this does not compile since result cannot outlive string2
        // let string1 = String::from("long string is long");
        // let result;
        // {
        //     let string2 = String::from("xyz");
        //     result = longest(string1.as_str(), string2.as_str());
        // }
        // println!("The longest string is {}", result);
    }
    {
        // this works since we know that string2 will not be returned
        // so that the lifetime of return value only depending on string1
        fn longest<'a>(x: &'a str, y: &str) -> &'a str {
            println!("{}", y);
            x
        }
        let string1 = String::from("long string is long");
        let result;
        {
            let string2 = String::from("xyz");
            result = longest(string1.as_str(), string2.as_str());
        }
        println!("The longest string is {}", result);
    }

    {
        // struct need to specify lifetime when it holds reference
        #[derive(Debug)]
        struct ImportantExcerpt<'a> {
            part: &'a str,
        }

        {
            let novel = String::from("Call me Ishmael. Some years ago...");
            let first_sentence = novel.split('.').next().expect("Could not find a '.'");
            let i = ImportantExcerpt {
                part: first_sentence,
            };
            println!("{:?}", i);
        }

        {
            // this does not work, since normally struts that holds the reference needs to be
            // equal or short lived than the value it referencing
            // let i;
            // {
            //     let novel = String::from("Call me Ishmael. Some years ago...");
            //     let first_sentence = novel.split('.').next().expect("Could not find a '.'");
            //     i = ImportantExcerpt {
            //         part: first_sentence,
            //     };
            // }
            // println!("{:?}", i);
        }

        //static life time is always valid
        let s: &'static str = "I have a static lifetime.";
        println!("{}", s);

        // all things in one function
        let string1 = String::from("abcd");
        let string2 = "xyz";
        fn longest_with_an_announcement<'a, T>(x: &'a str, y: &'a str, ann: T) -> &'a str
        where
            T: Display,
        {
            println!("Announcement! {}", ann);
            if x.len() > y.len() {
                x
            } else {
                y
            }
        }
        println!(
            "{}",
            longest_with_an_announcement(string1.as_str(), string2, "hello")
        );
    }
}
// this does not work since we did not specify lifetime for the return value
// because the return value could be either string1 or string2
// rust's compiler cannot figure out what would be the lifetime of the returned reference
//
// fn longest(x: &str, y: &str) -> &str {
//     if x.len() > y.len() {
//         x
//     } else {
//         y
//     }
// }

// The function signature now tells Rust that for some lifetime 'a,
// the function takes two parameters, both of which are string slices
// that live at least as long as lifetime 'a. The function signature also tells
// Rust that the string slice returned from the function will live at least as long as lifetime 'a
fn longest<'a>(x: &'a str, y: &'a str) -> &'a str {
    if x.len() > y.len() {
        x
    } else {
        y
    }
}
