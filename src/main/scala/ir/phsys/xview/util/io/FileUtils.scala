package ir.phsys.xview.util.io

import java.io.{File, PrintWriter}

/**
 * @author : Пуя Гуссейни
 *         Email : info@pooya-hfp.ir
 *         Date: 2/12/14
 *         Time: 7:34 PM
 */
object FileUtils {

  implicit class FileWriter(f: File) {
    def <#(str: String): Unit = {
      if (!f.exists())
        f.createNewFile()

      val out = new PrintWriter(f)
      out.write(str)
      out.close()
    }

    def <<#(str: String): Unit = {
      val out = new PrintWriter(f)
      out.append(str)
      out.close()
    }

  }

}
