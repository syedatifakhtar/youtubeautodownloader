package youtube

import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.libs.ws.WS
import model.YoutubeModel._


class YoutubeWrapper(applicationKey: String) {

  implicit val resourceIdFormat = Json.format[ResourceId]
  implicit val snippetFormat = Json.format[Snippet]
  implicit val playListItemFormat = Json.format[PlaylistItem]


  def getPlaylistsForUser(userId: String)= {
    val log = play.api.Logger
    log.debug(s"Fetching playlists for username: $userId")
    WS
      .url(s"https://www.googleapis.com/youtube/v3/channels?part=contentDetails&forUsername=$userId&key=$applicationKey")
      .get.map {
      response =>
        val parsedResponse = Json.parse(response.body.toString)
        val playLists = (parsedResponse \ "items" \\ "relatedPlaylists").head.as[Map[String,String]]
        playLists.map{value=>
            Playlist(value._1,value._2)
        }
    }
  }

  def getVideosInPlayList(playlistId: String,playlistName: Option[String] = None)= {
    WS
      .url(
        s"https://www.googleapis.com/youtube/v3/playlistItems?" +
          s"part=snippet&maxResults=50&" +
          s"playlistId=$playlistId" +
          s"&key=$applicationKey")
      .get.map {
      response =>
        val parsedResponse = Json.parse(response.body.toString)
        val playListItems = parsedResponse \ "items"
        parseItemsForVideo(playListItems)
    }
  }

  def parseItemsForVideo(items: JsValue) = {
    items.as[JsArray].validate[Seq[PlaylistItem]]
  }
//  def getVideosInPlaylist(id: String) = ???



}


