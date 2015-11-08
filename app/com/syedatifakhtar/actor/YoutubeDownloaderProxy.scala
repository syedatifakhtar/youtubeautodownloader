package com.syedatifakhtar.actor

import akka.actor.Actor
import com.syedatifakhtar.actor.YoutubeDownloaderProxySupervisor.{Succeeded, Failed, DownloadVideo}

import scala.sys.process._

class YoutubeDownloaderProxy extends Actor {

  val log = play.api.Logger
  def receive = {
    case DownloadVideo(videoId,args,outputDirectory) =>

      log.debug(s"\n-------------------------------DownloadVideoConfig--------------" +
        s"\nvideoId: $videoId" +
        s"\noutputDirectory: $outputDirectory" +
        s"\nargs: $args" +
        s"\n-------------------------------------------------------")
      log.debug(s"Downloading video with id: $videoId")
      val command = s"sudo youtube-dl -i --no-check-certificate --restrict-filenames " +
        s"https://www.youtube.com/watch?v=$videoId " +
        s"--write-thumbnail " +
        s"-o $outputDirectory/%(uploader)s/%(title)s-%(id)s.%(ext)s " +
          s"${args.getOrElse("")}"

      log.info(s"----------------\nCommand: \n\n\n$command\n---------------------------------------------------")
      val output = command !!

      if(!output.contains("[download] 100%"))
        Failed(s"Downloading of video failed,detailed logs: \n$output")
      sender ! Succeeded
  }

  override def preRestart(reason: Throwable, message: Option[Any]) = {
    if(message.isDefined)
      self ! message.get
  }

}
