package ir.phsys.xview.generator.actor.java

import akka.actor.Actor
import ir.phsys.xview.generator.CodeGenerator.Generate
import org.fusesource.scalate.TemplateEngine
import sys.process._
import java.io.File
import grizzled.slf4j.Logger

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
      for (dataModel <- project.getDataModels.getMap) {
        logger.info(s"data model ${dataModel._1}")
        val result = engine.layout(dataModelTemplate, Map("dataModel" -> dataModel))
        val out = new File("/home/pooya/projects/I2Sc/src/main/resource/a.java")

        result #> out
      }
  }
}
