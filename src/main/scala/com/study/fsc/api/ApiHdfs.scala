package com.study.fsc.api

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

import scala.collection.mutable.ListBuffer

object ApiHdfs extends ApiStrategy {
  private val fs: FileSystem = {
    val conf = new Configuration()
    conf.set("fs.defaultFS", "hdfs://localhost:127001")
    FileSystem.get(conf)
  }
  override def getAllFiles(path: String): Seq[String] = {
    val files = fs.listFiles(new Path(path), false)
    val filenames = ListBuffer[String]()
    while (files.hasNext) {
      filenames += files.next().getPath().toString()
    }
    filenames.toList
  }

  override def getAllDirectories(path: String): Seq[String] = ???
}
