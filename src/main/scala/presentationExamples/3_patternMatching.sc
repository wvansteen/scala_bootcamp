import scala.collection.mutable

sealed abstract class Option[+A] {

  def isEmpty: Boolean //abstract method
  def get: A
}
sealed class Some[A](val a: A) extends Option[A] {

  override def isEmpty = false

  override def get = a
}

object None extends Option[Nothing] {

  override def isEmpty = true

  override def get = throw new UnsupportedOperationException("Nothing cannot contain Something")
}

val foo: Option[String] = new Some("foo")

if(!foo.isEmpty) foo.get

//This really isn't better then a null check.

//Enter: Pattern Matching
val i = 100
i match {
  case 0 => "Bust" //Literals can be matched on
  case 100 => "Winner Winner Chicken Dinner"
  case _ => "Lame" // '_' used in pattern matching is the any wild card
}

//So...How do we get our option to work with Pattern Matching?


//First: Any Object can be a function as long as it has an 'apply' method.

object Addition {

  def apply(a: Int, b: Int) = {
    a + b
  }
}

Addition(1, 1)

//Second: Scala doesn't have static methods.
//By creating an object with the same name as a class we can emulate that functionality.
//This is called a Companion Object
class Person(val name: String, val age: Int)

object Person {

  //Allows the object to be treated as a function.
  def apply(name: String, age: Int): Person = {
    new Person(name, age)
  }

  //Third: Any object can have an unapply method, which extracts values
  //from the parameter(s)

  //Allows the object to be deconstructed, and used in pattern matching
  def unapply(p: Person): Option[(String, Int)] = {
    new Some((p.name, p.age))
  }
}

Person("Foo", -1)
Person("Bar", 71)

val p = Person("Foo", 1)

p match {
  case Person("Bar", 71) => "Losing!"  //unapply functions can be matched on.
  case Person("Foo", 1) => "Winning!"  //If the unapply returns Some the match is successful
  case _ => "Still losing!"
}

//Pattern matching is more flexible than demonstrated here

//So... POCOs are really common in all languages.
//In Java equals, getters, hash, to string, and constructors all need to be added by hand.
//In Scala there is the case class.
//Case is for any object or class which is the product of it's fields.
//If all people with the same name and age are the same person, then person is a case class
case class SimplePerson(name: String, age: Int)

val p2 = Person("Foo", 1)

p == p2

val sp = SimplePerson("Test", 1)
val sp2 = SimplePerson("Test", 1)

sp == sp2

sp match {
  case SimplePerson("Test", 1) => "More Winning!"
  case _ => "Pattern missed"
}


//Enums are made up of unique serializable, comparable values, which is perfect for a case class
abstract class Genre
case object Action extends Genre
case object Adventure extends Genre
case object Comedy extends Genre
case object Crime extends Genre
case object Drama extends Genre

def correctGenre(g: Genre) = {
  g match {
    case Action => "Nope"
    case Adventure => "Not this one"
    case Comedy => "This one!"
    case Crime => "Still not this one"
    case Drama => "Nope Nope Nope"
  }
}

correctGenre(Drama)
correctGenre(Comedy)

//Options are made unique by their contents.  Some("Foo") === Some("Foo")
//Another great case class candidate.
abstract class CaseOption[+A]

case class CaseSome[A](a: A) extends CaseOption[A]
case object CaseNone extends CaseOption[Nothing]


val a: CaseOption[String] = CaseSome("Foo")
val b: CaseOption[String] = CaseNone

a match {
  case CaseSome(c) => c
  case CaseNone => "Failure"
}

b match {
  case CaseSome(_) => "Nothing is not Something!"
  case CaseNone => "Found the absence of stuff"
}

//Now that we've covered Abstract Data Types and Case Classes,
//it's time to talk about how immutable collections work.

//Linked List: -> 1 -> 2 -> 3 -> 4 -> 5 -> Null
//Peano List: Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Nil)))))

abstract class List[+A] extends Product with scala.Serializable
case class Cons[A](head: A, tail: List[A]) extends List[A]
case object Nil extends List[Nothing]

object List {
  def apply[A](xs: A*): List[A] = {
    xs.reverse.foldLeft(Nil: List[A])((tail, x) => Cons(x, tail))
  }
}

val arrayList = mutable.Seq[Int](1, 3, 4, 5)
val arrayListB = arrayList
arrayList == arrayListB
arrayList :+ 6
arrayList == arrayListB

val peanoList = List(2, 3, 4, 5)
val peanoList2 = peanoList
peanoList == peanoList2
val peanoList3 = Cons(1, peanoList)
peanoList3 == peanoList

def reverse[A](xs: List[A]): List[A] = {
  def loop(rest: List[A], out: List[A]): List[A] = {
    rest match {
      case Nil => out
      case Cons(a, as) => loop(as, Cons(a, out))
    }
  }
  loop(xs, Nil)
}