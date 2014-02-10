import java.io.File
import org.scalatest.FunSuite
import sys.process._
/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 12:29 PM
 */
class TestScalaFeatures extends FunSuite {
  test("Test list minus"){
    val result=List(1,2).diff(List(1))
    println(result)
//    assert(result.equals(List(2)))
  }

  test("Test write to file"){



  }
}

//class Substitution[A, B](val m: Map[A, B])
//  extends Map[A, B] with MapLike[A, B, Substitution] {
//  def get(key: A) = m.get(key)
//
//  def iterator: Iterator[(A, B)] = m.iterator
//
//  def +[B1 >: B](kv: (A, B)) = new Substitution(m + kv.asInstanceOf[(A, B)])
//
//  // Is there a more elegant/correct way to do this?
//  def -(key: A) = new Substitution(m - key)
//
//  override def empty = new Substitution(Map[A, B]())
//}
//
//object Substitution {
//  def empty[A, B] = new Substitution(Map[A, B]())
//
//  def apply[A, B](kvs: (A, B)*): Substitution = new Substitution(Map[A, B](kvs: _*))
//}