package actor

import actor.YoutubeDownloadManager.UpdateStatus
import actor.YoutubeDownloaderProxySupervisor._
import akka.actor._
import com.typesafe.config.ConfigFactory
import net.ceedubs.ficus.Ficus._
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.duration._

class YoutubeDownloaderProxySupervisor extends Actor{

  var videoId = ""
  var senderRef: ActorRef = null
  val config = ConfigFactory.load
  val maxDownloadWaitTime = config.as[FiniteDuration]("maxdownloadwaittime")
  context.system.scheduler.schedule(maxDownloadWaitTime,maxDownloadWaitTime,self,DownloadTimeout)

  def receive = {
    case downloadVideo@DownloadVideo(videoId) =>
      senderRef = sender
      this.videoId = videoId
      context.actorOf(Props[YoutubeDownloaderProxy]) ! downloadVideo
    case statusUpdate:DownloadStatus =>
      senderRef ! UpdateStatus(videoId,statusUpdate)
    case DownloadTimeout =>
      senderRef ! UpdateStatus(videoId,Failed("DownloadTimedOut"))
      context.stop(self)
  }

}

object YoutubeDownloaderProxySupervisor {
  case class DownloadVideo(videoId: String)
  trait DownloadStatus
  case object Succeeded extends DownloadStatus
  case class Failed(message: String) extends DownloadStatus
  case object DownloadTimeout

}
