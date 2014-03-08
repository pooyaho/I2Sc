package ir.phsys.xview.xml


import org.scalatest.{BeforeAndAfterAll, FunSuite}
import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.matchers.ShouldMatchers
import akka.testkit.ImplicitSender
import scala.util.parsing.combinator.JavaTokenParsers
import scala.collection.immutable.HashMap
import scala.collection.parallel.immutable.ParMap
import scala.collection.parallel.IterableSplitter


/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/2/14
 *         Time: 6:33 PM
 */
class XmlObjectifierTestSuite(_system: ActorSystem) extends TestKit(
  _system)                                                  with FunSuite with ShouldMatchers with BeforeAndAfterAll with ImplicitSender {

  def this() = this(ActorSystem("PostponeSpec"))

  override def afterAll: Unit = system.shutdown()

  test("test path") {
    //    val objectifier = system.actorOf(Props[XmlObjectifyActor])
    //    objectifier ! Objectify("/home/pooya/projects/I2Sc/src/main/resource/input/simple")
    //
    //    val project = receiveOne(10 seconds)
    //    println(project)
    //    expectMsgType[OperationSucceed](10 seconds)
    val Pattern = "^([a-zA-Z]+)(\\d+)([a-zA-Z]*)$".r


    "mm22" match {
      case Pattern(x, y, z) => println(List(x, y, z))
      case Pattern(x, y) => println("Two occurrence")
      case _ => println("Not matched!")
    }
  }


  def quickSort(lst: List[Int]): List[Int] = lst match {
    case Nil => Nil
    case pivot :: tail =>

      val (smaller, rest) = tail.partition(_ < pivot)
      quickSort(smaller) ::: pivot :: quickSort(rest)
  }

  test("Test parser") {

    //    val result=Parser parseAll(Parser.Grammar.root,new StringReader("class a {\n\n  }"))
    //    println(result)
    val lst = List(List(1, 2, 3), List(4, 3, 2), List(1, 7, 9))
    val sortFlat = lst.flatten.groupBy(identity).map(p => (p._1, p._2.size)).toList.sortWith((a, b) => a._2 > b._2)
                   .map(_._1)
    val result = lst.map(p => p.map(x => (x, sortFlat.indexOf(x))).sortWith((a, b) => a._2 < b._2).map(_._1))

    println(result)
    println(sortFlat)
    println(lst)
  }

  class A {
    def add(a: A) = {
      println("Add called!")
    }
  }

  object A {
    def close() = println("Closed!")
  }

  /*

   */
  object Parser extends JavaTokenParsers {

    object Grammar {
      def root = ("class" ~ ident ~ "{" ~ "}") ^^ {
        case c ~ name ~ "{" ~ "}" => println(s"name of class is $name")
        case _ => println("Not matched")
      }
    }

  }

  def myWhile(f: => Boolean)(a: => Unit): Unit = {
    if (f) {
      a
      myWhile(f)(a)
    }
  }

  def time(a: => Unit): Long = {
    val start = System.nanoTime()
    a
    val end = System.nanoTime()

    end - start
  }

  def using[A <: {def close()}](a: => A)(body: => Unit): Unit = {
    body
    a.close()
  }

  trait Trait {
    def printSomeThing()
  }

  class SomeClass(a: Int) {
    this: Trait =>

    require(a > 0)

    //    def this() = this(1)

    def printContent() = println(a)

    def method1(a: Int = 0) = {
      this.printSomeThing()
    }

    def method1(a: String) = {

    }

  }


  test("Test init") {


  }
}