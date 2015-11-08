package com.syedatifakhtar

import java.io.File

import akka.actor.{ActorSystem, Props}
import com.syedatifakhtar.actor.YoutubeDownloadManager
import com.syedatifakhtar.actor.YoutubeDownloadManager.DownloadPlaylist
import com.syedatifakhtar.youtube.YoutubeWrapper
import com.typesafe.config.ConfigFactory
import net.ceedubs.ficus.Ficus._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.core.StaticApplication


object Main extends StaticApplication(new File(".")){

  def main(args: Array[String]) = {
    println("---------------------Application started----------------------------")
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
        playlists=>
      playlists.map{
        playlist=>
        youtubeDownloadManager ! DownloadPlaylist(playlist.id,Some(playlist.name))}).recover {
      case e => log.debug(s"Failed to fetch videos for users playlists,got error: $e")
    }
  }
}
