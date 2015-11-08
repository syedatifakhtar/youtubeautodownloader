package com.syedatifakhtar.Configuration

import com.typesafe.config.{Config, ConfigFactory}
import collection.JavaConversions._

object PlaylistConfigurations {

  private val config = ConfigFactory.load
  private val playlistConfigList = config.getConfigList("PlaylistConfigs").toList

  private lazy val playlistArgs: Map[String,String] = getMapOfPlaylistConfigs(playlistConfigList)

  def getMapOfPlaylistConfigs(playlistConfigList: List[Config]) = {
    val playlistConfigs = playlistConfigList.foldLeft(Map.empty[String,String]){
      (configMap,config)=>
        configMap + (config.getString("name") -> config.getString("xargs"))
    }
    playlistConfigs
  }

  def getArgsForPlaylist(name: Option[String]): Option[String] = {
      if(playlistArgs.contains(name.get) && name.isDefined)
        playlistArgs(name.get)
    None
  }
}
