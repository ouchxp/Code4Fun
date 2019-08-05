pub fn return_10() -> i32 {
    10
}
#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn this_test_will_pass() {
        let value = return_10();
        assert_eq!(10, value);
    }

    #[test]
    fn this_test_will_fail() {
        let value = return_10();
        assert_eq!(5, value);
    }
}
