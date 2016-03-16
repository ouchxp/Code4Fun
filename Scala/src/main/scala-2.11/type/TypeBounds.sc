trait A
class B extends A
class C extends B
class D extends C

/** Upper Type Bound **/
case class UpperBound[T <: C](height: T)
// val u0:UpperBound[XVal] = null  /** This won't compile **/
val u1: UpperBound[D] = null
//
/** Lower Type Bound **/
case class LowerBound[T >: C](height: T)
val l0: LowerBound[B] = null
val l1: LowerBound[C] = null
//val l2:LowerBound[ZVal] = null /** This won't compile **/
/* type inference to YVal (because covariant), so it can pass */
val l3: LowerBound[C] = LowerBound(new D())
//
/** Range Type Bound **/
case class RangeBound[T >: C <: C](height: T)
//val r1: RangeBound[XVal] = null  /** This won't compile **/
val r2: RangeBound[C] = null
//val r3:LowerBound[ZVal] = null /** This won't compile **/
/* type inference to YVal (because covariant), so it can pass */
val r4: RangeBound[C] = RangeBound(new D())
//
/** Case class parameter is covariant */
val v1: UpperBound[C] = UpperBound(new D())
val v2: LowerBound[C] = LowerBound(new D())
