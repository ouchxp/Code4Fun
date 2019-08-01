use std::ops::Add;

struct A {
    val: i32,
}

pub fn test() {
    let x = add3(10, 1, 2);
    println!("{}", x);

    let x = add3(A { val: 10 }, A { val: 1 }, A { val: 2 });
    println!("{}", x.val);
}

fn add3<T: Add<T, Output = T>>(a: T, b: T, c: T) -> T {
    return a + b + c;
}

impl Add<A> for A {
    type Output = A;

    fn add(self, rhs: Self) -> Self::Output {
        A {
            val: self.val + rhs.val,
        }
    }
}
