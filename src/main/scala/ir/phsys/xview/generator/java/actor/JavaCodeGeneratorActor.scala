package ir.phsys.xview.generator.java.actor

import akka.actor.Actor
import java.io._
import ir.phsys.xview.model.project.Project
import ir.phsys.xview.generator.CodeGeneratorActor
import ir.phsys.xview.generator.CodeGeneratorActor.Generate
import ir.phsys.xview.generator.template.Engine


/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/4/14
 *         Time: 6:20 PM
 */


class JavaCodeGeneratorActor extends CodeGeneratorActor {
  val dataModelTemplate = "/home/pooya/projects/I2Sc/src/main/resource/template/cs/datamodel.ssp"

  def receive: Actor.Receive = {
    case Generate(project) =>
      logger.info("In Java code generator!")
      generateDataModels(project)
      generateViewModels(project)
  }

  def generateDataModels(project: Project): Unit = {
    import ir.phsys.xview.util.io.FileUtils._

    for ((name, dataModel) <- project.getDataModels.getMap) {
      logger.info(s"data model $name")
      val result = Engine(dataModelTemplate, Map("model" -> dataModel))
      val file = new File(s"/home/pooya/projects/I2Sc/src/main/resource/${dataModel.attributes("name")}.java")
      file <# result
    }
  }

  def generateViewModels(project: Project): Unit = {
    ???
  }
}