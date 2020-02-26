package com.study.fsc.jobtracker.db

import com.study.fsc.jobtracker.models.DataSource
import scalikejdbc._

class AppDb(options: DbOptions) {

  ConnectionPool.add("APP_DB",
    options.connectionString,
    options.appDbUser,
    options.appDbPassword
  )(ConnectionPool.DEFAULT_CONNECTION_POOL_FACTORY)

  implicit val session: NamedAutoSession = NamedAutoSession("APP_DB")
  private implicit val db: SQLSyntax = SQLSyntax.createUnsafely(options.appDb)

  def getPendingRequestByDataSource(dataSource: DataSource.Value): Seq[Request] = {
    NamedDB("APP_DB") readOnly { implicit session =>
      Request.getPendingByDataSource(dataSource).map(Request.apply).list()()
    }
  }

  def logStartProcessing(id: Long): Unit = {
    NamedDB("APP_DB") autoCommit  { implicit session =>
      Request.updateStartedRequest(id).update()()
    }
  }

  def logSuccessfulProcessing(id: Long): Unit = {
    NamedDB("APP_DB") autoCommit  { implicit session =>
      Request.updateSuccessfulRequest(id).update()()
    }
  }

  def logFailedProcessing(id: Long): Unit = {
    NamedDB("APP_DB") autoCommit  { implicit session =>
      Request.updateFailedRequest(id).update()()
    }
  }
}

case class DbOptions(connectionString: String = "",
                     appDb: String = "",
                     appDbUser: String = "",
                     appDbPassword: String = "")
