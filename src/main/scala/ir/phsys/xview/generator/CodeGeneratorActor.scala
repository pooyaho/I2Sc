package ir.phsys.xview.generator

import akka.actor.Actor
import org.fusesource.scalate.TemplateEngine
import grizzled.slf4j.Logger
import ir.phsys.xview.model.project.Project

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/12/14
 *         Time: 5:29 PM
 */
object CodeGeneratorActor {

  case class Generate(project: Project)

  case object OperationSuccess

  case object OperationFailed

}

trait CodeGeneratorActor extends Actor {
  val logger = Logger[this.type]

  protected def generateDataModels(project: Project)

  protected def generateViewModels(project: Project)
}