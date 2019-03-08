import scala.annotation.tailrec

/////////////////////////////
// Basic Syntax Deviations //
/////////////////////////////

// const int foo = 1;
val num: Int = 1

val intNum = 1

val doubleNum = 1.0
val floatNum = 1.0f

/*
public static int add(int a, int b) {
  return a + b;
}
 */
def add(a: Int, b: Int): Int = {
  return a + b;
}

/*
  This syntax can be cleaned up a bit.
  ; and return are optional and so should be omitted.
 */
def cleanerAdd(a: Int, b: Int): Int = {
  a + b
}

/*
  In Scala {} are not part of the syntax of a def.
  Everything to the right of the = is an expression.
  {} denotes a block expression, which is used to group multiple expressions together.
  Since cleanestAdd is not made up of multiple expressions {} can be omitted.
 */
def cleanestAdd(a: Int, b: Int): Int = a + b

/*
  public static Function[int, int, int] addf = {
    return (x, y) => x + y;
  }
 */
val addf: (Int, Int) => Int = (a, b) => a + b

/*
  public static isEven(int: i) {
    boolean b;
    if(i % 2 == 0) {
      b = true;
    } else {
      b = false;
    }
    return b;
 */

def isEven(i: Int): Boolean = {
  if (i % 2 == 0) {
    true
  } else {
    false
  }
}

/*
  Since expressions return what they evaluate to
  an if that returns booleans can be simplified to the predicate
  of that if expression.
 */
def cleanIsEven(i: Int): Boolean = i % 2 == 0

/*
  Unlike the previous example this cannot be simplified
 */
def fizzEven(i: Int): String = if (i % 2 == 0) "Fizz" else "Buzz"

//Recap
//Questions?

////////////////////////
// Recursion Examples //
////////////////////////

/*
 F(0) = 0
 F(1) = 1
 F(n) = F(n-1) + F(n-2)

 F(2) = 1 = 1 + 0
 F(3) = 2 = 1 + 1

*/

def badFib(n: Int): Long = {
  if (n == 0) 0
  else if (n == 1) 1
  else badFib(n - 1) + badFib(n - 2)
}

//Starting a 50 execution will take a LONG time
//s"Bad Fib Num 10: ${badFib(10)}"
//s"Bad Fib Num 50: ${badFib(50)}"
//s"Bad Fib Num 100: ${badFib(100)}"

// provide an iterative example
/*
  if(n == 0) {
    return 0;
  else if(n==1) {
    return 1;
  } else {

    int previous = 1
    int cur = 1

    for(i = 2; i < n; i ++) {
      int next = cur + previous
      previous = cur
      cur = next
    }
    return cur
  }
 */
def goodFib(n: Int): Long = {
//call out closure
  @tailrec // Enforces that the corresponding method is tail recursive/
  def loop(i: Int, cur: Long, previous: Long): Long = {
    if (i == n) cur
    else loop(i + 1, cur + previous, cur)
  }

  if (n == 0) 0
  else if (n == 1) 1
  else loop(2, 1, 1)
}

//look into worksheets in vscode and atom.  provide directions for vs code and atom in addition to idea
//Execution will be fast past the point where longs can store the result
//s"Good Fib Num 10: ${goodFib(10)}"
//s"Good Fib Num 100: ${goodFib(100)}"
//s"Good Fib Num 1000: ${goodFib(1000)}"
//s"Good Fib Num 10000: ${goodFib(10000)}"

//List in Scala are immutable and so have different ways of interaction

val a = List(1, 2, 3) //Constructor
a.head //Get the first item: 1
a.tail //Get all items except the first one: List(2, 3)
0 :: a //:: is read 'Cons' it prepends the item to the list: List(0, 1, 2, 3)
a.::(0)
a.isEmpty

//Show example of how this is executed
//Bottom up vs top down
val badFirstNFib: Int => List[Double] =
  n => {
    if (n == 0) {
      List(0)
    } else if (n == 1) {
      1 :: badFirstNFib(n - 1)
    } else {
      val rest = badFirstNFib(n - 1)
      val lessOne = rest.head
      val lessTwo = rest.tail.head
      lessOne + lessTwo :: rest
    }
  }

//Will stack overflow
//badFirstNFib(100000)

val tailFirstNFib: Int => List[Double] =
  n => {

    @tailrec //Does not work with val functions so a def must be used here
    def loop(remaining: Int, previous: List[Double]): List[Double] = {
      val updated = previous.head + previous.tail.head :: previous
      if (remaining == 0) updated
      else loop(remaining - 1, updated)
    }

    val baseFib = List(1.0, 0.0)
    if (n == 0) baseFib.tail
    else if (n == 1) baseFib
    else loop(n - 1, baseFib).reverse
  }

//Will not stack overflow
tailFirstNFib(100000)