package actor

import actor.YoutubeDownloadManager.{DownloadVideos, Downloading, InQueue, UpdateStatus}
import actor.YoutubeDownloaderProxySupervisor.{DownloadStatus, DownloadVideo, Failed, Succeeded}
import akka.actor.SupervisorStrategy.Restart
import akka.actor._
import com.typesafe.config.ConfigFactory
import net.ceedubs.ficus.Ficus._

import scala.concurrent.duration._

class YoutubeDownloadManager extends Actor with ActorLogging{
  val config = ConfigFactory.load
  val maxParallelDownloads = config.as[Int]("maxParallelVideoDownloads")
  var videosStatusMap = Map.empty[String,DownloadStatus]

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10,withinTimeRange = 10 minutes){
      case _:Exception =>
        Restart
    }



  def receive = {
    case DownloadVideos(videoIds) =>
      log.debug(s"\n------YoutubeDownloadManager received the following videos to download: " +
        s"\n$videoIds" +
        s"\n-----------------------------------------------------------------------")
      videoIds.map {
        videoId=>
          videosStatusMap += videoId->InQueue
      }
      DelegateVideosInQueue()

    case UpdateStatus(videoId,downloadStatus) =>
      DelegateVideosInQueue()
      if(!hasVideosInProgress)
        context.stop(self)


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

    log.debug(s"Delegating videos: $videosToDownload")
    log.debug(s"VideosStatusMap : $videosStatusMap")
    videosToDownload.map{
      videoId=>
        context.actorOf(Props[YoutubeDownloaderProxySupervisor]) ! DownloadVideo(videoId)
        videosStatusMap = videosStatusMap.updated(videoId,Downloading)
    }
  }

  def countOfVideosBeingDownloaded = videosStatusMap.values.count(_ == Downloading)
}

object YoutubeDownloadManager {
  case class DownloadVideos(videoIds: Seq[String])
  private case object InQueue extends DownloadStatus
  private case object Downloading extends DownloadStatus
  case class UpdateStatus(videoId:String,status: DownloadStatus)
}
