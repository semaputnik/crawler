package com.study.fsc.worker

import com.study.fsc.api.{ApiAPFS, ApiHdfs}
import com.study.fsc.jobtracker.db.{AppDb, Request}
import com.study.fsc.jobtracker.models.DataSource
import com.study.fsc.kafkawriter.Producer
import org.apache.log4j.LogManager

class RequestRunner(implicit appDb: AppDb) {

  private val logger = LogManager.getLogger(this.getClass)
  private val producer = new Producer

  def work(request: Request): List[String] = {

    logger.info(s"Request ${request.id} started")

    appDb.logStartProcessing(request.id)
    val api = request.dataSource match {
      case DataSource.HDFS => ApiHdfs
      case DataSource.APFS => ApiAPFS
    }

    try {
      val result = request.operation match {
        case "getAllFiles" => api.getAllFiles(request.path).toList
        case "getAllDirectories" => api.getAllDirectories(request.path).toList
      }

      producer.write(request.id, result.toString())
      appDb.logSuccessfulProcessing(request.id)
      logger.info(s"Request ${request.id} successfully finished")
      result
    } catch {
      case e: Exception => {
        appDb.logFailedProcessing(request.id)
        logger.error(s"Processecing request with id=${request.id} was failed with exception: $e")
        List("")
      }
    }
  }
}
