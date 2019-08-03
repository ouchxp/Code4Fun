use std::collections::HashMap;
pub fn test() {
    {
        let mut scores = HashMap::new();
        scores.insert(String::from("Blue"), 10);
        scores.insert(String::from("Yellow"), 50);
        if let Some(score) = scores.get("Blue") {
            println!("blue score {}", score);
        }
    }

    {
        // we can create map from tuples
        let keys = vec![String::from("Blue"), String::from("Yellow")];
        let values = vec![10, 50];
        // note that the type signature here is HashMap<&String, &i32>
        let scores: HashMap<_, _> = keys.iter().zip(values.iter()).collect();
        println!("{:?}", scores);

        let pairs = vec![(String::from("Blue"), 10), (String::from("Yellow"), 50)];
        let scores: HashMap<_, _> = pairs.iter().cloned().collect();
        println!("{:?}", scores);

        for (key, value) in &scores {
            println!("{}: {}", key, value);
        }
    }

    {
        let mut scores = HashMap::new();
        scores.insert(String::from("Blue"), 10);
        println!("{:?}", scores);
        scores.insert(String::from("Blue"), 25);
        println!("{:?}", scores);
    }

    {
        // insert if not present
        let mut scores = HashMap::new();
        scores.insert(String::from("Blue"), 10);
        scores.entry(String::from("Yellow")).or_insert(50);
        println!("{:?}", scores);
        let blue_score = scores.entry(String::from("Blue")).or_insert(50);
        // however or_insert returns a mut reference to the value, and we can update it directly
        *blue_score = 100;
        println!("{:?}", scores);
    }

    {
        // update values based on existing value
        let text = "hello world wonderful world";
        let mut map = HashMap::new();
        for word in text.split_whitespace() {
            let count = map.entry(word).or_insert(0);
            *count += 1;
        }
        println!("{:?}", map);
    }
}
