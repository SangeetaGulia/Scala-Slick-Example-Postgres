package repository.dao

import models.Environment
import repository.connection.{DbComponent, PostgresDBComponent}
import slick.lifted.ProvenShape
import scala.concurrent.Future

trait EnvironmentDAO {
  this: DbComponent =>

  import driver.api._

  val environmentTableQuery = TableQuery[EnvironmentTable]

  private[dao] class EnvironmentTable(tag: Tag) extends Table[Environment](tag, "environment") {
    val envt = column[String]("environment", O.SqlType("VARCHAR(200)"))
    val token = column[String]("access_token", O.SqlType("VARCHAR(1000)"))

    def pk = primaryKey("pkenvironment", (envt))

    def * : ProvenShape[Environment] = (envt,token) <> (Environment.tupled, Environment.unapply)
  }

}

trait EnvironmentRepo extends EnvironmentDAO {
  this: DbComponent =>

  import driver.api._

  // $COVERAGE-OFF$ This part is covered by create table scripts. Hence cannot be tested here.
  def createEnvironmentRepo(): Future[Unit] = {
    db.run(environmentTableQuery.schema.createIfNotExists)
  }
  // $COVERAGE-ON$

  def insertAll(environments: Seq[Environment]): Future[Option[Int]] = {
    db.run(environmentTableQuery ++= environments)
  }


  def insertenvironmentRecord(environment: Environment): Future[Int] = {
    db.run {
      environmentTableQuery += environment
    }
  }

  def gettoken(envt: String): Future[String] = {
    val statement = environmentTableQuery
      .filter(environmentRow => (environmentRow.envt === envt))
      .map(_.token)
      .to[List].result.head
    db.run(statement)
  }


  def getenvironmentDetails(): Future[List[Environment]] = {
    val statement = environmentTableQuery
      .to[List].result
    db.run(statement)
  }
}

object EnvironmentRepo extends EnvironmentRepo with PostgresDBComponent
