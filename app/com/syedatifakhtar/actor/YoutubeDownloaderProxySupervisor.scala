package com.syedatifakhtar.actor

import akka.actor.SupervisorStrategy.Stop
import akka.actor._
import com.syedatifakhtar.actor.YoutubeDownloadManager.UpdateStatus
import com.syedatifakhtar.actor.YoutubeDownloaderProxySupervisor.{DownloadStatus, DownloadTimeout, DownloadVideo, Failed}
import com.typesafe.config.ConfigFactory
import net.ceedubs.ficus.Ficus._
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.duration._

class YoutubeDownloaderProxySupervisor extends Actor{

  var videoId = ""
  var senderRef: ActorRef = null
  val config = ConfigFactory.load
  val maxDownloadWaitTime = config.as[FiniteDuration]("maxdownloadwaittime")
  val log = play.api.Logger
  context.system.scheduler.schedule(maxDownloadWaitTime,maxDownloadWaitTime,self,DownloadTimeout)

  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10,withinTimeRange = 10 minutes){
    case e:Exception=>
      log.debug(s"Got exception while downloading video: $videoId\nError:$e\n failing!")
      senderRef ! UpdateStatus(videoId,Failed(s"$e"))
      context.stop(self)
      Stop

  }
  def receive = {
    case downloadVideo:DownloadVideo =>
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
  case class DownloadVideo(videoId: String,args: Option[String] = None,outputDirectory : String = ".")
  trait DownloadStatus
  case object Succeeded extends DownloadStatus
  case class Failed(message: String) extends DownloadStatus
  case object DownloadTimeout

}
