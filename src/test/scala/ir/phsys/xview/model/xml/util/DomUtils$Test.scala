package ir.phsys.xview.xml.util

import org.scalatest.FunSuite
import ir.phsys.xview.model.datamodel.{Restriction, Element, DataModel}

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 7:53 PM
 */
class DomUtils$Test extends FunSuite {

  import DomUtils._
  import scala.xml._

  val content = XML.loadFile("/home/pooya/projects/I2Sc/src/main/resource/main.xml")

  test("Test converting attributes to map") {
    val map = content.getAttributeAsMap
    println(map)
    assert(map == Map("name" -> "Class1", "domain" -> "com.casp.poo"))
  }

  test("Test xml text and content") {
    (content \ "dataModel").foreach {
      case x => assert(x.label == "dataModel")
    }
    (content \ "elements" \\ "restriction").foreach {
      case x => x.child.foreach {
        case Elem(prefix, label, attribs, scope, Text(text)) => println(label + "->" + text)
        case _ =>
      }
    }
  }

  test("Test data model xml using for") {
    for (domModel <- content \\ "dataModel") {

      //      val name = DomUtils.getAttributeValue(dm, "name")
      //      val domain = DomUtils.getAttributeValue(dm, "domain")
      val dm = new DataModel
      dm.attributes = domModel.getAttributeAsMap
      for (e <- domModel \ "elements"; domElem <- e.child) domElem match {
        case x: Elem =>
          val element = new Element

          element.attributes = x.getAttributeAsMap
          element.elemType = x.label

          for (r <- x \ "restriction"; attrs = r.getAttributeAsMap) {

            val restriction = new Restriction()
            restriction.attributes = attrs
            for (rest <- r.child) rest match {
              case x: Elem =>
                restriction.values ++= Map(rest.label -> rest.text)
              case _ =>
            }
            element.restrictions = Some(restriction)
          }

          dm.elements :+= element
        case x =>
      }
      //    }

      //            elem.child.map {
      //              case elem =>
      //                (elem \\ "restriction").map {
      //                  case rest => {
      //                    rest.child.map {
      //                      case Elem(prefix, label, attribs, scope, Text(text)) => println(elem.label + "  rest is" + label)
      ////                      case Elem(prefix, label, attribs, scope, child)=>println("Hey")
      //                      case _ =>
      //                    }
      //                    //                    new Restrictions(rest.getAttributeAsMap, restMap)
      //                  }
      //                }
      //              //                new Element(elem.label, restrictions.head, elem.getAttributeAsMap)
      //            }
    }

    //    println(dm)
  }


  test("Test data model xml using map") {


    val a=List(("a","b","c"),("c","d","e"))
//    val b=a.flatten.groupBy(identity)
//
//    println(b)

    //    for (domModel <- content \\ "dataModel") {
    //
    //      val dm = new DataModel
    //      dm.attributes = domModel.getAttributeAsMap
    //      (domModel \ "elements").flatMap(e=>e.child).filter(domElement=>domElement.isInstanceOf[Elem]).map(x =>{
    //        element.attributes = x.getAttributeAsMap
    //        element.elemType = x.label
    //
    //        (x \ "restriction").map(r=>{
    //          val attrs = r.getAttributeAsMap
    //          val restValues = r.child.filter (p => p.isInstanceOf[Elem] ).map (p => p.label -> p.text).toMap
    //          new Restriction (attrs, restValues)
    //        })
    //
    //      })
    //        case x: Elem =>
    //          val element = new Element
    //


    //            restriction.attributes = attrs

    //            element.restrictions :+= restriction
  }

  //      dm.elements :+= element
  //        case x =>
  //      }

}