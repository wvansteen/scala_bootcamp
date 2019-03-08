//So we have a basic ETL process. As part of this ETL process we have to parse strings into people

//Class definition In the () is the default constructor for this class
//Without val all of the class members will be private
class Person1(val firstName: String, val middleName: String, val lastName: String, val age: Int, pin: String) {

  def this(firstName: String, lastName: String, age: Int, pin: String) = {
    this(firstName: String, "", lastName: String, age: Int, pin: String)
  }
}

def parse(line: String): Person = {

  val properties = line.split(',')

  val firstName = properties.head
  val middleName = properties(1)
  val lastName = properties(2)
  val age = properties(3).toInt
  val pin = properties(4)

  new Person1(firstName, middleName, lastName, age, pin)
}

//But what if the person in the line doesn't have a middle name?

//It's argued that Null is the single most expensive mistake in CS.  It causes billions of dollars of bugs.
// "" Isn't a great way of representing that a person does not have a middle name, neither is null

// A Person is made up of a firstName AND an Optional middleName AND a lastName AND an age AND a pin
// To represent an Optional Middle Name we need a type which can be Something OR Nothing

//Algebraic Data Types is a way to combine data types together with 'Or's and 'And's
//Person is an example of a Conjunction (fancy and), and so are basically all types we're used to
//Option is a Disjunction (fancy or)
//ADTs are always immutable.  Starting from an immutable POJO, ADTs add the ability to link data with OR in addition to AND

//CrazyType = One Int String
//          | Two Double
//          | Three CrazyType
//          | Four
// (One (Int AND String)) OR (Two Double) OR (Three CrazyType) OR Four

//Any Enum is really an Algebraic Data Type.  It's the most basic disjunction.
// Genre = Horror
//       | ScienceFiction
//       | Fantasy
//       | Romance

//sealed is basically the same as final: It means it cannot be extended out side of this class
sealed abstract class Genre {

  //No need to use abstract keywords, just omit the body expression
  def id(): Int
}

//object defines a singleton.
object Horror extends Genre {
  def id(): Int = 0
}
object ScienceFiction extends Genre {
  def id(): Int = 1
}
object Fantasy extends Genre {
  def id(): Int = 2
}
object Romance extends Genre {
  def id(): Int = 3
}

//Genre can be cleaned up by using a construction function
sealed abstract class CleanGenre(val id: Int)
object CleanHorror extends CleanGenre(0)
object CleanScienceFiction extends CleanGenre(1)
object CleanCleanFantasy extends CleanGenre(2)
object CleanRomance extends CleanGenre(3)

// Optional a = Some(a)
//            | None
// Enter: Algebraic Data Types!

//[A] is how generics are specified in Scala.  The + specifies the type variance (Just ignore this for now)
sealed abstract class Option[+A] {

  def isEmpty: Boolean
  def get: A
}
sealed class Some[A](val a: A) extends Option[A] {

  override def isEmpty = false

  override def get = a
}

//Nothing is the bottom type in Scala.  It is a sub-type of all other types.  Nothing is the opposite of Object.
//Everything is a sub-type of object.  Nothing is a sub-type of everything.
object None extends Option[Nothing] {

  override def isEmpty = true

  override def get = throw new UnsupportedOperationException("Nothing cannot contain Something")
}

class Person(val firstName: String, val middleName: Option[String], val lastName: String, val age: Int, val pin: String) {

  def this(firstName: String, lastName: String, age: Int, pin: String) = {
    this(firstName: String, None, lastName: String, age: Int, pin: String)
  }
}


def firstParse(line: String): Person = {

  val properties = line.split(',')

  val firstName = properties.head
  val middleName = if(properties(1) == "") None else new Some(properties(1))
  val lastName = properties(2)
  val age = properties(3).toInt
  val pin = properties(4)

  new Person(firstName, middleName, lastName, age, pin)
}

//But what happens if "Test,,Testerson,foo,1234" is passed in?

//Since this can throw an error, which we don't allow in Functional world,
//we have to find a way to wrap the error and put it in the type
//What we have so far is Option so lets wrap it in an option
def secondParse(line: String): Option[Person] = {

  val properties = line.split(',')

  val firstName = properties.head
  val middleName: Option[String] = if(properties(1) == "") None else new Some(properties(1))
  val lastName = properties(2)
  val pin = properties(4)

  try {
    val age = properties(3).toInt
    new Some(new Person(firstName, middleName, lastName, age, pin))
  } catch {
    case _: Throwable => None
  }
}
//However now we lose the error information
//What we need is a disjunction which can represent an Error OR a Valid Result
// Either a b = Left a
//            | Right b


//So...We're going to implement Either together