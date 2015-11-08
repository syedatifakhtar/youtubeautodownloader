package com.syedatifakhtar.actor

import akka.actor.SupervisorStrategy.Restart
import akka.actor._
import com.syedatifakhtar.Configuration.PlaylistConfigurations
import com.syedatifakhtar.actor.YoutubeDownloadManager._
import com.syedatifakhtar.actor.YoutubeDownloaderProxySupervisor.{DownloadStatus, DownloadVideo, Failed, Succeeded}
import com.syedatifakhtar.youtube.YoutubeWrapper
import com.typesafe.config.ConfigFactory
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import net.ceedubs.ficus.Ficus._

import scala.concurrent.duration._

class YoutubeDownloadManager extends Actor with ActorLogging{
  val config = ConfigFactory.load
  val maxParallelDownloads = config.as[Int]("maxParallelVideoDownloads")
  var videosStatusMap = Map.empty[String,DownloadStatus]
  var videosArgsMap = Map.empty[String,Option[String]]
  val applicationKey = config.as[String]("applicationKey")
  val outputDirectory: String = config.as[String]("outputDirectory")
  private val youtubeWrapper = new YoutubeWrapper(applicationKey)



  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10,withinTimeRange = 10 minutes){
      case _:Exception =>
        Restart
    }



  def receive = {
    case DownloadVideos(videoIds,args) =>
      log.debug(s"\n------YoutubeDownloadManager received the following videos to download: " +
        s"\n$videoIds" +
        s"\n-----------------------------------------------------------------------")
      videoIds.map {
        videoId=>
          videosStatusMap += videoId->InQueue
          videosArgsMap += videoId->args
      }
      DelegateVideosInQueue()


    case UpdateStatus(videoId,downloadStatus) =>
      videosStatusMap = videosStatusMap.updated(videoId,downloadStatus)
      DelegateVideosInQueue()
      if(!hasVideosInProgress)
        context.stop(self)

    case DownloadPlaylist(playlistId,playlistName)=>
      val playlistArgs = PlaylistConfigurations.getArgsForPlaylist(playlistName)
      log.debug(s"----------YoutubeManager: Downloading playlist : $playlistName" +
        s"\nargs: $playlistArgs")
      youtubeWrapper.getVideosInPlayList(playlistId,playlistName).map{
        result=>
        val playlistVideoIds = result.get.map{
          playlistItem=>
            playlistItem.snippet.resourceId.videoId
        }
          self ! DownloadVideos(playlistVideoIds,playlistArgs)
      }
  }

  def hasVideosInProgress = videosStatusMap.values.exists(status=>status!=Succeeded || status!=Failed)
  def updateStatus(videoId: String,downloadStatus: DownloadStatus) = {
    videosStatusMap = videosStatusMap.updated(videoId,downloadStatus)
  }
  def DelegateVideosInQueue() = {
    val numberOfVideosToDownload = maxParallelDownloads - countOfVideosBeingDownloaded
    val videosToDownload = videosStatusMap
      .filter(_._2==InQueue)
      .take(numberOfVideosToDownload)
      .keys

    log.info(s"Delegating videos: $videosToDownload")
    log.info(s"VideosStatusMap : $videosStatusMap")
    videosToDownload.map{
      videoId=>
        val args = videosArgsMap.get(videoId).flatten
        context.actorOf(Props[YoutubeDownloaderProxySupervisor]) ! DownloadVideo(videoId,args,outputDirectory)
        videosStatusMap = videosStatusMap.updated(videoId,Downloading)
    }
  }

  def countOfVideosBeingDownloaded = videosStatusMap.values.count(_ == Downloading)
}

object YoutubeDownloadManager {
  case class DownloadVideos(videoIds: Seq[String],optionalXArgs: Option[String]=None)
  case class DownloadPlaylist(playlistId: String,playlistName: Option[String] = None)
  private case object InQueue extends DownloadStatus
  private case object Downloading extends DownloadStatus
  case class UpdateStatus(videoId:String,status: DownloadStatus)
}
