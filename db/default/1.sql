# --- First database schema

# --- !Ups

CREATE TABLE youtube.videos (
  id                        BIGINT NOT NULL AUTO_INCREMENT,
  title                      VARCHAR(255) NOT NULL,
  description                   VARCHAR(2000) NOT NULL,
  video_id                   VARCHAR(100) NOT NULL,
  playlist_id                   VARCHAR(100) NOT NULL,
  download_path		                VARCHAR(200),
  number_of_retries               INT,
  download_date              TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT pk_videos PRIMARY KEY (id),
  CONSTRAINT unique_video_id UNIQUE(video_id))
;

# --- !Downs

drop table if exists youtube.videos;
