package ir.phsys.xview.model.datamodel

import ir.phsys.xview.model.BaseModel
import scala.beans.BeanProperty
import ir.phsys.xview.model.exception.DataModelAlreadyDefinedException


/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 1/29/14
 *         Time: 12:25 PM
 */
class DataModel(@BeanProperty var attributes: Map[String, String] = Map.empty[String, String],
                var elements: List[Element] = List.empty[Element],
                var dataModels: List[DataModel] = List.empty[DataModel]) extends BaseModel {
  def containsInnerClass = dataModels.length > 0
}

class DataModels extends Map[String, DataModel] {
  //  private var dataModelMap = Map.empty[String, DataModel]
  private var nameSet = Set.empty[String]

  //769
  def +=(m: DataModel) = {
    if (contains(m.getUniqueName))
      throw new DataModelAlreadyDefinedException(s"Data model ${m.getUniqueName}")
    this ++= Map(m.getUniqueName -> m)
    nameSet += m.getUniqueName

    def recursivelyAddInnerModels(model: DataModel): Unit = {
      if (model.containsInnerClass) {
        model.dataModels foreach {
          case inner =>
            val name = model.getUniqueName + "." + inner.getUniqueName
            if (nameSet.contains(name))
              throw new DataModelAlreadyDefinedException(name)
            nameSet += name
            recursivelyAddInnerModels(inner)
        }
      }
    }

    recursivelyAddInnerModels(m)
  }

  def contains(name: String, recursive: Boolean = false): Unit = contains(name) match {
    case x => if (x && recursive) x || nameSet.contains(name)
    else x
  }

  def get(key: String): Option[DataModel] = super.get(key)

  def iterator: Iterator[(String, DataModel)] = this.iterator

  def -(key: String): Map[String, DataModel] = super.-(key)

  def +[B1 >: DataModel](kv: (String, B1)): Map[String, B1] = super.+(kv)
}