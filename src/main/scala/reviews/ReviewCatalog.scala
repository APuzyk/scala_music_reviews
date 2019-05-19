package reviews

import helpers.TextParser
import review_factory.ReviewFactory

class ReviewCatalog (val review_location: String,
                          val input_type: String,
                          val output_location: String) {
{
  private val _rf: ReviewFactory = new ReviewFactory(input_type, review_location)

  private val _tp: TextParser = new TextParser
  private val reviews = _rf.get_bulk_review

  for(r <- reviews) r.cleanseAndParseContent

  private var _word_dictionary = _tp.createDict(reviews)

  def write_wordDictionary

}
