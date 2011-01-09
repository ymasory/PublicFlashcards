import java.io._
import scala.io._

val MetaFilename = ".flashcardset"
val UrlPrefix = "http://github.com/ymasory/PublicFlashcards/raw/master/"
val FlashupExt = "flashup"
val PdfDir = "bin"
val PdfExt = "pdf"
val Sep = "/"
val FrontsSuffix = "-fronts"
val BacksSuffix= "-backs"

val pwd = new File(System.getProperty("user.dir"))
val subdirs = pwd.listFiles filter (_.isDirectory)

val out = new PrintWriter("table.html")
out println "<table id=\"allflashcards\">"
out println """
<tr> 
<th><b>Deck</b></th> 
<th><b>3"x5"</b></th> 
<th><b>3"x5" Fronts</b></th> 
<th><b>3"x5" Backs</b></th> 
<th><b>Source</b></th> 
</tr>
""".trim

val metaPred = {(file: File) => new File(file, MetaFilename).exists}
val (metas, nots) = subdirs partition metaPred
println("no meta file found for: ")
nots foreach println

for {dir <- metas
     deck <- dir.listFiles
     name = deck.getName
     if name.endsWith("." + FlashupExt)} {
  val lines = Source.fromFile(new File(dir, MetaFilename)).getLines.toList
  val title = lines(0)
  val url = if (lines.length > 1) lines(1) else None
  val basename = name.split("\\.").head

  out println "<tr>"
  out println ("<td class=\"flashcardfile\">" + title + "</td>")
  def printLink(in: String) {
    out println ("<td><a href=\"" + UrlPrefix + dir.getName + Sep + in + "\">link</a></td>")
  }
  printLink(PdfDir + Sep + basename + "." + PdfExt)
  printLink(PdfDir + Sep + basename + FrontsSuffix + "." + PdfExt)
  printLink(PdfDir + Sep + basename + BacksSuffix + "." + PdfExt)
  printLink(basename + "." + FlashupExt)
  out println "</tr>"
}

out println "</table>"
out close()
