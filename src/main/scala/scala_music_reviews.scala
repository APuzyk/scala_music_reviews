import review_factory.ReviewFactory
import helpers.TextParser

object ScalaMusicReviews{
  def main(args: Array[String]): Unit = {
    val rf: ReviewFactory = new ReviewFactory("blah")
    val tp: TextParser = new TextParser

    val a = rf.get_bulk_review
    for(e <- a) e.cleanseAndParseContent
    val word_dictionary = tp.createDict(a)

    println(word_dictionary)
    println(a(0).get_content)
    a(0).get_cleansedContent.foreach(println(_))
  }
}
