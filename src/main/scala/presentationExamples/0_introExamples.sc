import java.util.concurrent._

import scala.collection.parallel.ParSeq
import scala.collection.mutable
import com.github.nscala_time.time.Imports._

def functional(in: List[Int]): List[Int] = {
  if(in.length == 1 || in.isEmpty) in
  else {
    val p = in.head
    val (l, h) = in
      .tail
      .partition(_ < p)

    functional(l) ::: List(p) ::: functional(h)
  }
}

def parallelFunctional(in: ParSeq[Int]): ParSeq[Int] = {
  if(in.length == 1 || in.isEmpty) in
  else {
    val p = in.head
    val (l, h) = in
      .tail
      .partition(_ < p)

    parallelFunctional(l.par) ++ List(p) ++ parallelFunctional(h.par)
  }
}

def imperative(list: Array[Int]): Unit = {
  def loop(s: Int, e: Int): Unit = {
    val p = list((s + e) / 2)
    var start = s
    var end = e

    if (e - s <= 1)
      return

    while (start < end)
      if (list(start) < p) {
        start = start + 1
      } else if (list(end) > p) {
        end = end - 1
      } else {
        val t = list(start)
        list(start) = list(end)
        list(end) = t
        end = end - 1
        start = start + 1
      }

    loop(s, start - 1)
    loop(start + 1, end)
  }

  loop(0, list.length - 1)
}

case class Employee(name: String, salary: Int, position: String, active: Boolean, timestamp: DateTime)

def loadInSection(section: Array[Employee]): Iterable[Employee] = {

  val threadPool = new ForkJoinPool()
  val output = mutable.HashMap[String, Employee]()

  for(
    i <- section.indices
  ) {

    val action = new RecursiveAction {
      override def compute(): Unit = {
        val cur = section(i)
        if (!output.contains(cur.name)
          || (output.contains(cur.name) && output(cur.name).timestamp < cur.timestamp)) {
          output += ((cur.name, cur))
        }
      }
    }

    threadPool.execute(action)
  }

  threadPool.awaitQuiescence(5, TimeUnit.MINUTES)

  output.values
}

def loadInFunctional(input: List[Employee]): List[Employee] = {

  input
    .par
    .groupBy(_.name)
    .mapValues(_.maxBy(_.timestamp))
    .values
    .seq
    .toList
}

val employees = Array(
  Employee("A", 1, "Test Dummy", true, DateTime.parse("2019-01-01")),
  Employee("A", 2, "Test Dummy", true, DateTime.parse("2019-01-02")),
  Employee("A", 3, "Senior Test Dummy", true, DateTime.parse("2019-01-03")),
  Employee("B", 1, "Test Dummy", true, DateTime.parse("2019-01-01")),
  Employee("B", 2, "Test Dummy", true, DateTime.parse("2019-01-02")),
  Employee("B", 3, "Senior Test Dummy", true, DateTime.parse("2019-01-03")),
  Employee("C", 1, "Test Dummy", true, DateTime.parse("2019-01-01")),
  Employee("C", 2, "Test Dummy", true, DateTime.parse("2019-01-02")),
  Employee("C", 3, "Senior Test Dummy", true, DateTime.parse("2019-01-03")),
  Employee("D", 1, "Test Dummy", true, DateTime.parse("2019-01-01")),
  Employee("D", 2, "Test Dummy", true, DateTime.parse("2019-01-02")),
  Employee("D", 3, "Senior Test Dummy", true, DateTime.parse("2019-01-03")),
)

loadInSection(employees)