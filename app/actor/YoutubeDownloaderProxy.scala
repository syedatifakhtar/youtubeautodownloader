package actor

import actor.YoutubeDownloaderProxySupervisor.{DownloadVideo, Failed, Succeeded}
import akka.actor.Actor

import scala.sys.process._

class YoutubeDownloaderProxy extends Actor {

  val log = play.api.Logger
  def receive = {
    case DownloadVideo(videoId) =>

      log.debug(s"Downloading video with id: $videoId")
      val output = s"sudo youtube-dl -i --no-check-certificate --restrict-filenames https://www.youtube.com/watch?v=$videoId " +
        s"-o downloads/%(uploader)s/%(title)s-%(id)s.%(ext)s" !!

      if(!output.contains("[download] 100%"))
        Failed(s"Downloading of video failed,detailed logs: \n$output")
      sender ! Succeeded
  }

  override def preRestart(reason: Throwable, message: Option[Any]) = {
    if(message.isDefined)
      self ! message.get
  }

}
