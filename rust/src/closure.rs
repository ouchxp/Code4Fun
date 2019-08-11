use std::collections::HashMap;
use std::thread;
use std::time::Duration;

struct Cacher<T: Fn(u32) -> u32> {
    calculation: T,
    values: HashMap<u32, u32>,
}

impl<T: Fn(u32) -> u32> Cacher<T> {
    fn new(calculation: T) -> Cacher<T> {
        Cacher {
            calculation,
            values: HashMap::new(),
        }
    }

    fn value(&mut self, arg: u32) -> u32 {
        let val = self.values.get(&arg);
        match val {
            Some(v) => *v,
            None => {
                let v = (self.calculation)(arg);
                self.values.insert(arg, v);
                v
            }
        }
    }
}

pub fn test() {
    {
        let mut c = Cacher::new(|a| a);
        let v1 = c.value(1);
        let v2 = c.value(2);
        assert_eq!(v1, 1);
        assert_eq!(v2, 2);
    }

    {
        let simulated_user_specified_value = 10;
        let simulated_random_number = 7;
        generate_workout(simulated_user_specified_value, simulated_random_number);
    }

    {
        let example_closure = |x| x;
        let s = example_closure(String::from("hello"));
        println!("{}", s);
        // closure only have one concrete type inferred, after inferred as String -> String
        // it cannot be inferred as i32 -> i32 again
        // let n = example_closure(5);
    }

    {
        let x = 4;
        let equal_to_x = |z| z == x;
        // this does not work since function cannot capture context
        // fn equal_to_x(z: i32) -> bool { z == x }
        let y = 4;
        assert!(equal_to_x(y));
    }

    {
        // closures does not take ownership, the only borrows
        // if we want to force it taking ownership, we can use the `move` keyword
        let x = vec![1, 2, 3];
        let equal_to_x = move |z| z == x;
        // this does not work since x has been taken ownership by the closure
        // println!("can't use x here: {:?}", x);
        let y = vec![1, 2, 3];
        assert!(equal_to_x(y));
    }
}

fn generate_workout(intensity: u32, random_number: u32) {
    let mut expensive_result = Cacher::new(|num| {
        println!("calculating slowly...");
        thread::sleep(Duration::from_secs(2));
        num
    });

    if intensity < 25 {
        println!("Today, do {} pushups!", expensive_result.value(intensity));
        println!("Next, do {} situps!", expensive_result.value(intensity));
    } else {
        if random_number == 3 {
            println!("Take a break today! Remember to stay hydrated!");
        } else {
            println!(
                "Today, run for {} minutes!",
                expensive_result.value(intensity)
            );
        }
    }
}
