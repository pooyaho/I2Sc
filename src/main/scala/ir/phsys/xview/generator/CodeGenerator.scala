package ir.phsys.xview.generator

import ir.phsys.xview.model.project.Project

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/8/14
 *         Time: 10:59 AM
 */
object CodeGenerator {

  case class Generate(project: Project)

  case object OperationSuccess

  case object OperationFailed

}
