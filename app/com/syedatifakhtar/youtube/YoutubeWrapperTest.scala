package com.syedatifakhtar.youtube
import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.{JsError, Json}

class YoutubeWrapperSpec extends FlatSpec with Matchers {

  
  val testJson = """[
    {
        "etag": "\"0KG1mRN7bm3nResDPKHQZpg5-do/v63sEy1Q4jSJiecYZRclyzOxyWw\"",
        "id": "FL-Amd-FKkuKlEQy__N02r_vTm6CxW_tJy",
        "kind": "com.syedatifakhtar.youtube#playlistItem",
        "snippet": {
            "channelId": "UC4YtIyiwYth9ay6BWE3fUzg",
            "channelTitle": "Atif Akhtar",
            "description": "music",
            "playlistId": "FL4YtIyiwYth9ay6BWE3fUzg",
            "position": 0,
            "publishedAt": "2015-11-05T19:40:08.000Z",
            "resourceId": {
                "kind": "com.syedatifakhtar.youtube#video",
                "videoId": "98P-gu_vMRc"
            },
            "thumbnails": {
                "default": {
                    "height": 90,
                    "url": "https://i.ytimg.com/vi/98P-gu_vMRc/default.jpg",
                    "width": 120
                },
                "high": {
                    "height": 360,
                    "url": "https://i.ytimg.com/vi/98P-gu_vMRc/hqdefault.jpg",
                    "width": 480
                },
                "medium": {
                    "height": 180,
                    "url": "https://i.ytimg.com/vi/98P-gu_vMRc/mqdefault.jpg",
                    "width": 320
                }
            },
            "title": "ELO - Mr.Blue Sky (Original Promo)"
        }
    },
    {
        "etag": "\"0KG1mRN7bm3nResDPKHQZpg5-do/_E9yMaioOiJqavUteCtq0ca_vik\"",
        "id": "FL-Amd-FKkuKkoYWX3MNV8qLeX3nHyghsS",
        "kind": "com.syedatifakhtar.youtube#playlistItem",
        "snippet": {
            "channelId": "UC4YtIyiwYth9ay6BWE3fUzg",
            "channelTitle": "Atif Akhtar",
            "description": "Scottish electrio Chvrches perform Arctic Monkeys' Do I Wanna Know? for triple j's Like A Version\nSubscribe: http://tripj.net/151BPk6\n\nLike A Version is a segment on Australian radio station triple j. Every Friday morning a musician or band comes into the studio to play one of their own songs and a cover of a song they love. \n\nSince 2004, many artists have participated, including Dirty Projectors, Ben Folds, The Temper Trap, Alt-J, Regina Spektor, Bon Iver, The Kooks and Hilltop Hoods. You can watch past sessions here: http://tripj.net/ZMSMjL\n\nYou can stream triple j online at http://www.triplej.net.au",
            "playlistId": "FL4YtIyiwYth9ay6BWE3fUzg",
            "position": 1,
            "publishedAt": "2014-08-24T20:05:36.000Z",
            "resourceId": {
                "kind": "com.syedatifakhtar.youtube#video",
                "videoId": "CjwwmFrsX_E"
            },
            "thumbnails": {
                "default": {
                    "height": 90,
                    "url": "https://i.ytimg.com/vi/CjwwmFrsX_E/default.jpg",
                    "width": 120
                },
                "high": {
                    "height": 360,
                    "url": "https://i.ytimg.com/vi/CjwwmFrsX_E/hqdefault.jpg",
                    "width": 480
                },
                "medium": {
                    "height": 180,
                    "url": "https://i.ytimg.com/vi/CjwwmFrsX_E/mqdefault.jpg",
                    "width": 320
                },
                "standard": {
                    "height": 480,
                    "url": "https://i.ytimg.com/vi/CjwwmFrsX_E/sddefault.jpg",
                    "width": 640
                }
            },
            "title": "Chvrches cover Arctic Monkeys' 'Do I Wanna Know?' for Like A Version"
        }
    },
    {
        "etag": "\"0KG1mRN7bm3nResDPKHQZpg5-do/UcKzPyD1YUkG9Gxg92KYICSpr6w\"",
        "id": "FL-Amd-FKkuKkrqXADvVvY50yjBpuJj8Kj",
        "kind": "com.syedatifakhtar.youtube#playlistItem",
        "snippet": {
            "channelId": "UC4YtIyiwYth9ay6BWE3fUzg",
            "channelTitle": "Atif Akhtar",
            "description": "PEOPLE ARE AWESOME T-SHIRT: http://instagram.com/p/Yak47SyEXY/\n\nFollow the best and biggest motivation page on facebook here -  http://www.facebook.com/sportsmotivation2\nFor a unique customized approach to fat loss with nutrition and workout routines: http://tiny.cc/kylecfl\n\n\n\nI do not own the rights to this song or any of the material used in this video.\n\nSong: Sail - Awolnation(Buy it on iTunes)\n\nAll the clips used. Check out the originals and support their channels!\nLes Chevaliers du Ciel HD Promo http://www.com.syedatifakhtar.youtube.com/watch?v=HEe3xfWfkG8&list=PL0593943EAB04B98C&index=1&feature=plpp_video\nLondon Lights http://www.com.syedatifakhtar.youtube.com/watch?v=rWxf0FJFwD0&list=PL0593943EAB04B98C&index=7&feature=plpp_video\nadidas Outdoor - Lukas Irmler Part 4: Sunrise on Table Mountain http://www.com.syedatifakhtar.youtube.com/watch?v=25Top07tHGM&list=PL0593943EAB04B98C&index=20&feature=plpp_video\nErik Mukhametshin 2010 http://www.com.syedatifakhtar.youtube.com/watch?v=U6pyy8SQbtY&list=PL0593943EAB04B98C&index=25&feature=plpp_video\nThey're Not Paranoid - Jeremy Marinas, Doeni, Kyle Cordova, Gui DaSilva, Tyler Santos http://www.com.syedatifakhtar.youtube.com/watch?v=rm3dR-5P2hA\nBad Wall Flip Fail - Slow Motion http://www.com.syedatifakhtar.youtube.com/watch?v=7wyQN5rYOBA&list=PL0593943EAB04B98C&index=27&feature=plpp_video\nTom Wallisch - Dew Tour Breckenridge GoPro Edit http://www.com.syedatifakhtar.youtube.com/watch?v=vhHBr7bjyVA&list=PL0593943EAB04B98C&index=5&feature=plpp_video\nSalt Boarding - Blank Snowboards http://www.com.syedatifakhtar.youtube.com/watch?v=PJqtqK9GSg4&list=PL0593943EAB04B98C&index=28&feature=plpp_video\nNick Vujicic - \"Something More\" Music Video http://www.com.syedatifakhtar.youtube.com/watch?v=GrV_ZvwZRvw&feature=my_liked_videos&list=LLphzEPxUMKVL9doksX9cNIg\nRasmus Ott - Source http://www.com.syedatifakhtar.youtube.com/watch?v=UvndqY3DVyU&list=PL0593943EAB04B98C&index=31&feature=plpp_video\nDownhill extreme: Rollerman overtaking motorcycle! http://www.com.syedatifakhtar.youtube.com/watch?v=cNVslA7T2q8&list=PL0593943EAB04B98C&index=2&feature=plpp_video\n(HD) Hamit Altintop AMAZING VOLLEY fifa best goal 2010 http://www.com.syedatifakhtar.youtube.com/watch?v=VdmpjEF2Zlk&list=PL0593943EAB04B98C&index=6&feature=plpp_video\nFree Soloing with Alex Honnold http://www.com.syedatifakhtar.youtube.com/watch?v=yzA5S-5w13M&list=PL0593943EAB04B98C&index=15&feature=plpp_video\nHD: Super Slo-mo Surfer! - South Pacific - BBC Two http://www.com.syedatifakhtar.youtube.com/watch?v=7BOhDaJH0m4&list=PL0593943EAB04B98C&index=16&feature=plpp_video\nHD-NBA 09 Dunk Contest-Nate Robinson Dunks Over Dwight Howard http://www.com.syedatifakhtar.youtube.com/watch?v=RjDmyW4RJ64&list=PL0593943EAB04B98C&index=12&feature=plpp_video\nI Believe I can Fly Movie Trailer http://www.com.syedatifakhtar.youtube.com/watch?v=NuoKGJUAANk&list=PL0593943EAB04B98C&index=17&feature=plpp_video\nWorld's Largest Rope Swing http://www.com.syedatifakhtar.youtube.com/watch?v=4B36Lr0Unp4&list=PL0593943EAB04B98C&index=19&feature=plpp_video\nHD Slo-Motion Skydives http://www.com.syedatifakhtar.youtube.com/watch?v=MuB0NpMOaMs&list=PL0593943EAB04B98C&index=36&feature=plpp_video\nSkydive 4LOKO 2011 http://www.com.syedatifakhtar.youtube.com/watch?v=J2cOe83IRjk&list=PL0593943EAB04B98C&index=37&feature=plpp_video\nLongboarding Freeride http://www.com.syedatifakhtar.youtube.com/watch?v=OW-J2pQrlsw&list=PL0593943EAB04B98C&index=39&feature=plpp_video\nGoPro + F18 = AWESOME http://www.com.syedatifakhtar.youtube.com/watch?v=qf_hdFSnOEI&list=PL0593943EAB04B98C&index=32&feature=plpp_video\nBest of 2011: Slow Motion http://www.com.syedatifakhtar.youtube.com/watch?v=kGIjetX6TBk&list=PL0593943EAB04B98C&index=43&feature=plpp_video\nYouri Zoon Kitesurfing - The Ultimate Reward http://www.com.syedatifakhtar.youtube.com/watch?v=jLsqPj8yTjg&list=PL0593943EAB04B98C&index=42&feature=plpp_video\nHä_Wie!. 2010 Slow Motion Reel - Bboy Bboying Breakdance (HD Quality) http://www.com.syedatifakhtar.youtube.com/watch?v=zL-kavErXvE&list=PL0593943EAB04B98C&index=41&feature=plpp_video\nSlackline WorldCup 2011 - GIBBON's Cut http://www.com.syedatifakhtar.youtube.com/watch?v=PqNhiP3i3-E&list=PL0593943EAB04B98C&index=40&feature=plpp_video\n\n\nextra tags\nPEOPLE ARE AWESOME 2012 - 2013\nPEOPLE ARE AWESOME 2012 - 2013\nPEOPLE ARE AWESOME 2012 - 2013\nPEOPLE ARE AMAZING\nPEOPLE ARE AMAZING\nAWESOME PEOPLE DOING AMAZING THINGS\nAWESOME PEOPLE DOING AMAZING THINGS\nPEOPLE ARE AWESOME 2012 PEOPLE ARE AWESOME 2013 AWESOME PEOPLE GOPRO HD REDBULL AMAZING VIDEO NEW COMPILATION BEST CRAZY STUNTS SNOWBOARDING MOTOCROSS SURFING PARKOUR CAMERA REEL SKIING SKATEBOARDING BMX SKYDIVING SNOWMOBILE KAYAK BIKING EXTREME SPORTS SNOWBOARD FREERUN SKI JUMP CLIFF CLIFFJUMP COMPILATION MONTAGE OF AWESOME PEOPLE DOING AWESOME THINGS\nAMAZING PEOPLE DOING AWESOME THINGS 2012 2013\nawesome people 2012 awesome people 2013 awesome compilation\npeople are awesome 2012 - 2013",
            "playlistId": "FL4YtIyiwYth9ay6BWE3fUzg",
            "position": 2,
            "publishedAt": "2013-06-13T19:14:00.000Z",
            "resourceId": {
                "kind": "com.syedatifakhtar.youtube#video",
                "videoId": "W3OQgh_h4U4"
            },
            "thumbnails": {
                "default": {
                    "height": 90,
                    "url": "https://i.ytimg.com/vi/W3OQgh_h4U4/default.jpg",
                    "width": 120
                },
                "high": {
                    "height": 360,
                    "url": "https://i.ytimg.com/vi/W3OQgh_h4U4/hqdefault.jpg",
                    "width": 480
                },
                "maxres": {
                    "height": 720,
                    "url": "https://i.ytimg.com/vi/W3OQgh_h4U4/maxresdefault.jpg",
                    "width": 1280
                },
                "medium": {
                    "height": 180,
                    "url": "https://i.ytimg.com/vi/W3OQgh_h4U4/mqdefault.jpg",
                    "width": 320
                },
                "standard": {
                    "height": 480,
                    "url": "https://i.ytimg.com/vi/W3OQgh_h4U4/sddefault.jpg",
                    "width": 640
                }
            },
            "title": "PEOPLE ARE AWESOME 2013 FULL HD"
        }
    },
    {
        "etag": "\"0KG1mRN7bm3nResDPKHQZpg5-do/tRLABo6oPaqdBMCVBtzGXG6Ugzk\"",
        "id": "FL-Amd-FKkuKl8ppeZ34TnrOr-BNqP_2P6",
        "kind": "com.syedatifakhtar.youtube#playlistItem",
        "snippet": {
            "channelId": "UC4YtIyiwYth9ay6BWE3fUzg",
            "channelTitle": "Atif Akhtar",
            "description": ".\n\nDVD available from http://amzn.to/wINQla\n\nMstislav Rostropovich plays the Prelude from Bach's Cello Suite No. 1 in G major, BWV 1007.  Filmed at the Basilique Sainte Madeleine, Vézelay, Yonne, France in 1991.",
            "playlistId": "FL4YtIyiwYth9ay6BWE3fUzg",
            "position": 3,
            "publishedAt": "2010-03-23T16:59:07.000Z",
            "resourceId": {
                "kind": "com.syedatifakhtar.youtube#video",
                "videoId": "LU_QR_FTt3E"
            },
            "thumbnails": {
                "default": {
                    "height": 90,
                    "url": "https://i.ytimg.com/vi/LU_QR_FTt3E/default.jpg",
                    "width": 120
                },
                "high": {
                    "height": 360,
                    "url": "https://i.ytimg.com/vi/LU_QR_FTt3E/hqdefault.jpg",
                    "width": 480
                },
                "medium": {
                    "height": 180,
                    "url": "https://i.ytimg.com/vi/LU_QR_FTt3E/mqdefault.jpg",
                    "width": 320
                }
            },
            "title": "Rostropovich plays the Prelude from Bach's Cello Suite No. 1"
        }
    },
    {
        "etag": "\"0KG1mRN7bm3nResDPKHQZpg5-do/0cjVZLKvQL1B2BrrL4HguvNhM9s\"",
        "id": "FL-Amd-FKkuKns-Wohf-BtgEHSH1E8cUuj",
        "kind": "com.syedatifakhtar.youtube#playlistItem",
        "snippet": {
            "channelId": "UC4YtIyiwYth9ay6BWE3fUzg",
            "channelTitle": "Atif Akhtar",
            "description": "SINCE PEOPLE KEEP ASKING, HERE ARE THE SONGS THAT HE PLAYED ACCORDING TO COMMENTERS:\n\n(old man song) \"Oh Susanna\"\n\n1ST: 'Jesu, Joy Of Man's Desiring' by Johann Sebastian Bach.\n\n2ND: Mozart's Piano Sonata in C, K. 545 - Allegro\n\n3RD: Rossini's William Tell overture (known to most as the theme from 'The Lone Ranger')\n\nWOW. He's so good on the harmonica!Buddy has talent pouring out from his ears. He also can sing, play the guitar, and he writes music (he wrote the music for 'Mary Did You Know') but he's most known for his Harmonica. Not to mention he is a really funny, cool guy.",
            "playlistId": "FL4YtIyiwYth9ay6BWE3fUzg",
            "position": 4,
            "publishedAt": "2010-03-23T16:51:24.000Z",
            "resourceId": {
                "kind": "com.syedatifakhtar.youtube#video",
                "videoId": "rfLhnkme2mE"
            },
            "thumbnails": {
                "default": {
                    "height": 90,
                    "url": "https://i.ytimg.com/vi/rfLhnkme2mE/default.jpg",
                    "width": 120
                },
                "high": {
                    "height": 360,
                    "url": "https://i.ytimg.com/vi/rfLhnkme2mE/hqdefault.jpg",
                    "width": 480
                },
                "medium": {
                    "height": 180,
                    "url": "https://i.ytimg.com/vi/rfLhnkme2mE/mqdefault.jpg",
                    "width": 320
                }
            },
            "title": "\"Classical Medley\" By Buddy Greene (HARMONICA)"
        }
    },
    {
        "etag": "\"0KG1mRN7bm3nResDPKHQZpg5-do/tZ3Cw_Ty2AL7dC_35TbSBE7YWA8\"",
        "id": "FL-Amd-FKkuKk8hFfgkNaG0DU7AclugOWX",
        "kind": "com.syedatifakhtar.youtube#playlistItem",
        "snippet": {
            "channelId": "UC4YtIyiwYth9ay6BWE3fUzg",
            "channelTitle": "Atif Akhtar",
            "description": "",
            "playlistId": "FL4YtIyiwYth9ay6BWE3fUzg",
            "position": 5,
            "publishedAt": "2010-03-16T16:39:27.000Z",
            "resourceId": {
                "kind": "com.syedatifakhtar.youtube#video",
                "videoId": "YHgo0HEjZWU"
            },
            "thumbnails": {
                "default": {
                    "height": 90,
                    "url": "https://i.ytimg.com/vi/YHgo0HEjZWU/default.jpg",
                    "width": 120
                },
                "high": {
                    "height": 360,
                    "url": "https://i.ytimg.com/vi/YHgo0HEjZWU/hqdefault.jpg",
                    "width": 480
                },
                "medium": {
                    "height": 180,
                    "url": "https://i.ytimg.com/vi/YHgo0HEjZWU/mqdefault.jpg",
                    "width": 320
                }
            },
            "title": "David Coverdale - Soldier Of Fortune"
        }
    },
    {
        "etag": "\"0KG1mRN7bm3nResDPKHQZpg5-do/ayRB3NZ1Hf_JTd_uwiZ4TCuzz_c\"",
        "id": "FL-Amd-FKkuKnVJxncYESRVELL3EOjEdkD",
        "kind": "com.syedatifakhtar.youtube#playlistItem",
        "snippet": {
            "channelId": "UC4YtIyiwYth9ay6BWE3fUzg",
            "channelTitle": "Atif Akhtar",
            "description": "in concert",
            "playlistId": "FL4YtIyiwYth9ay6BWE3fUzg",
            "position": 6,
            "publishedAt": "2010-03-16T16:27:34.000Z",
            "resourceId": {
                "kind": "com.syedatifakhtar.youtube#video",
                "videoId": "n-c66SJPuUI"
            },
            "thumbnails": {
                "default": {
                    "height": 90,
                    "url": "https://i.ytimg.com/vi/n-c66SJPuUI/default.jpg",
                    "width": 120
                },
                "high": {
                    "height": 360,
                    "url": "https://i.ytimg.com/vi/n-c66SJPuUI/hqdefault.jpg",
                    "width": 480
                },
                "medium": {
                    "height": 180,
                    "url": "https://i.ytimg.com/vi/n-c66SJPuUI/mqdefault.jpg",
                    "width": 320
                }
            },
            "title": "Chet Atkins - Mr. Sandman (TV 1954)"
        }
    },
    {
        "etag": "\"0KG1mRN7bm3nResDPKHQZpg5-do/2D6mS7s_ya8jZsxd4r-UmYkeZ1U\"",
        "id": "FL-Amd-FKkuKm5xcXPA9ydxiCxMgfj8clv",
        "kind": "com.syedatifakhtar.youtube#playlistItem",
        "snippet": {
            "channelId": "UC4YtIyiwYth9ay6BWE3fUzg",
            "channelTitle": "Atif Akhtar",
            "description": "Arpeggios From Hell!!!\r\nUnbelieveble Arpeggio shred by the one and only!!",
            "playlistId": "FL4YtIyiwYth9ay6BWE3fUzg",
            "position": 7,
            "publishedAt": "2010-03-16T16:26:51.000Z",
            "resourceId": {
                "kind": "com.syedatifakhtar.youtube#video",
                "videoId": "aS_IYe5JTZ4"
            },
            "thumbnails": {
                "default": {
                    "height": 90,
                    "url": "https://i.ytimg.com/vi/aS_IYe5JTZ4/default.jpg",
                    "width": 120
                },
                "high": {
                    "height": 360,
                    "url": "https://i.ytimg.com/vi/aS_IYe5JTZ4/hqdefault.jpg",
                    "width": 480
                },
                "medium": {
                    "height": 180,
                    "url": "https://i.ytimg.com/vi/aS_IYe5JTZ4/mqdefault.jpg",
                    "width": 320
                }
            },
            "title": "Yngwie Malmsteen-Arpeggios From Hell"
        }
    }
  ]"""

    val simpleTestJson =
        """[
    {
        "etag": "\"0KG1mRN7bm3nResDPKHQZpg5-do/2GA2NQgs1ulNOgNEDFa5CCtXUps\"",
        "id": "UU-Amd-FKkuKk1zpL_Y3T9rcvgWKfS_K-q",
        "kind": "com.syedatifakhtar.youtube#playlistItem",
        "snippet": {
            "channelId": "UC4YtIyiwYth9ay6BWE3fUzg",
            "channelTitle": "Atif Akhtar",
            "description": "Slideshow of pictures for the batch 2006-07 frm fr Agnel.....",
            "playlistId": "UU4YtIyiwYth9ay6BWE3fUzg",
            "position": 0,
            "publishedAt": "2008-03-01T19:20:34.000Z",
            "resourceId": {
                "kind": "com.syedatifakhtar.youtube#video",
                "videoId": "aHcuqZlPkwQ"
            },
            "thumbnails": {
                "default": {
                    "height": 90,
                    "url": "https://i.ytimg.com/vi/aHcuqZlPkwQ/default.jpg",
                    "width": 120
                },
                "high": {
                    "height": 360,
                    "url": "https://i.ytimg.com/vi/aHcuqZlPkwQ/hqdefault.jpg",
                    "width": 480
                },
                "medium": {
                    "height": 180,
                    "url": "https://i.ytimg.com/vi/aHcuqZlPkwQ/mqdefault.jpg",
                    "width": 320
                }
            },
            "title": "Fr Agnel 2006-07(Photograph)"
        }
    }
]"""


  it should "be able to parse any playlist video items" in {
    val youtubeWrapper = new YoutubeWrapper("someKey")
    youtubeWrapper.parseItemsForVideo(Json.parse(testJson)) should not be a [JsError]
//    println(youtubeWrapper parseItemsForVideo Json.parse(testJson))
  }
}