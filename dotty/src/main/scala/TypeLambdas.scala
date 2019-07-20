object TypeLambdas extends App {
  // Same as `type F[X] = (X, X)`
  type F = [X] =>> (X, X)
  val list: List[F[Int]] = (1, 1) :: (2, 3) :: Nil
  println(list)
}