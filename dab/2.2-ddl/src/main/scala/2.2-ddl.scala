
import shared.PresentationUtil._
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object Modeling {
  import Enumeration._
  
  val exampleMovieTable = <.table(
    <.thead(
      <.tr(
        <.th("title"),
        <.th("year"),
        <.th("length"),
        <.th("genre"),
      ),
    ),
    <.tbody(
      <.tr(
        <.td("Star Wars"),
        <.td("1977"),
        <.td("124"),
        <.td("SciFi"),
      ),
      <.tr(
        <.td("Ghost"),
        <.td("1990"),
        <.td("127"),
        <.td("Drama"),
      ),
      <.tr(
        <.td("Skyfall"),
        <.td("2012"),
        <.td("143"),
        <.td("Action"),
      ),
    ),
  )

  val chapter1 = chapter(
    auHeadlineSlide(
      <.h2("SQL"),
      <.br,
      <.h4("Domain Definition Language"),
      <.img(VdomAttr("data-src") := "./img/createDatabase.jpg", ^.cls := "headerMeme"),
    ),

    headerSlide("Agenda",
      Enumeration(
        Item.stable("About SQL"),
        Item.stable("CREATE statement"),
        Item.stable("Types"),
        Item.stable("Constraints"),
        Item.stable("INSERT statement"),
        Item.stable("ALTER statement"),
        Item.stable("Modification"),
      ),
    ),
  )

  val chapter2 = chapter(
    headerSlideWithColumns("Connect to SQL Server")
      (Enumeration(
        Item.stable("Docker"),
        Enumeration(
          Item.stable("$ /opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P '1234*******'"),
        ),
        Item.stable("C# program"),
        Enumeration(
          Item.stable("string ConnectionString = @\"Server=127.0.0.1,1433;Database=MyEfCoreApp;User Id=SA;Password=1234********\""),
          Item.stable("Used later in course"),
        ),
        Enumeration(
          Item.stable("Azure studio"),
        ),
      ),
    )(
        <.img(VdomAttr("data-src") := "./img/connectionAzure.jpg", VdomStyle("max-height") := "600px"),
    ),
  )

  val chapter3 = chapter(
    headerSlide("CREATE database",
      Enumeration(
        Item.stable("Create a database"),
        Enumeration(
          Item.stable(sql("CREATE DATABASE I4DAB")),
        ),
        Item.stable("Select a database"),
        Enumeration(
          Item.stable(sql("USE I4DAB")),
        ),
        Item.stable("Show database on server"),
        Enumeration(
          Item.stable(sql("""SELECT name FROM sys.Databases
                            |------------------------------
                            |master
                            |tempdb
                            |model
                            |msdb
                            |I4DAB""".stripMargin)),
        ),
        Item.stable("Note: SQL Server is not key sensitive"),

      ),
    ),

    headerSlide("CREATE tables",
      Enumeration(
        Item.stable("Create tables"),
        Enumeration(
          Item.stable(sql("""CREATE TABLE Persons
                            | (Id INT,
                            |  FirstName NVARCHAR(50),
                            |  LastName NVARCHAR(50),
                            |  Birthday DATETIME)""".stripMargin)),
        ),
        Item.stable("Show tables in selected database"),
        Enumeration(
          Item.stable(sql("""SELECT TABLE_NAME
                            |FROM I4DAB.INFORMATION_SCHEMA.TABLES
                            |WHERE TABLE_TYPE = 'BASE TABLE'""".stripMargin)),
        ),
        Item.stable("Note: Strings are case-sensitive"),
        Item.stable("Note: Create NVARCHAR without size paramater -> NVARCHAR(1)"),
      ),
    ),

    noHeaderSlide(
      Enumeration(
        Item.stable(<.span("Details about a existing table"), <.br,
          sql("exec sp_columns Persons")),
      ),
      <.hr,
      <.img(VdomAttr("data-src") := "./img/describeTable.png", VdomStyle("max-height") := "600px"),
    ),
  )

  val chapter4 = chapter(
    headerSlide("Data types",
      Enumeration(
        Item.stable(<.a(^.href :="https://docs.microsoft.com/en-us/sql/t-sql/data-types/data-types-transact-sql?view=sql-server-2017", "SQL Server 2017 datatypes")),
        Item.stable("Numbers:"),
        Enumeration(
          Item.stable("Bigint, Int, Decimal, Money, Float, Real,..."),
        ),
        Item.stable("DateTime:"),
        Enumeration(
          Item.stable("Date, DateTime2, DateTimeOffset, Time"),
        ),
        Item.stable("Text:"),
        Enumeration(
          Item.stable("Char, Text, Varchar, NChar, NVarChar, NText"),
        ),
        Item.stable("Binary:"),
        Enumeration(
          Item.stable("Binary, VarBinary, Image"),
        ),
      ),
    ),
  )

  val chapter5 = chapter(
    headerSlide("Constraint (1/2)",
      Enumeration(
        Item.stable("Primary key constraint"),
        Enumeration(
          Item.stable(sql("""CREATE TABLE Students
                            | (Id INT PRIMARY KEY,
                            |  FirstName NVARCHAR(32),
                            |  LastNAme NVARCHAR(32),
                            |  AuId NVARCHAR(20)
                            |)""".stripMargin)),
        ),
        Item.stable("Check column value constraint"),
        Enumeration(
          Item.stable(sql("""CREATE TABLE Teachers
                            | (Id INT PRIMARY KEY,
                            |  Name NVARCHAR(128),
                            |  Age Int,
                            |  CHECK(Age >= 18)
                            |)""".stripMargin)),
        ),
      ),
    ),

    headerSlide("Constraint (2/2)",
      Enumeration(
        Item.stable("Default value constraints"),
        Enumeration(
          Item.stable(sql("""CREATE TABLE Orders
                            | (Id INT NOT NULL,
                            |  OrderNumber INT NOT NULL,
                            |  OrderDate DATE DEFAULT GETDATE()
                            |)""".stripMargin)),
          Item.stable("NOT NULL - attribute cannot be null")
        ),
        Item.stable("Foreign key constraint"),
        Enumeration(
          Item.stable(sql("""CREATE TABLE Assignments
                              | (Id INT PRIMARY KEY
                              |  Name VARCHAR(20),
                              |  Body TEXT,
                              |  StudentId INT FOREIGN KEY REFERENCES Student(id)
                              |)""".stripMargin)),
        ),
        Item.stable("Note: Inline foregn key above, also possible to declare constraints after attributes")
      ),
    ),

    headerSlide("Referential integrity",
      Enumeration(
        Item.stable("The default policy"),
        Enumeration(
          Item.stable("Reject violating modifications"),
        ),
        Item.stable(<.span("Deletion"), <.br,
          sql("ON DELETE CASCADE")),
        Item.stable("Update"),
        Enumeration(
          Item.stable(<.span("Changes are mimicked in the foreign key"), <.br,
            sql("ON UPDATE CASCADE")),
          Item.stable(<.span("Modification to referenced relation, foreign key are change to null"), <.br,
            sql("ON UPDATE SET NULL")),
        ),
      ),
    ),

    headerSlide("Auto values",
      Enumeration(
        Item.stable(<.span("Auto increment"), <.br,
            sql("""CREATE TABLES Goods
                  | (Id INT IDENTITY(1,1) PRIMARY KEY,
                  |  NAME VARCHAR(128)
                  |)""".stripMargin)),
        Item.stable("Identity(1,1), starts at 1 and increment by 1"),
        Item.stable("Identity(10,2), starts with 10 and increment by 2"),
      ),
    ),
  )

  val chapter6 = chapter(
    headerSlide("Insertion",
      Enumeration(
        Item.stable("Insertion"),
        Enumeration(
          Item.stable(<.span("Insert all values"), <.br,
              sql("""INSERT INTO Student VALUES
                    | (1, "Alice", "A.", "AU123456")
                    | (2, "Bob", "B.", "AU234567")
                    |""".stripMargin)),
          Item.stable(<.span("Insert some values"), <.br,
              sql("""INSERT INTO Orders (Id, OrderNumber)
                    |VALUES (1,12345, 2, 23456)""".stripMargin)),
        ),
        Item.stable(<.span("Constraint conflict"), <.br,
            sql("""INSERT INTO Assignments VALUES
                  |(1, 'I4dab', 'EfCore ...', 45)""".stripMargin), <.br,
            <.span("""The INSERT statement conflicted with the FORIGN KEY constraint 'FK__assignment__student_12314a'.
                     |....""".stripMargin)
        ),
      ),
    ),
  )

  val chapter7 = chapter(
    headerSlide("Alter table (1/2)",
      Enumeration(
        Item.stable(<.span("Add attribute to existing table"), <.br,
          sql("""ALTER TABLE Orders
                |ADD Price INT DEFAULT 0""".stripMargin)
        ),
        Item.stable(<.span("Add constraint to existing table"), <.br,
          sql("""ALTER TABLE Persons
                |ALTER COLUMN Id INT NOT NULL""".stripMargin)
        ),
      ),
    ),

    headerSlide("Alter table (2/2)",
      Enumeration(
        Item.stable(<.span("Remove existing constraint"), <.br,
          sql("""ALTER TABLE Teachers
                |DROP CONSTRAINT CK_Teachers__Age__324D4AFE""".stripMargin)
        ),
        Item.stable(<.span("Remove attribute existing table"), <.br,
          sql("""ALTER TABLE Teachers
                |DROP COLUMN Age""".stripMargin)
        ),
        Item.stable("Note: Connot drop columns with constraints, constraints needs to be removed first")
      ),
    ),

    headerSlide("Changing",
      Enumeration(
        Item.stable("Change column name"),
        Item.stable("Copy columns between tables"),
        Item.stable("Change column order"),
      ),
    ),
    
  )

  val chapter8 = chapter(
    headerSlide("Delete data",
      Enumeration(
        Item.stable(<.span("Delete row(s) from table"), <.br,
          sql("""DELETE FROM Students
                |WHERE Id = 1""".stripMargin)),
        Item.stable(<.span("Delete table"), <.br,
          sql("DROP TABLE Teachers")),
        Item.stable(<.span("Delete database"), <.br,
          sql("DROP DATABASE I4DAB")),
      ),
    ),

    headerSlide("Update data",
      Enumeration(
        Item.stable(<.span("Update row(s) in a table"), <.br,
          sql("""UPDATE Students
                |SET LastName = 'Larsen'
                |WHERE id = 2""".stripMargin)),
      ),
    ),

    headerSlide("Backup",
    Enumeration(
      Item.stable(<.span("Backup database, tables and data to file"), <.br,
        sql("""BACKUP DATABASE I4DAB
              |TO DISK = '/tmp/i4dab.sql'
              |---------------------------
              |Processed 360 pages...
              |Processed 7 pages...
              |BACKUP DATABASE successfully...""".stripMargin)),
      ),
    ),
  )

  val chapterEnd = chapter(
    headerSlide("Exercises",
      <.img(VdomAttr("data-src") := "./img/programing.gif", VdomStyle("height") := "400px"),
    ),

    auHeadlineSlide(
      <.img(VdomAttr("data-src") := "./../../img/ausegl_hvid.png", VdomStyle("max-height") := "600px"),
    ),

    headerSlide(
      "References",
      <.span("Frontpage meme: https://rtask.thinkr.fr/the-ten-commandments-for-a-well-formatted-database/"),
      <.br,
      <.span("Exercise gif: https://giphy.com/gifs/13HgwGsXF0aiGY/media"),
      <.br, 
    )
  )


  val Talk = ScalaComponent
    .builder[Unit]("Presentation")
    .renderStatic(
      <.div(
        ^.cls := "reveal",
        <.div(
          ^.cls := "slides",
          chapter1,
          chapter2,
          chapter3,
          chapter4,
          chapter5,
          chapter6,
          chapter7,
          chapter8,
          chapterEnd
        )
      )
    )
    .build

  def main(args: Array[String]): Unit = {
    Talk().renderIntoDOM(dom.document.body)
  }
}