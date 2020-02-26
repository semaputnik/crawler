package com.study.fsc.jobtracker.db

import java.time.LocalDateTime

import com.study.fsc.jobtracker.models.{DataSource, JobStatus}
import scalikejdbc._

object Request {

  private[db] def getPendingByDataSource(dataSource: DataSource.Value)(implicit db: SQLSyntax): SQL[Nothing, NoExtractor] = {
    sql"""
         |SELECT
         |ID,
         |USERNAME,
         |REQUEST,
         |PATH,
         |DATASOURCE
         |FROM $db.crawler_logs
         |WHERE STATUS=${JobStatus.PENDING.toString}
         """.stripMargin
  }

  private[db] def updateStartedRequest(id: Long)(implicit db: SQLSyntax): SQL[Nothing, NoExtractor] = {
    sql"""
         |UPDATE $db.crawler_logs
         |SET
         |STATUS=${JobStatus.STARTED.toString},
         |STARTTIME=${LocalDateTime.now}
         |WHERE ID=$id
       """.stripMargin
  }

  private[db] def updateSuccessfulRequest(id: Long)(implicit db: SQLSyntax): SQL[Nothing, NoExtractor] = {
    sql"""
         |UPDATE $db.crawler_logs
         |SET
         |STATUS=${JobStatus.SUCCESSFUL.toString},
         |ENDTIME=${LocalDateTime.now}
         |WHERE ID=$id
       """.stripMargin
  }

  private[db] def updateFailedRequest(id: Long)(implicit db: SQLSyntax): SQL[Nothing, NoExtractor] = {
    sql"""
         |UPDATE $db.crawler_logs
         |SET
         |STATUS=${JobStatus.FAILED.toString},
         |ENDTIME=${LocalDateTime.now}
         |WHERE ID=$id
       """.stripMargin
  }

  private[db] def apply(res: WrappedResultSet): Request = {
    val id = res.long("ID")
    val username = res.string("USERNAME")
    val operation = res.string("REQUEST")
    val path = res.string("PATH")
    val dataSource = DataSource.withName(res.string("DATASOURCE"))
    Request(id, username, operation, path, dataSource)
  }
}

case class Request(id: Long,
                   username: String = "",
                   operation: String,
                   path: String,
                   dataSource: DataSource.Value = DataSource.APFS,
                  )
