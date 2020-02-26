package com.study.fsc.api

trait ApiStrategy {
  def getAllFiles(path: String): Seq[String]

  def getAllDirectories(path: String): Seq[String]
}
