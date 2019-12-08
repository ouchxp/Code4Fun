use std::mem;

pub fn array() {
    let a: [i32; 5] = [1, 2, 3, 4, 5];
    let element = a[1];
    println!("The value of element is: {}", element);

    println!("length {} ", a.len());

    println!("length {} ", a.len());
    // array are stack allocated
    println!("Array occupies {} bytes", mem::size_of_val(&a));

    let slice: &[i32] = &a[0..2];
    println!("Slice: {:?}", slice);
}
