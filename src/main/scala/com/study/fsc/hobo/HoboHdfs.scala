package com.study.fsc.hobo

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

import scala.collection.mutable.ListBuffer

object HoboHdfs extends HoboStrategy {
  private val fs: FileSystem = {
    val conf = new Configuration()
    conf.set("fs.defaultFS", "hdfs://localhost:8020")
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
}
