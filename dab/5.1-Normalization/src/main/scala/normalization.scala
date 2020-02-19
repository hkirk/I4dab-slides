
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
      <.h2("Normalization"),
      <.br,
      <.h4("1st-, 2nd-, 3rd-, 4th-, & BC-NF"),
      <.img(VdomAttr("data-src") := "./img/jedi.jpeg", ^.cls := "headerMeme"),
    ),

    headerSlide("Agenda",
      Enumeration(
        Item.stable("Why Normalize"),
        Item.stable("Functional Dependency"),
        Item.stable("Boyce and Codd Normal Form"),
        Item.stable("3rd Normal Form"),
        Item.stable("4th Normal Form"),
      ),
    ),
  )

  val firstMovieTable = <.table(
    <.thead(
      <.tr(
        <.th("title"),
        <.th("year"),
        <.th("length"),
        <.th("genre"),
        <.th("studio"),
        <.th("starName"),
      ),
    ),
    <.tbody(
      <.tr(
        <.td("Star Wars"),
        <.td("1977"),
        <.td("124"),
        <.td("scifi"), // TODO break 1st??
        <.td("Fox"),
        <.td("Carrie Fisher"),
      ),
      <.tr(
        <.td("Star Wars"),
        <.td("1977"),
        <.td("124"),
        <.td("scifi"), // TODO break 1st
        <.td("Fox"),
        <.td("Mark Hamil"),
      ),
      <.tr(
        <.td("Star Wars"),
        <.td("1977"),
        <.td("124"),
        <.td("scifi"), // TODO break 1st
        <.td("Fox"),
        <.td("Harrison Ford"),
      ),
      <.tr(
        <.td("Gone with the Wind"),
        <.td("1939"),
        <.td("231"),
        <.td("drama"),
        <.td("MGM"),
        <.td("Vivien Leigh"),
      ),
      <.tr(
        <.td("Wayne's World"),
        <.td("1992"),
        <.td("95"),
        <.td("comedy"),
        <.td("Paramount"),
        <.td("Dana Carvey"),
      ),
      <.tr(
        <.td("Wayne's World"),
        <.td("1992"),
        <.td("95"),
        <.td("comedy"),
        <.td("Paramount"),
        <.td("Mike Meyers"),
      ),
    ),
  )

  val chapter2 = chapter(
    headerSlide("Anomalies",
      Enumeration(
        Item.stable(<.b("Redundancy"), <.br,
            <.span("Information may be repeated unnecessarily in several tubles"),
        ),
        Item.stable(<.b("Update anomalies"), <.br,
          <.span("We change information in one tuble, but the same information is left in another tuble"),
        ),
        Item.stable(<.b("Deletion anomalies"), <.br,
          <.span("If we delete data we may lose information as a side effect"),
        ),
      ),
    ),

    headerSlide("Movies1 table",
      firstMovieTable,
    ),
  )

  val chapter3 = chapter(
    headerSlide("Functional dependency",
      <.p(^.textAlign := "left",
        <.span("If two tuples of R agree on all attributes "), getOneThroughN("a"), <.span(", then they must also agree on another list of attributes "), getOneThroughN("b"), <.span("."), <.br,
        <.span("Formally"), 
        <.div(^.textAlign := "center",
          getOneThroughN("a"), <.span(" → "), getOneThroughN("b"), <.br,
        ),
        <.span("Reads: "),
        <.div(^.textAlign := "center",
          getOneThroughN("a"), <.span(" functional determines "), getOneThroughN("b"),
        )
      )
    ),

    headerSlide("FD examples", 
      <.p(^.textAlign := "left",
        <.span("Example:"),
        <.div(^.textAlign := "center",
          <.span("Movies1(title, year, length, genre, studioName, starName)"), <.br, <.br,
        ),
        fadeInFragment(
          <.b("Claim 1: "), 
          <.div(^.textAlign := "center",
            <.span("title, year → length, genre, studioName")
          ),
        ),
        fadeInFragment(
          <.span("Expect that no movies with same title are made in the same year"), <.br, // TODO more precise
        ),
        fadeInFragment(
          <.b("Claim 2: "),
          <.div(^.textAlign := "center",
            <.span("title, year → starName")
          ),
        ),
        fadeInFragment(
          <.span("False - more than one star for a particular movie"), <.br,  // TODO more precise
        ),
      )
    ),

    headerSlideLeftAligned("Keys", // TODO include notes
      <.span("We can say than 1...n of attributes { "), getOneThroughN("a"), <.span(" } is a key for a relation R if"), <.br,
      OrderedList(
        Item.stable("These attributes functional determine all other attributes in R"),
        Item.stable(<.span("No proper subset of { "), getOneThroughN("a"), <.span(" } functional determines all other attributes in R")),
      ),
      <.span("Example:"),
      <.div(^.textAlign := "center",
        <.span("Attributes {title, year, starName} forms a key for Movies1")
      ),
    ),

    headerSlideLeftAligned("Multiple keys",// TODO include notes
      <.span("A set of attributes that contains a key is called a superkey (superset of a key)"), <.br,
      <.span("A relation can hold multiple superkeys"), <.br,
      <.span("Example"),
      <.div(^.textAlign := "center",
        <.span("{title, year, starName} and {title, year, length, studioName, starName}")
      ),
    ),

    headerSlideLeftAligned("Decomposing Relations",
      <.span("To eliminate anomalies in relations, we can decompose them."), <.br,
      <.span("Given a relation R("), getOneThroughN("a"), <.span(") we may decompose into two relations S("), getOneThroughX("b", "m"), <.span(") and T("), getOneThroughX("c", "k"), <.span(") such that"), <.br,
      OrderedList(
        Item.stable(<.span("{"), getOneThroughN("a"), <.span("} = {"), getOneThroughX("b", "m"), <.span("} ∪ {"), getOneThroughX("c", "k"), <.span("}")),
        Item.stable("Attributes in S and T may overlap")
      ),
      fadeInFragment(
        <.span("Movies1(title, year, length, genre, studioName, starName) can be decomposed into"),
        <.div(^.textAlign := "center",
          <.span("Movies2(title, year, length, genre, studioName) and"), <.br,
          <.span("Stars(title, year, starName)"),
        ),
      )
    ),
  )

  val chapter4 = chapter(
    headerSlide("Normal forms",
      <.img(VdomAttr("data-src") := "./img/normalforms.png", VdomStyle("height") := "600px"),
    ),

    headerSlideLeftAligned("Boyce-Codd Normal Form (BCNF)", // TODO notes Movies1 is not in BCNF because FD exists
                                                            // Title, year -> length, genre, studioName - which is not a key
      <.span("Definition:"),
      <.div(^.textAlign := "center", <.span("A relation R is in BCNF iff. whenever there is a nontrival FD "), getOneThroughN("a"), <.span(" → "), getOneThroughN("b"), <.span(" for R it is the case that { "), getOneThroughN("a"), <.span(" } is a superkey")),
      <.div(^.textAlign := "center", <.span("Or left side of every nontrivial FD must contain a key")),
      fadeInFragment(
        <.span("Example"), <.br,
        <.span("Movies1 is not in BCN"), <.br,
      ),
      fadeInFragment(
        <.span("Movies 2, Stars is in BCNF"), <.br,
      ),
      <.b("Note"), <.span(": Sometimes decomposition into BCNF can end in situation where checking FD on the relations is not possible"),
    ),

    headerSlideLeftAligned("3NF",
      <.span("A relation R is in the third normal form (3NF) if:"),
      <.div(^.textAlign := "center", <.span("Whenever "), getOneThroughN("a"), <.span(" → "), getOneThroughN("b"), <.span(" is a nontrivial FD, either")),
      <.div(^.textAlign := "center", <.span("{"), getOneThroughN("a"), <.span("} is a super key or")),
      <.div(^.textAlign := "center", <.span(""), getOneThroughN("b"), <.span(" not in A’s are member of some key*.")),
      <.span("“For each nontrival FD, either the left side is a superkey, or the right side consists of prime attributes only.” - Database Systems the Complete book 2nd edition."), <.br,
      <.b("Note"), <.span(": Weaker than BCNF"), <.br, <.br,
      <.span(VdomStyle("fontSize") := "22px", "* an attribute that is a member of some key is called a prime."),
      /* Todod notes
      3NF
      • 2NF
      • Intet felt må være transitivt afhængigt af primærnøglen 

    Hvis en tabel har felter, der er indbyrdes afhængige, og ikke er en del af primærnøglen, så skal én eller flere af disse felter flyttes over i en ny tabel sammen med en kopi af det tilbageblevne felt (som derved bliver fremmednøgle).
    */
    ),

    headerSlideLeftAligned("1NF & 2NF",
      Enumeration(
        Item.stable(
          <.b("1NF"), <.br,
          <.span("A relation is in 1NF iff. each have only atomic values - each column have only one value for each row"), <.br,
          <.span("Upheld these days for “all” SQL servers."), <.br,
          <.span("Reason this exists is because SQL builds on Relational Algebra"), <.br,
        ),
        Item.stable(
          <.b("2NF"), <.br,
          <.span("A relation is in 2NF iff. its in 1NF and every non-key attribute is fully dependent on a primary key. An attribute is fully dependent on a primary key if its on the right side of an FD for which the left side is either the primary key or something that can be derived from the primary key."),
        ),
      ),
    ),
      
  )

  val chapter5 = chapter(

    headerSlideLeftAligned("Examples (1/4)", 
      <.img(VdomAttr("data-src") := "./img/initial_data.png", VdomStyle("height") := "150px"),
      fadeInFragment(
        <.b("1NF - take 1"), <.br,
        <.img(VdomAttr("data-src") := "./img/1nf.png", VdomStyle("height") := "150px")
      ),
      fadeInFragment(
        <.b("1NF - take 2"), <.br,
        <.span(VdomStyle("fontSize") := "32px", 
          <.span("Book("), <.u("title"), <.span(", "), <.u("format"), <.span(", author, authorNationality, price, pages, thickness, genreId, genreName, publisherId)"), <.br,
          <.span("Subject("), <.u("subjectId"), <.span(", subjectName)"), <.br,
          <.span("Publisher("), <.u("publisherId"), <.span(", name, Country)"), <.br,
          <.span("Subject("), <.u("title"), <.span(", "), <.u("subjectId"), <.span(")")
        ),
      ),
    ),
    
    headerSlideLeftAligned("Examples (2/4)", // TODO All attributer der ikke er del af en nøgle,  afhænger af title. Men kun Price afhænger af Format
      <.b("2NF"), <.br,
      <.span(VdomStyle("fontSize") := "32px", 
        <.span("Book("), <.u("title"), <.span(", author, authorNationality, pages, thickness, genreId, genreName, publisherId)"), <.br,
        <.span("Price("), <.u("title"), <.span(", "), <.u("format"), <.span(", price)"), <.br,
        <.span("Subject("), <.u("subjectId"), <.span(", subjectName)"), <.br,
        <.span("Publisher("), <.u("publisherId"), <.span(", name, country)"), <.br,
        <.span("Subject("), <.u("title"), <.span(", "), <.u("subjectId"), <.span(")")
      ),
    ),

    headerSlideLeftAligned("Examples (3/4)", // TODO 2NF + no transitive depencies
                                              // Eg GenreId and GenreName both depend on primary key Title
      <.b("3NF"), <.br,
      <.span(VdomStyle("fontSize") := "32px", 
        <.span("Book("), <.u("title"), <.span(", author, authorNationality, pages, thickness, genreId, publisherId)"), <.br,
        <.span("Genre("), <.u("genreId"), <.span(", genreName)"), <.br,
        <.span("Price("), <.u("title"), <.span(", "), <.u("format"), <.span(", price)"), <.br,
        <.span("Subject("), <.u("subjectId"), <.span(", subjectName)"), <.br,
        <.span("Publisher("), <.u("publisherId"), <.span(", name, country)"), <.br,
        <.span("Subject("), <.u("title"), <.span(", "), <.u("subjectId"), <.span(")")
      ),
    ),

    headerSlideLeftAligned("Examples (4/4)", 
      <.b("BCNF"), <.br, // TODO Non-triviel depency between Author and AuthorNationality
      <.span(VdomStyle("fontSize") := "32px", 
        <.span("Book("), <.u("title"), <.span(", author, pages, thickness, genreId, publisherId)"), <.br,
        <.span("Author("), <.u("author"), <.span(", nationality)"), <.br,
        <.span("Genre("), <.u("genreId"), <.span(", genreName)"), <.br,
        <.span("Price("), <.u("title"), <.span(", "), <.u("format"), <.span(", price)"), <.br,
        <.span("Subject("), <.u("subjectId"), <.span(", subjectName)"), <.br,
        <.span("Publisher("), <.u("publisherId"), <.span(", name, country)"), <.br,
        <.span("Subject("), <.u("title"), <.span(", "), <.u("subjectId"), <.span(")")
      ),
    ),
  )

  val chapter6 = chapter(
    headerSlideLeftAligned("4NF",
    /* TODO
    That is, if a relation is in 4NF, then every nontrivial MVD is really an FD with a superkey on the left

    Both in BCNF and therefore also in 3NF some redundancies are possible.

    https://en.wikipedia.org/wiki/Fourth_normal_form
    */
      <.span("BCNF applied to MVD instead of FD"), <.br,
      <.span("A relation R is in 4NF if whenever"),
      <.div(^.textAlign := "center", getOneThroughN("a"), <.span(" ↠ "), getOneThroughN("b")),
      <.div(^.textAlign := "center", <.span("Is a nontrival MVD, {"), getOneThroughN("a"), <.span("} is a super")),
      <.span("A multivalued dependency (MVD)"),
      <.div(^.textAlign := "center", getOneThroughN("a"), <.span(" ↠ "), getOneThroughN("b")),
      <.span("holds for a relation R if when we restrict ourselves to tubles of R that have particular values for each attribute in ("), getOneThroughN("a"), <.span(") then there is a set of values we find among the ("), getOneThroughN("b"), <.span(") is independent of the set of values we find among the attributes of R that is not in those sets."),
    ),

    headerSlide("In practice",
      OrderedList(
        Item.stable("Analyse the potential for performance benefits before normalizing"),
        OrderedList(
          Item.stable("Storage usage"),
          Item.stable("Update time"),
          Item.stable("Query time"),
        )
        Item.stable("BCNF or 3NF is normally the table forms to strive for*"),
        Item.stable("If performance is compromised by normalization, try denormalizing"),
      )
      <.span(VdomStyle("fontSize") := "22px", "* “The practical need for fourth normal form” by Margaret S. Wu states in astudy of forty organizational databases, over 20% contained one or more tables that violated 4NF while meeting all lower normal forms "),
    ),

    headerSlide("Database lifecycle"
      <.img(VdomAttr("data-src") := "./img/normalize-life-cycle.png", VdomStyle("height") := "600px"),
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
      <.span("Frontpage meme: http://www.developermemes.com/2013/03/27/sql-timing-out-so-raise-timeout/"),
      <.br,
      <.span("Join: https://www.cleanpng.com/png-join-microsoft-sql-server-table-oracle-database-6091970/"),
      <.br,
      <.span("Exercise gif: https://parentsneed.com/7-ways-to-help-make-homework-fun/"),
      <.br, 
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