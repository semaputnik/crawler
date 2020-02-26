package com.study.fsc.worker

import com.study.fsc.hobo.{HoboHdfs, HoboLocal}
import com.study.fsc.jobtracker.db.{AppDb, Request}
import com.study.fsc.jobtracker.models.DataSource

class Miner(implicit appDb: AppDb) {
  def work(request: Request): List[String] = {
    appDb.logStartProcessing(request.id)
    val hobo = request.dataSource match {
      case DataSource.HDFS => HoboHdfs
      case DataSource.APFS => HoboLocal
    }

    val result = request.operation match {
      case "ls" => hobo.getAllFiles(request.path).toList
    }
    appDb.logSuccessfulProcessing(request.id)
    result
  }
}
