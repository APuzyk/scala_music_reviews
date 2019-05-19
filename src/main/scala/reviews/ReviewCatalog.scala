package reviews

import java.io.File

import helpers.TextParser
import review_factory.ReviewFactory
import java.io.{File, FileWriter, BufferedWriter}

class ReviewCatalog (val review_location: String,
                          val input_type: String,
                          val output_location: String) {
{
  private val _rf: ReviewFactory = new ReviewFactory(input_type, review_location)

  private val _tp: TextParser = new TextParser
  private val reviews: Array[String] = _rf.get_bulk_review

  for(r <- reviews) r.cleanseAndParseContent

  private var _word_dictionary: Map[Int, String] = _tp.createDict(reviews)

  def write_wordDictionary: Unit = {
    val file = new File(output_location.concat("/word_dict.txt"))
    val bw = new BufferedWriter(new FileWriter(file))
    var o: String = ""
    for ((k,v) <- _word_dictionary) {
      o = o + k.toString + "," + v + "\n"
    }
    bw.write()
    bw.close()
  }
}

}
