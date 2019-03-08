package exercises

/**
  * In Scala object is how you define a singleton.
  * Class is used consistently as: A template to create an object
  * Object is an instance of a class, or a singleton object.
  * Note: object in Scala is not the same as a static class in java.
  */
object RecursionRefresher {

  /**
    * To get started we're going to do a refresher on recursion.
    * Since Functional programing doesn't have variables, just values,
    * Loops are not a useful control structure.
    * With out variables the loop predicate can never change from false to true.
    */

  /**
    * This method returns n!
    * n! = n * (n-1)! for all integers >= 0
    * 0! = 1
    * Checking for negative numbers is not necessary
    * @param n
    * @return
    */
  def factorial(n: Int): Long = ??? // ??? means undefined.  This will compile but throw an error when called.  Very useful for TDD.

  /**
    * Sums all of the numbers in the list.
    *
    * List(1,2,3) = 6
    */
  def sumList(list: List[Int]): Int = ???

  /**
    * Takes
    * @param list
    * @return
    */
  def vectorScaling(list: List[Int], f: Double): List[Int] = ???

  def geometricSum(a: Int, r: Int, n: Int): Double = ???

  /////////////////////
  // BONUS QUESTIONS //
  /////////////////////

  def isPalindrome(list: List[Int]): Boolean = ???
  /**
    * Return the number of different ways the total can be reached
    * using the given denominations.
    * The order the denominations are used should not matter
    * The order of the denominations in the list should not matter
    * changeCombinations(19, List(1, 5, 10)) = 6
    * Ex:
    * 1x10 1x5 4x1
    * 1x10 9x1
    * 3x5 4x1
    * 2x5 9x1
    * 1x5 14x1
    * 19x1
    */
  def changeCombinations(total: Int, denominations: List[Int]): Int = ???

}
