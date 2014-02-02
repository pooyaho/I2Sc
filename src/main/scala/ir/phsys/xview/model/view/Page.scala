package ir.phsys.xview.model.view

import ir.phsys.xview.model.layout.FormLayout
import ir.phsys.xview.model.BaseModel
import scala.beans.BeanProperty


/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/1/14
 *         Time: 4:23 PM
 */


case class Page(@BeanProperty var attributes: Map[String, String] = Map.empty[String, String],
           var layout: FormLayout = null,
           var widgets: List[Widget] = List.empty[Widget])
  extends BaseModel


//case class ApplicationForm(@BeanProperty var attributes: Map[String, String] = Map.empty[String, String],
//                           var layout: FormLayout = null,
//                           var widgets: List[Widget] = List.empty[Widget]) extends BaseModel

//object ApllicationForm{
//implicit def toApp(p:to):
//}