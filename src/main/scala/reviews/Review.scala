package reviews

import helpers.TextParser

class Review(val reviewid: Int,
             val artist: String,
             val content: String,
             val genre: String,
             val music_label: String,
             val title: String,
             val url: String,
             val score: Float,
             val best_new_music: Int,
             val author: String,
             val author_type: String,
             val pub_weekday: Int,
             val pub_day: Int,
             val pub_month: Int,
             val pub_year: Int,
             val year: Int) {

  private val _reviewid: Int = reviewid
  private val _artist: String = artist
  private val _content: String = content
  private val _genre: String = genre
  private val _music_label: String = music_label
  private val _title: String = title
  private val _url: String = url
  private val _score: Float = score
  private val _best_new_music: Int = best_new_music
  private val _author: String = author
  private val _author_type: String = author_type
  private val _pub_weekday: Int = pub_weekday
  private val _pub_day: Int = pub_day
  private val _pub_month: Int = pub_month
  private val _pub_year: Int = pub_year
  private val _year: Int = year
  private var _cleansedContent: Array[String] = Array()

  def get_reviewid: Int = {
    _reviewid
  }
  
  def get_artist: String = {
    _artist
  }
  
  def get_content: String = {
    _content
  }
  
  def get_genre: String = {
    _genre
  }
  
  def get_music_label: String = {
    _music_label
  }
  
  def get_title: String = {
    _title
  }
  
  def get_url: String = {
    _url
  }
  
  def get_score: Float = {
    _score
  }
  
  def get_best_new_music: Int = {
    _best_new_music
  }
  
  def get_author: String = {
    _author
  }
  
  def get_author_type: String = {
    _author_type
  }
  
  def get_pub_weekday: Int = {
    _pub_weekday
  }
  
  def get_pub_day: Int = {
    _pub_day
  }
  
  def get_pub_month: Int = {
    _pub_month
  }
  
  def get_pub_year: Int = {
    _pub_year
  }
  
  def get_year: Int = {
    _year
  }

  def cleanseAndParseContent {
    val tp = new TextParser
    _cleansedContent = tp.cleanseAndParseText(_content)
  }

  def get_cleansedContent: Array[String] = {
    _cleansedContent
  }
}
