package com.study.fsc.hobo

import java.io.File

object HoboLocal extends HoboStrategy {
  override def getAllFiles(path: String): Seq[String] = {
    new File(path).listFiles().map(_.getName).toList
  }
}
