import scalaz.std.either._
import scalaz.syntax.apply._
import scalaz.{Category => _, _}

case class User(name: String)

case class Category(user: User, parent: Category, name: String, desc: String)

def nonNull[A](a: A, msg: String): Either[String, A] = Option(a).toRight(msg)
def buildCategory(user: User, parent: Category, name: String, desc: String) = (
  nonNull(user, "User is mandatory for a normal category")
    |@| nonNull(parent, "Parent category is mandatory for a normal category")
    |@| nonNull(name, "Name is mandatory for a normal category")
    |@| nonNull(desc, "Description is mandatory for a normal category")
  ) (Category.apply) // this is just intellij's error
val result1 = buildCategory(User("mary"), null, null, "Some category.")

