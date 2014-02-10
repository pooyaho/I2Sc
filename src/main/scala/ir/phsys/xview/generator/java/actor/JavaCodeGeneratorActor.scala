package ir.phsys.xview.generator.java.actor

import akka.actor.Actor
import ir.phsys.xview.generator.CodeGenerator.Generate
import org.fusesource.scalate.TemplateEngine
import java.io._
import grizzled.slf4j.Logger
import ir.phsys.xview.model.project.Project

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/4/14
 *         Time: 6:20 PM
 */


class JavaCodeGeneratorActor extends Actor {
  val engine = new TemplateEngine()
  val dataModelTemplate = "/home/pooya/projects/I2Sc/src/main/resource/template/cs/datamodel.ssp"
  val logger = Logger[this.type]


  def receive: Actor.Receive = {
    case Generate(project) =>
      logger.info("In Java code generator!")
      generateDataModels(project)
      generateUI(project)
  }

  private def generateDataModels(project: Project) = {
    for ((name, dataModel) <- project.getDataModels.getMap) {
      logger.info(s"data model $name")
      val result = engine.layout(dataModelTemplate, Map("model" -> dataModel))

      val out = new PrintWriter(
        new File(s"/home/pooya/projects/I2Sc/src/main/resource/${dataModel.attributes("name")}.java"))
      out.write(result)
      out.close()
      //        (result #> out).!
      //        "Pooya" #> out
    }
  }

  private def generateUI(project: Project) = {
    project.getPages.getApplication
  }
}
