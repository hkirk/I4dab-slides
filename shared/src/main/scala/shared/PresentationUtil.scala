package shared

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.vdom.TagOf
import org.scalajs.dom
import dom.raw.HTMLElement

object PresentationUtil {

  type TagOfHTMLElement = TagOf[HTMLElement];

  val font = HtmlTag("font")

  val dataBackground      = VdomAttr("data-background")
  val dataBackgroundColor = VdomAttr("data-background-color")
  val dataBackgroundSize  = VdomAttr("data-background-size")
  val dataTrim            = VdomAttr("data-trim") := ""
  val dataNoEscape        = VdomAttr("data-noescape") := ""
  val dataImageBackground = VdomAttr("data-background-image")

  def chapter(slides: TagOf[HTMLElement]*): TagOf[HTMLElement] = <.section(slides: _*)

  def header(text: String, cls: String): TagOf[HTMLElement] = 
    <.div(
      ^.cls := cls,
      <.p(text)
    )

  // 100% side-effect full
  private def removeHeader(): Unit = {
    val headerElements = dom.document.getElementsByClassName("slide-header")

    (0 until headerElements.length).foreach { id =>
      val element = headerElements(id)

      element.parentNode.removeChild(element)
    }
  }

  private def cleanSlide(content: TagOf[HTMLElement]): TagOf[HTMLElement] = {
    removeHeader()
    
    content
  }

  private val AUHeadlineSlideProps = Seq(
    (dataBackgroundColor := "#002546"),
    (dataBackgroundSize  := "30%")
  )

  def auHeadlineSlide(content: TagOf[HTMLElement]*): TagOf[HTMLElement] = cleanSlide(
      <.section(
        (AUHeadlineSlideProps ++: content): _*
      )
  )

  private val ChapterSlideProps = Seq(
    (dataBackgroundColor := "#002546"),
    (dataBackgroundSize  := "30%")
  )
  def chapterSlide(content: TagOf[HTMLElement]*): TagOf[HTMLElement] = cleanSlide(
    <.section(
      (ChapterSlideProps ++: content): _*
    )
  )

  def fullscreenImageSlide(url: String): TagOf[HTMLElement] = cleanSlide(
    <.section(
      dataBackgroundSize := "contain",
      dataImageBackground := url, <.span("")
    )
  )

  def noHeaderSlide(content: TagOf[HTMLElement]*): TagOf[HTMLElement] = cleanSlide(
    <.section(
      content: _*
    )
  )

  def headerSlide(headerStr: String, content: TagOf[HTMLElement]*): TagOf[HTMLElement] = cleanSlide(
    <.section(
      (header(headerStr, "slide-header") +: content): _*
    )
  )

  def headerSlideLeftAligned(headerStr: String, content: TagOf[HTMLElement]*): TagOf[HTMLElement] = cleanSlide(
    <.section(
      header(headerStr, "slide-header"), <.p(^.textAlign := "left", content.toTagMod), 
    )
  )
  

  def headerSlideWithColumns(headerStr: String)(column1: TagOf[HTMLElement]*)(column2: TagOf[HTMLElement]*): TagOf[HTMLElement] = cleanSlide(
    <.section(
      header(headerStr, "slide-header"),
      <.div(^.cls := "container",
        <.div(^.cls := "col", column1.toTagMod),
        <.div(^.cls := "col", column2.toTagMod)
      ),
    ),
  )

  def getOneThroughX(letter: String, x: String): TagOfHTMLElement = <.span(<.span(letter), <.sub("1"), <.span(", "), <.span(letter), <.sub("2"), <.span(", ...,"), <.span(letter), <.sub(x))
  def getOneThroughN(letter: String): TagOfHTMLElement = getOneThroughX(letter, "n")


  private def rawCode(language: String, codeStr: String): TagOf[HTMLElement] =
    <.code(
      ^.cls := language,
      dataTrim,
      dataNoEscape,
      codeStr
    )

  def bash(codeStr: String): TagOf[HTMLElement] = <.pre(rawCode("Bash", codeStr))
  def scalaC(codeStr: String): TagOf[HTMLElement] = <.pre(rawCode("Scala", codeStr))
  def haskell(codeStr: String): TagOf[HTMLElement] = <.pre(rawCode("Haskell", codeStr))
  def lisp(codeStr: String): TagOf[HTMLElement] = <.pre(rawCode("Lisp", codeStr))
  def cSharp(codeStr: String): TagOf[HTMLElement] = <.pre(rawCode("C#", codeStr))
  def xml(codeStr: String): TagOf[HTMLElement] = <.pre(rawCode("Xml", codeStr))
  def sql(codeStr: String): TagOf[HTMLElement] = <.pre(rawCode("Sql", codeStr))

  private def rawCodeFragment(language: String, codeStr: String): TagOf[HTMLElement] =
    <.pre(
      ^.cls := "fragment fade-in",
      rawCode(language, codeStr)
    )

  def scalaFragment(codeStr: String): TagOf[HTMLElement] = rawCodeFragment("Scala", codeStr)
  def haskellFragment(codeStr: String): TagOf[HTMLElement] = rawCodeFragment("Haskell", codeStr)
  def lispFragment(codeStr: String): TagOf[HTMLElement] = rawCodeFragment("Lisp", codeStr)
  def cSharpFragment(codeStr: String): TagOf[HTMLElement] = rawCodeFragment("C#", codeStr)
  def sqlFragment(codeStr: String): TagOf[HTMLElement] = rawCodeFragment("Sql", codeStr)

  def rawFragment(reveal: String, content: TagOfHTMLElement*): TagOfHTMLElement =
  <.div(
    ^.cls := "fragment " + reveal,
    content.toTagMod,
  )

  def fadeInFragment(content: TagOfHTMLElement*): TagOfHTMLElement = rawFragment("fade-in", content: _*)
    

  private val dataFragmentIndex = VdomAttr("data-fragment-index")

  def colorRedSpan(content: String, index: Option[Int] = None): TagOf[HTMLElement] = {
    index match {
      case Some(a) => <.span(^.cls := "fragment highlight-red", dataFragmentIndex := a, content)
      case None => <.span(^.cls := "fragment highlight-red", content)
    }
  }

  object Enumeration {
   
    object Item {

      def stable(content: TagOf[HTMLElement]): TagOf[HTMLElement] = <.li(content)
      def stable(content: TagOfHTMLElement*): TagOfHTMLElement = <.li(content: _*)
      def stable(content: String): TagOf[HTMLElement] = <.li(<.p(content))
      def fadeIn(content: TagOf[HTMLElement]): TagOf[HTMLElement] = <.li(^.cls := "fragment fade-in", content)
      def fadeIn(content: String): TagOf[HTMLElement] = <.li(^.cls := "fragment fade-in", <.p(content))
      def colorRedIn(content: String): TagOf[HTMLElement] = <.li(^.cls := "fragment highlight-red", <.p(content))
    }

    def apply(head: TagOf[HTMLElement], tail: TagOf[HTMLElement]*): TagOf[HTMLElement] = {
      <.ul(
        (head +: tail): _*
      )
    }
  }

  object OrderedList extends Enumeration {
    def apply(head: TagOf[HTMLElement], tail: TagOf[HTMLElement]*): TagOf[HTMLElement] = {
      <.ol(
        (head +: tail): _*
      )
    }

    def withType(cls: String, head: TagOf[HTMLElement], tail: TagOf[HTMLElement]*): TagOf[HTMLElement] = {
      <.ol(
        ((^.`cls` := cls) +: head +: tail): _*
      )
    }

  }
}
