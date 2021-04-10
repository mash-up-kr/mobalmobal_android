package com.mashup.mobalmobal.ui.profile.data

object ProfileMockData {
    val donateData = """
        {
        	"code": 200,
        	"data": {
        		"donate": [{
        			"donate_id": 3,
        			"user_id": 193,
        			"post_id": 39,
        			"amount": 19000,
        			"createdAt": "2021-03-29: 01:01:01",
        			"updatedAt": "2021-03-29: 01:01:01",
        			"deletedAt": "2021-03-29: 01:01:01",
        			"post": {
        "post_id": 92,
        "user_id": 1,
        "title": "닥터마틴 1460 스무스",
        "description": "닥터마틴 부츠가 요즘 너무 예뻐보여서..",
        "goal": 250000,
        "current_amount": 0,
        "started_at": "2021-04-10T00:00:00.000Z",
        "end_at": "2021-04-30T00:00:00.000Z",
        "post_image": "https://cdn.crewbi.com/images/goods_img/20201019/456999/456999_a_500.jpg?v=1603090538",
        "createdAt": "2021-04-10T05:50:45.000Z",
        "updatedAt": "2021-04-10T05:50:45.000Z",
        "deleted_at": null
        			}
        		}]
        	}
        }
    """.trimIndent()

    val userData = """
    {
      "code": 1,
      "data": {
        "user": {
          "user_id": 0909,
          "nick_name": "리액션마스터",
          "profile_image": "http://pht.qoo-static.com/VHB9bVB8cTcnqwnu0nJqKYbiutRclnbGxTpwnayKB4vMxZj8pk1220Rg-6oQ68DwAkqO=w300",
          "cash": 135000
        }
      }
    }
    """.trimIndent()

    val postData = """
    {
      "code": 1,
      "data": {
        "post": [
          {
        "post_id": 89,
        "user_id": 1,
        "title": "마샬 엠버튼",
        "description": "날씨도 좋은데 캠핑할 때 블루투스 스피커로 노래 들으면 좋을것같아요 !!",
        "goal": 249000,
        "current_amount": 5000,
        "started_at": "2021-04-10T00:00:00.000Z",
        "end_at": "2021-04-15T00:00:00.000Z",
        "post_image": "http://img.danawa.com/prod_img/500000/361/743/img/11743361_1.jpg?shrink=360:360&_v=20201006163903",
        "createdAt": "2021-04-10T05:43:07.000Z",
        "updatedAt": "2021-04-10T05:47:55.000Z",
        "deleted_at": null
          },
          {
                  "post_id": 90,
                  "user_id": 1,
                  "title": "제이에스티나 목걸이",
                  "description": "쇄골이 없으니까 목걸이라도 걸래여",
                  "goal": 76000,
                  "current_amount": 0,
                  "started_at": "2021-04-10T00:00:00.000Z",
                  "end_at": "2021-04-30T00:00:00.000Z",
                  "post_image": "http://webimg.jestina.co.kr/UpData2/item/G2000014830/JNSVA842-M948TRZM.png",
                  "createdAt": "2021-04-10T05:46:37.000Z",
                  "updatedAt": "2021-04-10T05:46:37.000Z",
                  "deleted_at": null
          }
        ]
      }
    }
    """.trimIndent()



}