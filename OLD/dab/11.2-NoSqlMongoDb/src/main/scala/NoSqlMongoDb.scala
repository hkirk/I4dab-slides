
import shared.PresentationUtil._
import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

object NoSqlMongo {
  import Enumeration._
  
  val rdNoSql = <.table(
    <.thead(
      <.tr(
        <.th("Sql Server"),
        <.th("NoSql"),
      ),
    ),
    <.tbody(
      <.tr(
        <.td("Database"),
        <.td("Database"),
      ),
      <.tr(
        <.td("Table"),
        <.td("Collection"),
      ),
      <.tr(
        <.td("Row"),
        <.td("Document"),
      ),
    ),
  );

  val chapter1 = chapter(
    auHeadlineSlide(
      <.h2("NoSQL - MongoDb"),
    ),

    // headerSlide("Agenda",
    //   Enumeration(
    //     Item.stable("Modeling / Normalization"),
    //     Item.stable("Polymorphism"),
    //     Item.stable("Working with NoSQL"),
    //   ),
    // ),
  )

  val chapter2 = chapter(
    headerSlide("Document database",
      Enumeration(
        Item.stable("Record in MongoDb is a document"),
        Item.stable("Database consists of one or more collections"),
        Item.stable("Records are stored in collections (tables)"),
        Item.stable("Consists of key:value pairs"),
        Enumeration(
          Item.stable("Values can be: other documents, arrays, and simple types")
        ),
        Item.stable("Benefits:"),
        Enumeration(
          Item.stable("Similar to types in most programing language"),
          Item.stable("No expensive joins"),
          Item.stable("Dynamic schema"),
        ),
      ),
      // TODO insert image of json
    ),

    headerSlideLeftAligned("Database and Collections",
      rdNoSql,
      Enumeration(
        Item.stable("use myNewDB"),
        Enumeration(
          Item.stable("selects existing database or creates new"),
        ),
        Item.stable("db.myNewCollection1.insertOne( { x: 1 } )"),
        Enumeration(
          Item.stable("creates new collections and inserts element"),
        ),
      ),
    ),

    headerSlideLeftAligned("MongoDB shell",
      <.span("Whenever we are in the shell ‘db’ references the "), <.b("current"), <.span(" database"), <.br,
      <.br,
      <.span("Inserting into database"), <.br,
      javascript("""db.inventory.insertMany([
                  |   { item: "journal", qty: 25, status: "A", size: { h: 14, w: 21, uom: "cm" }, tags: [ "blank", "red" ] },
                  |   { item: "notebook", qty: 50, status: "A", size: { h: 8.5, w: 11, uom: "in" }, tags: [ "red", "blank" ] },
                  |   { item: "paper", qty: 10, status: "D", size: { h: 8.5, w: 11, uom: "in" }, tags: [ "red", "blank", "plain" ] },
                  |   { item: "planner", qty: 0, status: "D", size: { h: 22.85, w: 30, uom: "cm" }, tags: [ "blank", "red" ] },
                  |   { item: "postcard", qty: 45, status: "A", size: { h: 10, w: 15.25, uom: "cm" }, tags: [ "blue" ] }
                  |]);""".stripMargin), <.br,
      <.br,
      <.span("MongoDB adds an _id field with an ObjectId value if the field is not present in the document"),
    ),
  )

  val chapter3 = chapter(
    headerSlideLeftAligned("Selection", 
      <.span(".pretty() prints data in a readable format"), <.br,
      javascript("db.inventory.find({}).pretty()"), <.br,
      <.br,
      javascript("""db.inventory.find(
                  |  status: "D" or
                  |  qty: 0, status: "D" or  
                  |  tags: "red" or
                  |  size: { h: 14, w: 21, uom: "cm"}
                  |)""".stripMargin), <.br,
      <.br,
      <.span("What to return"), <.br,
      javascript("db.inventory.find( { }, { item: 1, status: 1 } );"), <.br,

      Enumeration(
        Item.stable("1: include"),
        Item.stable("0: exclude"),
      ), <.br,

      <.span("_id is special"),
      
      
    ),

    headerSlideLeftAligned("Selection from MongoDriver",
      cSharp("""public List<Book> Get() =>
              |        _books.Find(book => true).ToList();
              |
              |public Book Get(string id) =>
              |        _books.Find<Book>(book => book.Id == id).FirstOrDefault();
              |""".stripMargin), <.br,
      <.br,
      <.span("In general:"), <.br,
      cSharp("""var filter = Builders<BsonDocument>.Filter.Eq("i", 71);
              |var document = collection.Find(filter).First(); // .ToCursor""".stripMargin), <.br,
      <.br,
      <.span("See more on "), <.a(^.href := "https://docs.mongodb.com/drivers/csharp", "https://docs.mongodb.com/drivers/csharp"), <.span(" important links are API and Getting started")
    ),

    headerSlideLeftAligned("Querying",
      javascript("""db.inventory.find({})
                  |    .sort({qty: 1})
                  |    .limit(20)""".stripMargin),
      javascript("""db.inventory.find({})
                  |  .limit(20)
                  |  .skip(page * 20)""".stripMargin),
      javascript("db.inventory.find({“item”: /book$/})"), <.br,
      <.br,
      <.span("Create index"), <.br,
      javascript("db.inventory.createIndex(“tags”: 1)"),
    ),

    headerSlideLeftAligned("Alternative quering methods",
      javascript("db.collection.find()"),
      javascript("db.collection.findOne()"),
      javascript("db.collection.aggregate()"),
      javascript("db.collection.countDocuments()"),
      javascript("db.collection.estimatedDocumentCount()"),
      javascript("db.collection.count()"),
      javascript("db.collection.distinct()"), <.br,
      <.br,
      <.span("More at: "), <.a(^.href := "https://docs.mongodb.com/manual/reference/method/js-collection/", "https://docs.mongodb.com/manual/reference/method/js-collection/"),
    ),
  )

  val chapter4 = chapter(
    headerSlide("BSON",
       // TODO insert BSON types
    ),

    headerSlide("ObjectId",
      Enumeration(
        Item.stable("All documents in a collection must have an unique _id"),
        Item.stable("If omittied _id is generated - by following rules"),
        Enumeration(
          Item.stable("a 4-byte value representing the seconds since the Unix epoch,"),
          Item.stable("a 5-byte random value, and"),
          Item.stable("a 3-byte counter, starting with a random value."),
        ),
      ),
      javascript("""{
                    |  _id: ObjectId("5099803df3f4948bd2f98391"),
                    |  name: { first: "Alan", last: "Turing" },
                    |  birth: new Date('Jun 23, 1912'),
                    |  death: new Date('Jun 07, 1954'),
                    |  contribs: [ "Turing machine", "Turing test", "Turingery" ],
                    |  views : NumberLong(1250000)
                    |}""".stripMargin)
    ),

    headerSlide("Views",
      <.span("Read only views:"), <.br,
      Enumeration(
        Item.stable(<.span("Create a view that "), <.b("excludes"), <.span(" private or confidential data from a collection of employee data.")),
        Item.stable(<.span("Create a view that "), <.b("adds"), <.span(" computed fields from a collection of metrics.")),
        Item.stable(<.span("Create a view that "), <.b("joins"), <.span(" data from two different related collections.")),
      ),
    ),

  )

  val chapter5 = chapter(
    headerSlide("MongoDB Features", 
      Enumeration(
        Item.stable("Performance"),
        Enumeration(
          Item.stable("Fewer queries"),
          Item.stable("Keys (sub documents)"),
        ),
        Item.stable("Query Language"),
        Enumeration(
          Item.stable("CRUD"),
          Item.stable("Data searches"),
        ),
        Item.stable("Availability"),
        Enumeration(
          Item.stable("Via Replica set"),
          Item.stable("Automatic failover"),
          Item.stable("Data redundancy"),
        ),
        Item.stable("Scalability"),
        Enumeration(
          Item.stable("Sharding"),
        ),
      ),
      // TODO insert image
    ),

    headerSlide("Sharding",
      Enumeration(
        Item.stable("Shard - contains a subset of data"),
        Item.stable("Mongos - query router"),
        Item.stable("Config servers - Meta data and configurations"),
      ), <.br,
      <.br,
      <.span("Uses shard key(s) to distribute data. All data must contain this key"), <.br,
      <.br,
      Enumeration(
        Item.stable("+ Distributed Read/Writes"),
        Item.stable("+ Storage"),
        Item.stable("+ High availability"),
        Item.stable("- Complexity"),
        Item.stable("- Infrastructure"),
      ),
      // TODO insert image
    ),

  )

  val chapterEnd = chapter(
    headerSlide("Exercises",
      <.img(VdomAttr("data-src") := "./img/make-homework-fun.jpg", VdomStyle("height") := "600px"),
    ),

    auHeadlineSlide(
      <.img(VdomAttr("data-src") := "./../../img/ausegl_hvid.png", VdomStyle("max-height") := "600px"),
    ),

    headerSlide(
      "References",
    ),    
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