package com.syedatifakhtar.youtube

import java.sql.Connection

import anorm.Sql
import play.api.Play.current
import play.api.db.DB

class YoutubeDownloadedVideosDB {

  def execute[A](f:Connection=>A ) = {
    DB.withConnection(f)
  }

 def executeSQL(sql: Sql) = {
   execute{
     implicit c=> sql.execute()
   }
 }

}
