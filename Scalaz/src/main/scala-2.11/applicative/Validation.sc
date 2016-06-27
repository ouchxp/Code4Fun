import scalaz.{Category => _, _}
import syntax.apply._, syntax.std.option._

case class User(name: String)

case class Category(user: User, parent: Category, name: String, desc: String)

def nonNull[A](a: A, msg: String): ValidationNel[String, A] =
  Option(a).toSuccess(msg).toValidationNel

def buildCategory(user: User, parent: Category, name: String, desc: String) = (
  nonNull(user, "User is mandatory for a normal category")
    |@| nonNull(parent, "Parent category is mandatory for a normal category")
    |@| nonNull(name, "Name is mandatory for a normal category")
    |@| nonNull(desc, "Description is mandatory for a normal category")
  ) (Category.apply)

val result: ValidationNel[String, Category] =
  buildCategory(User("mary"), null, null, "Some category.")
