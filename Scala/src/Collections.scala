import scala.collection.mutable.HashMap
import scala.collection.mutable.SynchronizedMap
import scala.collection.SortedSet

object Collections extends App {
  /**
   *  Normally class extends from IndexedSeq is optimized for Random access such as Array
   *  class extends from LinerSeq is optimized for liner access (head, tail, like linked list in java)
   *  But Vector class is optimized on both random access and liner access.
   *  Questions:
   *  why? how? vector able to do that.
   *  What's the difference between IndexedSeqOptimized and IndexedSeq ???
   */

  /** Set's apply method is same as contains */
  val set = Set("good", "bad")
  println(set("good"), set("worse"))
  println(set contains "good", set contains "worse")

  val map: PartialFunction[String, String] = Map("good" -> "g", "bad" -> "b")
  println(map.isDefinedAt("worse"))
  val mm = Map("good" -> "g", "bad" -> "b")
  mm.transform((k, v) => { println(k); v })

  val arr = Array("a", "b", "c")
  arr(0) = "g" // another way of using update method

  // Use java concurrentHashMap instead
  new HashMap[String, String] with SynchronizedMap[String, String]

  // Cons operation is call by name so steam will not evaluate until use
  val stream = 1 #:: { println("get2"); 2 } #:: Stream.empty
  println(stream(0))
  println("nothing")
  println(stream(1))

  println(Seq(1, 2, 3)) //Seq default is List
  println(IndexedSeq(1, 2, 3)) //IndexedSeq default is Vector
  println(SortedSet(1, 2, 3)) //TreeSet (red black tree)

  // we can use pattern matching in for expression, to extract the element of case class object 
  case class MyString(s: String) {
    def mkString = "My:" + s
  }
  val myset: Set[MyString] = Set(MyString("good"), MyString("bad"))
  val strSet: Set[String] = for (MyString(x) <- myset) yield x

  /** 2 ways of implementing map function */
  // map function could does not generate a new value directly, but build a channel/pipe that suck the data through it and change it.
  // If the data doesn't change or doesn't matter if changed. (Just like the random number generator)
  // but for mutable array, it will cause confusion, so the map function of array just create the new data directly/immediately
  val mutableArr = Array[String]("1", "2", "3")
  val mappedArr = mutableArr.map { x => x + "x" }
  mutableArr(0) = "100"
  println(mutableArr(0))
  println(mappedArr(0)) // If it was building pipe (does not generate the data immediately), this would be 100x.

}