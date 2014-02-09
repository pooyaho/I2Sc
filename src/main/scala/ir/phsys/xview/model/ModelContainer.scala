package ir.phsys.xview.model


/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/4/14
 *         Time: 3:05 PM
 */

trait ModelContainer[A, B] {
  protected var nameSet = Set.empty[A]
  protected var map = Map.empty[A, B]

  def contains(name: A, recursive: Boolean = false): Unit = map.contains(name) match {
    case x => if (x && recursive) x || nameSet.contains(name)
    else x
  }

  def getMap = map

  def +=(m: B): Unit

  def values = map.values

  def get(key: A): Option[B] = map.get(key)
}