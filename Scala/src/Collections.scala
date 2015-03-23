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
  val stream = 1 #:: {println("get2");2} #:: Stream.empty
  println(stream(0))
  println("nothing")
  println(stream(1))
  
  println(Seq(1,2,3))//Seq default is List
  println(IndexedSeq(1,2,3))//IndexedSeq default is Vector
  println(SortedSet(1,2,3))//TreeSet (red black tree)
}