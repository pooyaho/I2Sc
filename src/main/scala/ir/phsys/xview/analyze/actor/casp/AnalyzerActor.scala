package ir.phsys.xview.analyze.actor.casp

import akka.actor.{Actor, ActorRef}
import grizzled.slf4j.Logger
import scala.util.{Failure, Success, Try}

import ir.phsys.xview.model.project.Project
import ir.phsys.xview.model.view.Page
import ir.phsys.xview.analyze.exception.AttributeNotFoundException
import ir.phsys.xview.model.datamodel.DataModel
import ir.phsys.xview.model.layout.Layout
import ir.phsys.xview.model.BaseModel

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 23.02.14
 *         Time: 18:56
 */

