package youtube

import java.sql.Connection
import play.api.Play.current

import play.api.db.DB

class YoutubeDownloadedVideosDB {

  import anorm._

  def execute[A](f:Connection=>A ) = {
    DB.withConnection(f)
  }
  DB.withConnection { implicit c =>
    val result: Boolean = SQL("Select 1").execute()
  }

}
