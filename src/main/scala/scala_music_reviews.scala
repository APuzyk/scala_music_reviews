import reviews.ReviewCatalog

object ScalaMusicReviews{
  def main(args: Array[String]): Unit = {
    val rc: ReviewCatalog = new ReviewCatalog(review_location = "/home/apuzyk/Projects/music_reviews/data/database.sqlite",
                                              input_type = "sqllite",
                                              output_location = "/home/apuzyk/Projects/music_reviews/data/database.sqlite")
    rc.createTextToDictMap
    rc.write_wordDictionary
    rc.write_reviews
  }
}


