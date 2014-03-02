package ir.phsys.xview.generator.java.actor

import akka.actor.Actor
import java.io._
import ir.phsys.xview.model.project.Project
import ir.phsys.xview.generator.CodeGeneratorActor
import ir.phsys.xview.generator.template.Engine
import scala.util.{Failure, Success, Try}


/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/4/14
 *         Time: 6:20 PM
 */


class JavaCodeGeneratorActor(id: Int) extends CodeGeneratorActor {

//  import CodeGeneratorActor._
//
//  val dataModelTemplate = "/home/pooya/projects/I2Sc/src/main/resource/template/cs/datamodel.ssp"
//
//  def receive: Actor.Receive = {
//    case GenerateCode(path, project) =>
//      logger.info("In Java code generator!")
//      Try({
//        generateDataModels(project)
//        generateViewModels(project)
//      }) match {
//        case Success(s) =>
//          sender ! CodeGenSuccess(id)
//        case Failure(f) =>
//          sender ! CodeGenFailure(f)
//      }
//
//  }
//
//  def generateDataModels(project: Project): Unit = {
//    import ir.phsys.xview.util.io.FileUtils._
//
//    for ((name, dataModel) <- project.getDataModels.getMap) {
//      logger.info(s"data model $name")
//      val result = Engine(dataModelTemplate, Map("model" -> dataModel))
//      val file = new File(s"/home/pooya/projects/I2Sc/src/main/resource/${dataModel.attributes("name")}.java")
//      file <# result
//    }
//  }
//
//  def generateViewModels(project: Project): Unit = {
//    ???
//  }
  protected def generateDataModels(project: Project): Unit = ???

  protected def generateViewModels(project: Project): Unit = ???

  def receive: Actor.Receive = ???
}