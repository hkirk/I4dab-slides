
import shared.PresentationUtil._
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object Modeling {
  import Enumeration._
  
  val chapter1 = chapter(
    auHeadlineSlide(
      <.h2("SQLConnection + relation mapping"),
      <.br,
      <.h4("Domain Definition Language"),
      <.img(VdomAttr("data-src") := "./img/sql-connection.jpg", ^.cls := "headerMeme"),
    ),

    headerSlide("Agenda",
      Enumeration(
        Item.stable("Map relationships"),
        Enumeration(
          Item.stable("1-1"),
          Item.stable("1-N"),
          Item.stable("N-N"),
          Item.stable("Inheritance")
        ),
        Item.stable("SQL connection"),
      ),
    ),
  )

  val chapter2 = chapter(
    // TODO move to previuous lecture (ddl)
    headerSlide("Primary Keys",
      Enumeration(
        Item.stable(
          <.span("Primary key inline"), <.br,
          sql("""CREATE TABLE Movies (
            |  title varchar(50) PRIMARY KEY,
            |  year varchar(4), studio varchar(50),
            |  genre varchar(40),
            |)""".stripMargin),
        ),
        Item.stable(
          <.span("Primary key as constraint"), <.br,
          sql("""CREATE TABLE Movies (
            |  title varchar(50), year varchar(4),
            |  studio varchar(50), genre varchar(40),
            |  CONSTRAINT pk_title PRIMARY KEY (title)
            |)""".stripMargin),
        ),
        Item.stable(
          <.span("Primary key spanning multiple columns"), <.br,
          sql("""CREATE TABLE Movies (
            |  title varchar(50), year varchar(4),
            |  studio varchar(50), genre varchar(40),
            |  CONSTRAINT pk_title_year 
            |    PRIMARY KEY (title, year)
            |)""".stripMargin)
        ),
      ),
    ),

    headerSlide("Map 1-1 relations ships",
      Enumeration(
        Item.stable("Interest defines where key is located"),
      ),
      sql("""CREATE TABLE Car (
            |    reg VARCHAR(20) PRIMARY KEY,
            |    color VARCHAR(7),
            |)
            |
            |CREATE TABLE ParkingSpot (
            |    id INT PRIMARY KEY,
            |    address VARCHAR(80),
            |    car_reg VARCHAR(20) UNIQUE
            |      FOREIGN KEY REFERENCES Car(reg),
            |)""".stripMargin)
    ),

    headerSlide("Map 1-N relations ships",
      Enumeration(
        Item.stable("Set the foreign key constraint in N entity"),
      ),
      sql("""CREATE TABLE Car (
            |    reg VARCHAR(20) PRIMARY KEY,
            |    color VARCHAR(7),
            |)
            |
            |CREATE TABLE Wheels(
            |    id INT PRIMARY KEY,
            |    placement VARCHAR(2),
            |    car_reg VARCHAR(20)
            |      FOREIGN KEY REFERENCES Car(reg)
            |)""".stripMargin)
    ),

    headerSlide("Map N-N relations ships (1/2)",
      Enumeration(
        Item.stable("In CarDriver driver_cpr/car_reg pair"),
        Item.stable("Optional define pair as unique"),
      ),
      sql("""CREATE TABLE Car (
            |    reg VARCHAR(20) PRIMARY KEY,
            |    color VARCHAR(7),
            |)
            |
            |CREATE TABLE Drivers(
            |    cpr INT PRIMARY KEY,
            |    Age int,
            |    Name VARCHAR(50)
            |)
            |
            |CREATE TABLE CarDriver(
            |    driver_cpr INT
            |       FOREIGN KEY REFERENCES Drivers(cpr),
            |    car_reg VARCHAR(20)
            |       FOREIGN KEY REFERENCES Car(reg)
            |)""".stripMargin)
    ),

    headerSlide("Map N-N relations ships (2/2)",
      sql("""-- Or if the driver/car couple should be unique
            |CREATE TABLE CarDriver(
            |    driver_cpr INT 
            |       FOREIGN KEY REFERENCES Drivers(cpr),
            |    car_reg VARCHAR(20) 
            |        FOREIGN KEY REFERENCES Car(reg),
            |    CONSTRAINT uc_cpr_reg
            |        UNIQUE (driver_cpr, car_reg)
            |)
            |
            |-- To create relations insert into CarDriver
            |INSERT INTO CAR VALUES
            |  ('12345', '123145'), ('123145', '21sdaf')
            |INSERT INTO Drivers VALUES
            |  (123456, 12, 'Alice'), (43535, 18, 'Bob')
            |INSERT INTO CarDriver VALUES
            |  (123456,'12345'), (43535, '123145'),
            |  (123456,'123145')""".stripMargin)
    ),

    headerSlide("Map inheritance",
      Enumeration(
        Item.stable("Either in single table"),
        Item.stable("or as a 1-1 relation with required existence"),
      ),

      sql("""CREATE TABLE Products (
            |  id INT PRIMARY KEY,
            |  price INT, soup_type VARCHAR(10),
            |  expiration DATE, bakedOn VARCHAR(120),
            |  slices int, type VARCHAR(5)
            |)
            |
            |INSERT INTO Products
            | (id, price, soup_type, expiration, type)
            |VALUES (1, 10, 'mushroom', getDate(), 'soup'),
            |       (2, 15, 'shrimp', getDate(), 'soup')
            |
            |INSERT INTO Products
            |  (id, price, bakedOn, slices, type)
            |VALUES (3, 9, 'full', 30, 'bread'),
            |       (4, 12, 'white', 25, 'bread')""".stripMargin)
    ),
  )

  val chapter3 = chapter(
    headerSlide("How to install",
      Enumeration(
        Item.stable(".Net Core 3.0+ (I'll be using 3.1)"),
        OrderedList(
          Item.stable("$ mkdir SqlConnectionExample; cd SqlConnectionExample"),
          Item.stable("$ dotnet new console"),
          Item.stable("$ dotnet add package Microsoft.Data.SqlClient --version 1.1.0"),
          Item.stable("Or via package manager PM> Install-Package Microsoft.Data.SqlClient -Version 1.1.0"),
          Item.stable("$ dotnet run")
        ),
      ),
    ),

    headerSlide("SqlConnection",
      cSharp("""private static void CreateCommand(
               |    string queryString,
               |    string connectionString)
               |{
               |    using (SqlConnection connection
               |      = new SqlConnection(connectionString))
               |    {
               |        SqlCommand command = new SqlCommand(
               |             queryString, connection);
               |        command.Connection.Open();
               |        command.ExecuteNonQuery();
               |    }
               |}""".stripMargin)
    ),

    headerSlide("SqlCommand",
      cSharp("""private static void ReadOrderData(
                |                    string connectionString) {
                |  string queryString = 
                |      "SELECT OrderID, CustomerID
                |         FROM dbo.Orders;";
                |  using (SqlConnection connection =
                |       new SqlConnection(
                |             connectionString)) {
                |    SqlCommand command = new SqlCommand(
                |        queryString, connection);
                |    connection.Open();
                |    using(SqlDataReader reader =
                |                 command.ExecuteReader()) {
                |      while (reader.Read()) {
                |         Console.WriteLine(String.Format("{0}, {1}",
                |          reader[0], reader[1]));
                | } } } }""".stripMargin),
    ),
    
  )

  val chapterEnd = chapter(
    headerSlide("Exercises",
      <.img(VdomAttr("data-src") := "./img/sql-connection.jpg", VdomStyle("height") := "400px"),
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
          // chapter4,
          // chapter5,
          // chapter6,
          // chapter7,
          // chapter8,
          chapterEnd
        )
      )
    )
    .build

  def main(args: Array[String]): Unit = {
    Talk().renderIntoDOM(dom.document.body)
  }
}