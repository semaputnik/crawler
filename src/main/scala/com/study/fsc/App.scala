package com.study.fsc

import com.study.fsc.jobtracker.db.{AppDb, DbOptions}
import com.study.fsc.jobtracker.models.DataSource

/**
 * @author Semyon.Putnikov
 */
object App {
  
  def main(args : Array[String]) {
    val options = DbOptions(
      "jdbc:postgresql://localhost:5432/postgres",
      "public",
      "postgres",
      "postgres"
    )

    implicit val appDb = new AppDb(options)
    appDb.getPendingRequestByDataSource(DataSource.HDFS).foreach(println)
  }

}
