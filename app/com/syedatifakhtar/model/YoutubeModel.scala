package com.syedatifakhtar.model

object YoutubeModel{
  case class Snippet(val title: String,val description: String,resourceId: ResourceId)
  case class ResourceId(val videoId: String)
  case class PlaylistItem(val id: String,val snippet: Snippet)
  case class Playlist(val name: String,val id: String)
}