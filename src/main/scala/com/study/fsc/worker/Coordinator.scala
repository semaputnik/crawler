package com.study.fsc.worker

import com.study.fsc.jobtracker.db.{AppDb, DbOptions}
import com.study.fsc.jobtracker.models.DataSource

class Coordinator(options: DbOptions) {

  implicit val appDb = new AppDb(options)

  def work: Unit = {

    while (true) {
      val requests = appDb.getPendingRequestByDataSource(DataSource.APFS)

      if (requests.nonEmpty) {
        val request = requests.head
        val miner = new RequestRunner
        println(miner.work(request))
      }

      Thread.sleep(10000)
    }
  }
}
