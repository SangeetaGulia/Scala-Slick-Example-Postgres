package models

import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.{Application, Mode}
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.WithApplication

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class PersonRepositoryTest extends PlaySpec {

  val app: Application = new GuiceApplicationBuilder()
      .configure(Map(
    "slick.dbs.default.driver" -> "slick.driver.H2Driver$",
    "slick.dbs.default.db.driver" -> "org.h2.Driver",
    "slick.dbs.default.db.url" -> "jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;TRACE_LEVEL_SYSTEM_OUT=2;DATABASE_TO_UPPER=false;INIT=runscript from './test/resources/schema.sql'\\;runscript from './test/resources/schemadata.sql'",
    "slick.dbs.default.db.user" -> "test",
        "slick.dbs.default.db.keepAliveConnection" -> "true",
    "slick.dbs.default.db.password" -> ""))
    .in(Mode.Test)
    .build()


  val personRepo = Application.instanceCache[PersonRepository]

  "PersonRepository" should {
    "test1" in new WithApplication(app) {
      assert(Await.result(personRepo(app).insert("paritosh", 30), Duration.Inf) === 2)
    }

      "test2" in new WithApplication(app) {
        assert(Await.result(personRepo(app).list(), Duration.Inf) === 1)
      }

    }
  }

