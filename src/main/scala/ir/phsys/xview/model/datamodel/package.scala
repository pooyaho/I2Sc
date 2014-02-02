package ir.phsys.xview.model

import scala.util.parsing.combinator.JavaTokenParsers

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 3:12 PM
 */
package object datamodel {
  //  type Restriction = String => String
  val name = "lambda"


}


object ParseSomething extends JavaTokenParsers {

  //  def getStatementValue ={
  //    case "(" ~ a ~ op ~ b ~ ")" => op match {
  //      case "+" => println(a.asInstanceOf[String].toInt + b.asInstanceOf[String].toInt)
  //      case "*" => println(a.asInstanceOf[String].toInt * b.asInstanceOf[String].toInt)
  //      case "/" => println(a.asInstanceOf[String].toInt / b.asInstanceOf[String].toInt)
  //      case "-" => println(a.asInstanceOf[String].toInt - b.asInstanceOf[String].toInt)
  //      case "^" => println(math.pow(a.asInstanceOf[String].toInt, b.asInstanceOf[String].toInt))
  //    }
  //  }

  def root: Parser[_] = ("(" ~ root ~ operation ~ root ~ ")") | wholeNumber
//  ^^ {
//    case "(" ~ a ~ op ~ b ~ ")" =>
//      a match {
//        case s: String if b.isInstanceOf[String] => op match {
//          case "+" => println(s.toInt + b.asInstanceOf[String].toInt)
//          case "*" => println(s.toInt * b.asInstanceOf[String].toInt)
//          case "/" => println(s.toInt / b.asInstanceOf[String].toInt)
//          case "-" => println(s.toInt - b.asInstanceOf[String].toInt)
//          case "^" => println(math.pow(s.toInt, b.asInstanceOf[String].toInt))
//        }
//        case _ =>
//      }
//    case _ =>
//  }

  def operation = "+" | "*" | "/" | "-" | "^"
}

