package helpers

import reviews.Review
import scala.collection.mutable.ArrayBuffer

class TextParser {
  def cleanseAndParseText (text: String): Array[String] = {
    val ctext = cleanseText(text)
    val o = ctext.split(" ")
    o.map(_.replaceAll("""\p{IsWhite_Space}""", ""))
    o.map(_.trim())

    o
  }

  def cleanseText (text: String): String = {
    var o = text.replaceAll("""\p{IsPunctuation}""", "")
    o = o.replaceAll("\\n", "")
    o = o.replaceAll("\\r", "")
    o = o.toLowerCase()
    o
  }

  def createDict (reviews: Array[Review], min_word_count: Int = 5) : Map[String, Int] = {
    // Get words for dict
    var words: ArrayBuffer[String] = ArrayBuffer()
    var o: Map[String, Int] = Map()
    var words_group: Map[String, Int] = Map()

    for (r <- reviews) {
      r.get_cleansedContent.foreach(words += _)
    }
    words_group = words.groupBy(identity).mapValues(_.size)

    for ((k, v) <- words_group) {
      if (v < min_word_count || k == ""){
        words_group -= k
      }
    }


    var i: Int = 1
    for ((w, v) <- words_group) {
      o += (w -> i)
      i += 1
    }

    o
  }
}
