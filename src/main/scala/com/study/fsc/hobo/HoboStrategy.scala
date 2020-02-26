package com.study.fsc.hobo

trait HoboStrategy {
  def getAllFiles(path: String): Seq[String]
}
