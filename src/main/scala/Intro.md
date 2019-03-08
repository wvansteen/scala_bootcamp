# Intro

* Introduce myself
* Purpose and Scope of the bootcamp
    * Functional Programming first; Scala second
* What is Functional Programming
    * Ask the question to the room
    * Be positive and nice when handling the responses
    * Functional Programing is programing with only Mathematical functions.
      This means for each set of parameters there is exactly 1 result.
* Example Functions
    * The first is a function because for each x there is exactly 1 y.
      0 on X is always 0 on Y there is no alternative.
    * The second is not a function because for each x there can be more than 1 y.  
      0 on X can be 4 or -4 on Y.
    * The third is a function is terms of x but not y. 
      2 on X is always 4 on Y, while 4 on Y is either 2 or -2 on X.
#Add programming examples for each
    
* What is Functional Programming Recap
    * These constraints seem impossible, but there are many techniques to get around them
      which can be both easier to use and read.
    * I also want to note that practical programs cannot be written without side-effects (reading from std in, talking to databases, etc)
      Side Effects are possible in Functional programing, they are just confusing and really different, so for this boot-camp we're going to 
      act like they are not possible.
    * These seem like a lot of annoying restrictions, but there are upsides
* Why is Functional Programming useful
    * For distributed programming talk about Spark
    * For concurrent Programming talk about Facebook and Haxl
    * For comprehension, we're going to do an example.
    * For More clear error states: Since we have to have deterministic outputs
      The error must be part of the type, so all consumers have to handle if there was an error.
   #solidify more clear error states language
* Comprehension example
    * Anyone want to take a stab at what this code does?
    * The point isn't that the functional version is smaller, which it is.
      The point is that since there is no state each section can be evaluated by itself    
* Why Scala
    * Most used language Functional Programming language
    * Spark
    * JVM
    * Popularity on the rise 
   #Don't qualify most used, handle it if they ask
