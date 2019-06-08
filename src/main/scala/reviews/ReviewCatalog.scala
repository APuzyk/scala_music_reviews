package reviews

import helpers.TextParser
import review_factory.ReviewFactory
import java.io.{BufferedWriter, File, FileWriter}
import java.util.UUID.randomUUID

import review_interfaces.MusicReviewSqlliteInterface


class ReviewCatalog (val review_location: String,
                          val input_type: String,
                          val output_location: String) {
  private val _rf: ReviewFactory = new ReviewFactory(input_type, review_location)

  private val _tp: TextParser = new TextParser
  private val reviews: Array[Review] = _rf.get_bulk_review
  private val _output_location = output_location
  private val _uuid = randomUUID().toString
  val sql_interface = new MusicReviewSqlliteInterface("sqllite", _output_location)
  sql_interface.writeRunId(_uuid)

  for(r <- reviews) r.cleanseAndParseContent

  private var _word_dictionary: Map[String, Int] = _tp.createDict(reviews)

  def write_wordDictionary: Unit = {
    println("\nwriting word dict to sqlite db...")
    val sql_interface = new MusicReviewSqlliteInterface("sqllite", _output_location)
    sql_interface.writeWordDict(_word_dictionary, _uuid)
    println("Completed writing word dict\n")
  }


  def createTextToDictMap: Unit = {
    for (r <- reviews) r.createContentVec(_word_dictionary)
  }

  def write_reviews: Unit = {
    println("\nwriting review to sqlite db...")
    val sql_interface = new MusicReviewSqlliteInterface("sqllite", _output_location)
    sql_interface.writeReviewData(reviews, _uuid)
    println("Completed writing reviews\n")
  }

  def completeRun: Unit = {
    val sql_interface = new MusicReviewSqlliteInterface("sqllite", _output_location)
    sql_interface.completeRun(_uuid)
  }
}
