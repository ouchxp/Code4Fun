val (x, y) = (1, 2)
// Actually calling
val Array(a, b) = "1,2".split(",")
val Seq(c, d) = Seq(1, 2)

// Only get first two
//val Array(e, f) = "a b c d e".split(" ")
val Array(e, f, _*) = "a b c d e".split(" ")
