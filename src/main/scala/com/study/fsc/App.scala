package com.study.fsc

import com.study.fsc.jobtracker.db.{AppDb, DbOptions}
import com.study.fsc.jobtracker.models.DataSource
import com.study.fsc.hobo.{HoboHdfs, HoboLocal}
import com.study.fsc.worker.Miner
import org.slf4j.{Logger, LoggerFactory}

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

    while (true){
      val requests = appDb.getPendingRequestByDataSource(DataSource.APFS)
      if (requests.nonEmpty) {
        val request = requests.head
        val miner = new Miner
        println(miner.work(request))
      }
      Thread.sleep(10000)
    }
  }

}
