import java.io.File

import actor.YoutubeDownloadManager
import akka.actor.{ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import net.ceedubs.ficus.Ficus._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.core.StaticApplication
import youtube.YoutubeWrapper


object Main extends StaticApplication(new File(".")) with App {

  val log = play.api.Logger
  val config = ConfigFactory.load
  val username = config.as[String]("userId")
  val applicationKey = config.as[String]("applicationKey")
  val youtubeWrapper = new YoutubeWrapper(applicationKey)
  val playlistsFuture = youtubeWrapper.getPlaylistsForUser(username)
  val system = ActorSystem("youtubeDownloadSystem")
  val youtubeDownloadManager = system.actorOf(Props[YoutubeDownloadManager])
  val result = playlistsFuture
    .map(
      _.map{
        playlist=>
          log.debug(s"Fetching videos in playlist: $playlist")
          val playlistItems = youtubeWrapper.getVideosInPlayList(playlist.id,Some(playlist.name))
          .map(_.get)
          playlistItems
      }).recover{
    case e=> log.debug(s"Failed to fetch videos for users playlists,got error: $e")
  }

  println("Hello world")

}
