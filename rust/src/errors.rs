use std::fs::File;
use std::io;
use std::io::Read;

pub fn test() {
    {
        let f = File::open("src/main.rs");
        let mut f = match f {
            Ok(file) => file,
            Err(error) => panic!("Problem opening the file: {:?}", error),
        };
        let mut contents = String::new();
        f.read_to_string(&mut contents).expect("read failed");
        println!("{}", contents);
    }

    {
        let f = File::open("src/main.rs");
        let mut f = f.unwrap();
        let mut contents = String::new();
        f.read_to_string(&mut contents).expect("read failed");
        println!("{}", contents);
    }

    {
        println!("{}", propagate().unwrap());
    }
}

fn propagate() -> Result<String, io::Error> {
    // question mark can be used to propagate errors
    let mut contents = String::new();
    File::open("src/main.rs")?.read_to_string(&mut contents)?;
    Ok(contents)
}
