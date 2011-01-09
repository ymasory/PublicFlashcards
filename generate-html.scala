import java.io._
import scala.io._

val MetaFilename = ".flashcardset"
val UrlPrefix = "http://github.com/ymasory/PublicFlashcards/raw/master/"
val FlashupExt = "flashup"
val Sep = "/"

val pwd = new File(System.getProperty("user.dir"))
val subdirs = pwd.listFiles filter (_.isDirectory)

val out = new PrintWriter("table.html")
out println "<table>"
out println """
<tr> 
  <th><b>Deck</b></th> 
  <th><b>3"x5"</b></th> 
  <th><b>3"x5" Fronts</b></th> 
  <th><b>3"x5" Backs</b></th> 
  <th><b>Source</b></th> 
</tr>
"""

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
  out println ("<td>" + title + "</td>")
  out println ("<td>link</td>")
  out println ("<td>link</td>")
  out println ("<td>link</td>")
  out println ("<td><a href=\"" + UrlPrefix + dir.getName + Sep + basename + "." + FlashupExt + "\">link</a></td>")
  out println "</tr>"
}

out println "</table>"
out close()
