import scala.language.higherKinds
import scala.reflect.runtime.universe._
trait Bool{
  type If[T,F]
}
trait True extends Bool {
  type If[T,F] = T
}
trait False extends Bool {
  type If[T,F] = F
}

type IfIntElseLong[A <: Bool] = A#If[Int,Long]

// TODO: what is implicitly means
implicitly[ IfIntElseLong[True] =:= Int ]
implicitly[ IfIntElseLong[False] =:= Long ]

//doesn't compile: Cannot prove that
//Types.Booleans.IntOrLong[Types.Booleans.True] =:= Long
//implicitly[ IfIntElseLong[True] =:= Long ]
// similar to type level if:
// def ifIntElseLong(b: Boolean) = if(b) Int.MaxValue else Long.MaxValue

//---------------------------------------------------------------------------
trait Nat {
  type Match[NonZero, IfZero]
}
trait _0 extends Nat {
  type Match[NonZero, IfZero] = IfZero
}
trait Succ[N <: Nat] extends Nat {
  type Match[NonZero, IfZero] = NonZero
}
type _1 = Succ[_0]
type _2 = Succ[_1]
type Is0[A <: Nat] = A#Match[False, True]
implicitly[Is0[_1] =:= False]
implicitly[Is0[_0] =:= True]
/*doesn't compile*/
//implicitly[Is0[_1] =:= True]
typeTag[_2]
