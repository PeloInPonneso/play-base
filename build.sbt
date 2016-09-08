name := "yes"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaJpa,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "org.springframework" % "spring-context" % "3.2.1.RELEASE",
  "org.springframework" % "spring-orm" % "3.2.1.RELEASE",
  "org.springframework" % "spring-jdbc" % "3.2.1.RELEASE",
  "org.springframework" % "spring-tx" % "3.2.1.RELEASE",
  "org.springframework" % "spring-aop" % "3.2.1.RELEASE",
  "org.springframework" % "spring-expression" % "3.2.1.RELEASE",
  "org.hibernate" % "hibernate-entitymanager" % "3.6.9.Final",
  "commons-lang" % "commons-lang" % "2.4",
  "com.google.guava" % "guava" % "16.0"
)     

play.Project.playJavaSettings
