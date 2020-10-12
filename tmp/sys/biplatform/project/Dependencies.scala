import sbt._

object Version {
  val slf4jVersion = "1.7.21"
  val scalaLoggingVersion = "3.1.0"
  val slickVersion = "3.1.1"
  val circeVersion = "0.6.1"
  val akkaV = "2.4.14"
  val akkaHttpV = "10.0.0"
  val ficusV = "1.2.1"
  val catsV = "0.8.1"
  val swaggerVersion = "1.5.12"
  val kylinVersion = "1.6.0"
}

object Libary {

  import Version._

  val cats = "org.typelevel" %% "cats" % catsV

  val kylinJdbc = "org.apache.kylin" %  "kylin-jdbc" % kylinVersion

  //  val elastic4sCore=  "com.sksamuel.elastic4s"  %% "elastic4s-core" % "1.7.0"

  val guava = "com.google.guava" % "guava" % "18.0"
  val commonsIo = "commons-io" % "commons-io" % "2.4"

  val slf4j = Seq(
    "org.slf4j" % "slf4j-api",
    "org.slf4j" % "slf4j-log4j12"
  ).map(_ % slf4jVersion)

  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion

  val loggingStack = Seq(slf4j, Seq(scalaLogging)).flatten

  val typesafeConfig = "com.typesafe" % "config" % "1.3.0"
  val ficus = "com.iheart" %% "ficus" % ficusV

  val jodaTime = "joda-time" % "joda-time" % "2.8.2"
  val jodaConvert = "org.joda" % "joda-convert" % "1.7"

  val circe = Seq("io.circe" %% "circe-core",
    "io.circe" %% "circe-generic",
    "io.circe" %% "circe-jawn"
  ).map(_ % circeVersion)


  val slick = "com.typesafe.slick" %% "slick" % slickVersion
  val slickHikari = ("com.typesafe.slick" %% "slick-hikaricp" % slickVersion)
    .exclude("com.zaxxer", "HikariCP-java6")
  val h2 = "com.h2database" % "h2" % "1.4.193"
  val mysqlConnectorJava = "mysql" % "mysql-connector-java" % "6.0.5"
  val flyway = "org.flywaydb" % "flyway-core" % "4.0"
  val jodaMapper = "com.github.tototoshi" %% "slick-joda-mapper" % "2.1.0"
  val slickStack = Seq(slick, h2, mysqlConnectorJava, slickHikari, jodaMapper, flyway)

  val scalatest = "org.scalatest" %% "scalatest" % "2.2.6" % "test"
  val unitTestingStack = Seq(scalatest)

  val sigarLoader = "io.kamon" % "sigar-loader" % "1.6.6-rev002"
  val akkaKryo = "com.github.romix.akka" %% "akka-kryo-serialization" % "0.5.0"

  val akka = Seq(
    "com.typesafe.akka" %% "akka-slf4j",
    "com.typesafe.akka" %% "akka-actor",
    "com.typesafe.akka" %% "akka-remote",
    "com.typesafe.akka" %% "akka-persistence",
    "com.typesafe.akka" %% "akka-cluster",
    "com.typesafe.akka" %% "akka-cluster-sharding",
    "com.typesafe.akka" %% "akka-cluster-metrics",
    "com.typesafe.akka" %% "akka-cluster-tools"
  ).map(_ % akkaV)

  val akkaHttp = Seq(
    "com.typesafe.akka" %% "akka-http",
    "com.typesafe.akka" %% "akka-http-core"
  ).map(_ % akkaHttpV) :+ "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV % Test

  val levelDb = "org.iq80.leveldb" % "leveldb" % "0.7"
  val levelDbJni = "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8"
  val akkaPersistenceJdbc = "com.github.dnvriend" %% "akka-persistence-jdbc" % "2.6.12"
  val akkaPersistenceStack = Seq(levelDb, levelDbJni, akkaPersistenceJdbc)


  val akkaHttpSession = "com.softwaremill.akka-http-session" %% "core" % "0.3.0"


  val akkaStack = Seq(
    akka,
    akkaHttp,
    akkaPersistenceStack,
    Seq(akkaHttpSession,
      sigarLoader,
      akkaKryo)).flatten


  val swaggerStack =
    Seq("io.swagger" % "swagger-core",
      "io.swagger" % "swagger-annotations",
      "io.swagger" % "swagger-models",
      "io.swagger" % "swagger-jaxrs").map(_ % swaggerVersion) ++
      Seq("io.swagger" %% "swagger-scala-module" % "1.0.3",
        "io.github.swagger2markup" % "swagger2markup" % "1.3.1")
}

object Dependencies {

  import Libary._

  val designer = loggingStack ++ circe ++ slickStack ++ akkaStack ++ Seq(
    cats,
    typesafeConfig,
    ficus,
    jodaTime,
    jodaConvert,
    commonsIo,
    kylinJdbc
  ) ++ unitTestingStack ++ swaggerStack

  val fileServer = unitTestingStack
}
