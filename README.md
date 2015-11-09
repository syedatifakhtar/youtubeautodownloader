Scala and Play! app that uses the youtube API to fetch a given users youtube playlist and then downloads all the videos within those playlists.Perfect for running on the Raspberry PI.Uses youtube-dl to download videos.Need a google youtube API key as of now.

Ive left the service as a one time executable that shuts down and cleans itself automatically after all downloads have finished,this has been done purposely since the app maybe a little too heavy for devices such as the raspberry pi 2 which is what i created this for.

Installation
~~~~~~~~~~~~

1.Install youtube-dl,see steps here-:
https://github.com/rg3/youtube-dl/blob/master/README.md#readme

2.Generate a youtube developer API key from here
https://developers.google.com/youtube/registering_an_application

3.Edit the application.conf file and modify the following config values-:
#userId whose playlist you want to pull
userId = <usernameHere>

4.Start the application jar with the following command -: java -jar YoutubeAutoDownloader.jar

5.You can add it as a cron job if you like as well
http://askubuntu.com/questions/2368/how-do-i-set-up-a-cron-job

4.Building and generating the jar yourself(Optional)-:
You will need to have sbt for this (Brew install sbt)

sbt clean assembly

compiles and creates the jar for you



#applicationKey from google api
applicationKey = <applicationKeyHere>


Configs
~~~~~~~~~

The following configs are available for modification and customization-:

#Set to true for linux devices
ws.acceptAnyCertificate=true

#Number of parallel video downloads you want to have
maxParallelVideoDownloads = 20

#Will timeout and fail the video download if it crosses this limit,valid values are in <hours,minutes,seconds>
maxdownloadwaittime = 1 hour

#Output directory
outputDirectory = "downloads"


youtube-dl configs
~~~~~~~~~~~~~~~~~~~~~~

Youtube dl by itself also has a lot of configuration options,these can be configured in the global Xargs property for all downloads like below

Global Xargs = -i --no-check-certificate --restrict-filenames 

You can also configure Playlist specific youtube-dl configs with additional args (Ie,if you want to download only audio for your music playlists)

PlaylistConfigs = [
  {name:"uploads",xargs:"-x --audio-format aac --embed-thumbnail"}
]
