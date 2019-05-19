package review_factory

import review_interfaces.MusicReviewSqlliteInterface
import reviews.Review
import scala.collection.mutable.ArrayBuffer

class ReviewFactory(val input_type: String, val review_location: String) {

  private val _input_type: String = input_type

  def get_bulk_review: Array[Review] = {
    val music_review_interface = new MusicReviewSqlliteInterface(review_location)

    val genres: Map[Int, String] = music_review_interface.getGenres
    val artists: Map[Int, String] = music_review_interface.getArtists
    val music_labels: Map[Int, String] = music_review_interface.getMusicLabels
    val reviews: Map[Int, Map[String, String]] = music_review_interface.getReviews
    val year: Map[Int, Int] = music_review_interface.getYear
    val review_text: Map[Int, String] = music_review_interface.getReviewText

    var o: ArrayBuffer[Review] = ArrayBuffer()
    for (i <- reviews.keys) {
      var r: Review =  new Review(reviewid = i,
                      artist = artists(i),
                      content = review_text(i),
                      genre = genres(i),
                      music_label = music_labels(i),
                      title = reviews(i)("title"),
                      url = reviews(i)("url"),
                      score = reviews(i)("score").toFloat,
                      best_new_music = reviews(i)("best_new_music").toInt,
                      author = reviews(i)("author"),
                      author_type = reviews(i)("author_type"),
                      pub_weekday = reviews(i)("pub_weekday").toInt,
                      pub_day = reviews(i)("pub_day").toInt,
                      pub_month = reviews(i)("pub_month").toInt,
                      pub_year = reviews(i)("pub_year").toInt,
                      year = year(i))
      o += r
    }

    o.toArray
  }

}
