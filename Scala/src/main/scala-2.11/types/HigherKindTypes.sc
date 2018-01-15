import scala.language.higherKinds

// original, as above
def tuplef[F[_], A, B](xs: F[A], ys: F[B]): F[(A, B)] = ???
def tupleList[A, B](xs: List[A], ys: List[B]): List[(A, B)] =
  tuplef[List, A, B](xs, ys)

def tupleEither[E, A, B](xs: Either[E, A], ys: Either[E, B]): Either[E, (A, B)] =
  tuplef[Either[E, _], A, B](xs, ys)

