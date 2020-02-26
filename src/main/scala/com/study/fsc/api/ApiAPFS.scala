package com.study.fsc.api

import java.io.File

object ApiAPFS extends ApiStrategy {
  override def getAllFiles(path: String): Seq[String] = {
    new File(path).listFiles().map(_.getName).toList
  }

  override def getAllDirectories(path: String): Seq[String] = {
    new File(path).listFiles().filter(_.isDirectory).map(_.getName).toList
  }
}
