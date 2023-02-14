package repository.connection

import slick.jdbc.PostgresProfile
//import utilities.ConfigReader

trait PostgresDBComponent extends DbComponent {
  val driver = PostgresProfile
  import driver.api._
  val db: Database = PostgresDB.connectionPool
}

private[connection] object PostgresDB {
  import slick.jdbc.PostgresProfile.api._
  val connectionPool = Database.forConfig("db"/*, ConfigReader.config*/)
}
