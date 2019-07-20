trait Entry { type Key; val key: Key }
def extractKey(e: Entry): e.Key = e.key          // a dependent method
// ^ scala can do this above, note the Key type of Entry is not implemented
// so the output type of extractKey could vary depending on the parameter e
// e.g.
val strKey: String = extractKey(new Entry() {
  type Key = String
  val key = "abc"
})
val intKey: Int = extractKey(new Entry() {
  type Key = Int
  val key = 10
})

// scala cannot do the line below, since scala function type does not take parameter
// In Dotty this is now possible. The type of the extractor value is (e: Entry) => e.Key
val extractor: (e: Entry) => e.Key = extractKey  // a dependent function value
//            ║   ⇓ ⇓ ⇓ ⇓ ⇓ ⇓ ⇓   ║
//            ║     Dependent     ║
//            ║   Function Type   ║
//            ╚═══════════════════╝


val strKey1: String = extractor(new Entry() {
  type Key = String
  val key = "abc"
})
val intKey1: Int = extractor(new Entry() {
  type Key = Int
  val key = 10
})


// This is NOT equivalent as the one below
// val extractor1: (e: Entry) => Entry#Key = extractKey
// since this returns a generalized Entry#Key type and does not work wit specific implementation
// e.g.
// this not compile because of type mismatch, intKey2 expect a Int value
// yet extractor1 only gives you Entry#Key type and it is useless (no specific type info)
// val intKey2: Int = extractor1(new Entry() {
//   type Key = Int
//   val key = 10
// }) 

object DependentFunctionType extends App {
  println(strKey)
  println(intKey)
  
  println(strKey1)
  println(intKey1)
}
