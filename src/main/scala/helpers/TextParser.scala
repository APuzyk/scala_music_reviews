package helpers

import reviews.Review
import scala.collection.mutable.ArrayBuffer

class TextParser {
  def cleanseAndParseText (text: String): Array[String] = {
    val ctext = cleanseText(text)
    val o = ctext.split(" ")
    o
  }

  def cleanseText (text: String): String = {
    var o = text.replaceAll("""\p{Punct}""", "")
    o = o.toLowerCase()
    o
  }

  def createDict (reviews: Array[Review]): Map[String, Int] = {
    // Get words for dict
    var words: ArrayBuffer[String] = ArrayBuffer()
    var o: Map[String, Int] = Map()

    for (r <- reviews) {
      r.get_cleansedContent.foreach(words += _)
    }
    words = words.distinct

    var i: Int = 1
    for (w <- words) {
      o += (w -> i)
      i += 1
    }

    o
  }
}
