package review_interfaces

import java.sql.{Connection, DriverManager, ResultSet}
import java.util.UUID

import reviews.Review

class MusicReviewSqlliteInterface(var input_type: String, var review_location: String) {
  if (input_type != "sqllite") print("Inputs other than sqllite not currently supported")

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

  def writeWordDict (word_dict: Map[String, Int], uuid: String): Boolean = {

    Class.forName("org.sqlite.JDBC") //idk why I need to do this
    var _connection: Connection = null
    _connection = DriverManager.getConnection(_url)
    val statement = _connection.createStatement()
    //sql lite auto defines rowid
    val create_statement = "CREATE TABLE IF NOT EXISTS word_dictionary (\n" +
                            "	word_index BIGINT,\n" +
                            "	word VARCHAR,\n" +
                            " uuid VARCHAR\n" +
                            ");"
    val o = statement.execute(create_statement)
    val insert_sql = "INSERT INTO word_dictionary(word_index, word, uuid) VALUES(?, ?, ?)"
    val prepared_statement = _connection.prepareStatement(insert_sql)
    prepared_statement.setString(3, uuid)

    for((k, v) <- word_dict){
      prepared_statement.setLong(1, v)
      prepared_statement.setString(2, k)
      prepared_statement.executeUpdate()
    }
    o
  }

  def writeReviewData (reviews: Array[Review], uuid: String): Boolean = {

    Class.forName("org.sqlite.JDBC") //idk why I need to do this
    var _connection: Connection = null
    _connection = DriverManager.getConnection(_url)
    val statement = _connection.createStatement()
    //sql lite auto defines rowid
    val create_statement = "CREATE TABLE IF NOT EXISTS prepared_reviews (\n" +
      "	reviewid BIGINT,\n" +
      "	artist VARCHAR,\n" +
      " genre VARCHAR,\n" +
      " music_label VARCHAR,\n" +
      " title VARCHAR,\n" +
      " url VARCHAR,\n" +
      " score DOUBLE,\n" +
      " best_new_music INT,\n" +
      " author VARCHAR,\n" +
      " author_type VARCHAR,\n" +
      " pub_weekday INT,\n" +
      " pub_day INT,\n" +
      " pub_month INT,\n" +
      " pub_year INT,\n" +
      " year INT,\n" +
      " contentVec VARCHAR,\n" +
      " uuid VARCHAR\n" +
      ");"
    val o = statement.execute(create_statement)
    val create_ts: Long = System.currentTimeMillis / 1000
    val insert_sql = "INSERT INTO prepared_reviews(reviewid," +
                                                  "artist," +
                                                  "genre," +
                                                  "music_label," +
                                                  "title," +
                                                  "url," +
                                                  "score," +
                                                  "best_new_music,"+
                                                  "author,"+
                                                  "author_type,"+
                                                  "pub_weekday,"+
                                                  "pub_day,"+
                                                  "pub_month,"+
                                                  "pub_year,"+
                                                  "year,"+
                                                  "contentVec,"+
                                                  "uuid) " +
                      "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

    val prepared_statement = _connection.prepareStatement(insert_sql)
    prepared_statement.setString(17, uuid)

    for(r <- reviews){
      prepared_statement.setInt(1, r.get_reviewid)
      prepared_statement.setString(2, r.get_artist)
      prepared_statement.setString(3, r.get_genre)
      prepared_statement.setString(4, r.get_music_label)
      prepared_statement.setString(5, r.get_title)
      prepared_statement.setString(6, r.get_url)
      prepared_statement.setDouble(7, r.get_score)
      prepared_statement.setInt(8, r.get_best_new_music)
      prepared_statement.setString(9, r.get_author)
      prepared_statement.setString(10, r.get_author_type)
      prepared_statement.setInt(11, r.get_pub_weekday)
      prepared_statement.setInt(12, r.get_pub_day)
      prepared_statement.setInt(13, r.get_pub_month)
      prepared_statement.setInt(14, r.get_pub_year)
      prepared_statement.setInt(15, r.get_year)
      prepared_statement.setString(16, r.get_content_vec_as_string)
      prepared_statement.executeUpdate()
    }
    o
  }
}