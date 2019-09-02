use serde::{Deserialize, Serialize};
use serde_json;

pub fn test() {
    #[derive(Serialize, Deserialize, Debug)]
    struct User {
        name: String,
        email: String,
    }

    fn handle_json(json: &str) -> Result<String, serde_json::error::Error> {
        let data: serde_json::Value = serde_json::from_str(json)?;
        let name = data["name"].as_str().unwrap_or("Nobody");
        return Ok(format!("hello {}", name));
    }
    println!("{:?}", handle_json("{ \"name\": \"nan\"}"));

    fn handle_user(json: &str) -> Result<User, serde_json::error::Error> {
        let user = serde_json::from_str(json)?;
        return Ok(user);
    }

    println!(
        "{:?}",
        handle_user("{ \"name\": \"nan\", \"email\":\"nan@email.com\"}")
    );
}
