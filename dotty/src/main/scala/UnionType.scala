def len[T](id: Seq[T] | List[T]) = id.length

def toStr(id: String | List[Int]): String = id match {
  case x: String => x
  case x: List[Int] => x.mkString(",")
}

object UnionType extends App {
  println(len(List(1,2,3)))
  println(len(Seq(1,2,3)))

  println(toStr(List(1,2,3)))
  println(toStr("abc"))
}