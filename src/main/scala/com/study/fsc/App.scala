package com.study.fsc

import com.study.fsc.jobtracker.db.DbOptions
import com.study.fsc.worker.Coordinator
import org.apache.log4j.LogManager

/**
 * @author Semyon.Putnikov
 */
object App {

  private val logger = LogManager.getLogger(this.getClass)

  def main(args : Array[String]) {

    val options = DbOptions(
      "jdbc:postgresql://localhost:5432/postgres",
      "public",
      "postgres",
      "postgres"
    )

    logger.info(s"App was started")

    val coordinator = new Coordinator(options)
    coordinator.work
  }

}
