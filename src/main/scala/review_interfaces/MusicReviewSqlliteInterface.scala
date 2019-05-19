package review_interfaces

import java.sql.{Connection, DriverManager, ResultSet}

class MusicReviewSqlliteInterface(var review_location: String = "/home/apuzyk/Projects/music_reviews/data/database.sqlite") {

  private val _url: String = "jdbc:sqlite:".concat(review_location)

  def getIntStringMapFromResultSet (resultSet: ResultSet, to_get_name: String) : Map[Int, String] = {
    var o: Map[Int, String] = Map()
    while (resultSet.next()) {
      o += (resultSet.getInt("reviewid") -> resultSet.getString(to_get_name))
    }

    o
  }

  def getResultSet (query: String): ResultSet = {
    print(1)

    Class.forName("org.sqlite.JDBC") //idk why I need to do this
    print(2)
    var _connection: Connection = null
    print(3)
    _connection = DriverManager.getConnection(_url)
    print(4)
    val statement = _connection.createStatement()
    print(5)
    val o = statement.executeQuery(query)
    print(6)
    //_connection.close()
    print(7)
    o
  }

  def getArtists : Map[Int, String] = {
    val query = "SELECT reviewid, artist FROM artists"
    val resultSet = getResultSet(query)

    getIntStringMapFromResultSet(resultSet, "artist")
  }

  def getReviewText : Map[Int, String] = {
    val query = "SELECT reviewid, content FROM content"
    val resultSet = getResultSet(query)

    getIntStringMapFromResultSet(resultSet, "content")
  }

  def getGenres : Map[Int, String] = {
    val query = "SELECT reviewid, genre FROM genres"
    val resultSet = getResultSet(query)

    getIntStringMapFromResultSet(resultSet, "genre")
  }

  def getMusicLabels: Map[Int, String] = {
    val query = "SELECT reviewid, label FROM labels"
    val resultSet = getResultSet(query)

    getIntStringMapFromResultSet(resultSet, "label")
  }

  def getYear: Map[Int, Int] = {
    val query = "SELECT reviewid, year from years"
    val resultSet = getResultSet(query)

    var o: Map[Int, Int] = Map()

    while(resultSet.next()){
      o += (resultSet.getInt("reviewid") -> resultSet.getInt("year"))
    }
    o
  }

  def getReviews: Map[Int, Map[String, String]] = {
    val query = "SELECT reviewid, title, url, score, best_new_music, author, author_type, pub_weekday, pub_day, pub_month, pub_year from reviews"
    val resultSet = getResultSet(query)

    var o: Map[Int, Map[String, String]] = Map()

    while(resultSet.next()) {
      var val_map: Map[String, String] = Map()

      val_map += ("title" -> resultSet.getString("title"))
      val_map += ("url" -> resultSet.getString("url"))
      val_map += ("score" -> resultSet.getFloat("score").toString)
      val_map += ("best_new_music" -> resultSet.getInt("best_new_music").toString)
      val_map += ("author" -> resultSet.getString("author"))
      val_map += ("author_type" -> resultSet.getString("author_type"))
      val_map += ("pub_weekday" -> resultSet.getInt("pub_weekday").toString)
      val_map += ("pub_day" -> resultSet.getInt("pub_day").toString)
      val_map += ("pub_month" -> resultSet.getInt("pub_month").toString)
      val_map += ("pub_year" -> resultSet.getInt("pub_year").toString)

      o += (resultSet.getInt("reviewid") -> val_map)
    }

    o
  }
}