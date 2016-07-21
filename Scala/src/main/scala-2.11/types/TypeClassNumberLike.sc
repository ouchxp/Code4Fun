import annotation.implicitNotFound
@implicitNotFound("No member of type class NumberLike in scope for ${T}")
trait NumberLike[T] {
  def plus(x: T, y: T): T
}
implicit object NumberLikeDouble extends NumberLike[Double] {
  def plus(x: Double, y: Double): Double = x + y
}
implicit object NumberLikeLong extends NumberLike[Long] {
  def plus(x: Long, y: Long): Long = x + y
}
implicit val bigDecNumLike = new NumberLike[BigDecimal] {
  def plus(x: BigDecimal, y: BigDecimal): BigDecimal = x + y
}

def sum[T: NumberLike](xs: List[T]):T = xs.reduce(implicitly[NumberLike[T]].plus)
sum(List(1.0d,2.0d,3.0d))
sum(List(1L,2L,3L))
sum(List(BigDecimal(1),BigDecimal(2),BigDecimal(3)))

@implicitNotFound("No member of type class StringLike in scope for ${T}")
trait StringLike[T] {
  def asStr(x: T): String
}
implicit object StringLikeLong extends StringLike[Long] {
  def asStr(x: Long): String = x.toString
}

trait Sumable[T] {
  def ssum(): T
  def print(): Unit
}

// Multi context bound example
implicit def numLikeListToSumable[T : NumberLike : StringLike](xs: List[T]): Sumable[T] = new Sumable[T]{
  override def ssum(): T = xs.reduce(implicitly[NumberLike[T]].plus)
  override def print():Unit = xs.foreach(x => println(implicitly[StringLike[T]].asStr(x)))
}
List(1L,2L,3L).ssum()
List(1L,2L,3L).print()
