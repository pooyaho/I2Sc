package ir.phsys.xview.model.datamodel

import ir.phsys.xview.model.BaseModel
import scala.beans.BeanProperty

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/1/14
 *         Time: 11:18 AM
 */
class Restriction(@BeanProperty var attributes: Map[String, String] = Map.empty[String, String],
                  var values: Map[String, String] = Map.empty[String, String])
  extends BaseModel


//class Test(a: Int, b: String) {
////  if(b=="equal"){
////    throw new Exception
////  }
//  def this() = this(1, "")
//}


//object Test {
//  def apply(a: Int, b: String) = new Test(a, b)
//
//  def apply(a: Int) = new Test(a, "")
//
//  def apply(b: String) = new Test(0, b)
//
//  def apply() = new Test(0, "")
//
//  //  def unapply() = {}
//}
//
//object TestTestClass {
//  val t = Test()
//  val t1 = Test(1)
//  val t2 = Test("P")
//
//  val Pattern = "(\\w+)\\s(\\w+)".r
//
//  "Mohamad mir" match {
//    case Pattern(x, y) => println(x)
//    case Pattern(x) => println(x)
//  }
//
//}