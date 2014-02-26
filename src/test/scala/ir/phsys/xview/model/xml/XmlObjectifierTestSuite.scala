package ir.phsys.xview.xml


import org.scalatest.{BeforeAndAfterAll, FunSuite}
import akka.actor.{Props, ActorSystem}
import akka.testkit.TestKit
import org.scalatest.matchers.ShouldMatchers
import akka.testkit.ImplicitSender
import ir.phsys.xview.xml.objectifier.XmlObjectifyActor
import XmlObjectifyActor._
import scala.concurrent.duration._


/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/2/14
 *         Time: 6:33 PM
 */
class XmlObjectifierTestSuite(_system: ActorSystem) extends TestKit(
  _system)                                                  with FunSuite with ShouldMatchers with BeforeAndAfterAll with ImplicitSender {

  def this() = this(ActorSystem("PostponeSpec"))

  override def afterAll: Unit = system.shutdown()

  test("test path") {
    val objectifier = system.actorOf(Props[XmlObjectifyActor])
    objectifier ! Objectify("/home/pooya/projects/I2Sc/src/main/resource/input/simple")

    val project = receiveOne(10 seconds)
    println(project)
//    expectMsgType[OperationSucceed](10 seconds)
  }
}