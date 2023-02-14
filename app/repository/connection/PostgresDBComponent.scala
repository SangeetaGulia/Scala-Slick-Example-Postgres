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

/*
For extended support on PostgreSQL, use below code:

library dependency required:   "com.github.tminglei" %% "slick-pg" % "0.16.2",


package repository.connection

import utils.ConfigReader
import com.github.tminglei.slickpg._
import com.google.inject.{ImplementedBy, Inject}

trait PostgresProfile extends ExPostgresProfile
  with PgArraySupport
  with PgDate2Support
  with PgRangeSupport
  with PgHStoreSupport
  //with PgPlayJsonSupport
  with PgSearchSupport
  //with PgPostGISSupport
  with PgNetSupport
  with PgLTreeSupport {
  override val api = MyAPI

  // Add back `capabilities.insertOrUpdate` to enable native `upsert` support; for postgres 9.5+
  //override protected def computeCapabilities: Set[Capability] =
  //  super.computeCapabilities + JdbcProfile.capabilities.insertOrUpdate

  def pgjson = "jsonb" // jsonb support is in postgres 9.4.0 onward; for 9.3.x use "json"

  object MyAPI extends API with ArrayImplicits
    with DateTimeImplicits
    //  with JsonImplicits
    with NetImplicits
    with LTreeImplicits
    with RangeImplicits
    with HStoreImplicits
    with SearchImplicits
    with SearchAssistants {

    implicit val intListTypeMapper = new SimpleArrayJdbcType[Int]("integer").to(_.toList)
    implicit val intListListTypeMapper = new AdvancedArrayJdbcType[List[Int]]("integer[]",
      utils.SimpleArrayUtils.fromString[List[Int]](s =>
        scala.util.Try(s.substring(5, s.length - 1).split(",").map(_.trim.toInt).toList).getOrElse(List())
      )(_).orNull,
      utils.SimpleArrayUtils.mkString[List[Int]](_.toString)
    ).to(_.toList)
  }

}

object PostgresProfile extends PostgresProfile

@ImplementedBy(classOf[PostgresDBComponentImpl])
trait PostgresDBComponent extends DbComponent {
  val driver = PostgresProfile

  import driver.api._

  val db: Database = PostgresDB.connectionPool
}

class PostgresDBComponentImpl @Inject()(configReader: ConfigReader) extends PostgresDBComponent

private[connection] object PostgresDB {

  import slick.jdbc.PostgresProfile.api._
  val configReader = new ConfigReader{}
  val connectionPool = Database.forConfig("db", configReader.config)
}


 */
