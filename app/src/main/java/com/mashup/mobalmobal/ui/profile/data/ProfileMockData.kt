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
        				"post_id": 39,
        				"user_id": 193,
        				"title": "내가 후원한 타이틀",
        				"post_description": "ㅅㅓ서서서서설며여여여영",
        				"goal": 2000000,
        				"started_at": "2021-03-29: 01:01:01",
        				"end_at": "2021-03-29: 01:01:01",
        				"post_image": "http://pht.qoo-static.com/VHB9bVB8cTcnqwnu0nJqKYbiutRclnbGxTpwnayKB4vMxZj8pk1220Rg-6oQ68DwAkqO=w300",
        				"created_at": "2021-03-29: 01:01:01",
        				"updated_at": "2021-03-29: 01:01:01",
        				"current_amount": 2000
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
          "user_id": "koba0909",
          "nick_name": "koba_nick",
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
            "post_id": 2,
            "title": "도네이션 타이틀",
            "description": "도네이션 설명",
            "goal": 200000,
            "current_amount": 100000,
            "start_at": "2021-03-29: 01:01:01",
            "end_at": "2021-03-29: 01:01:01",
            "post_image": "http://pht.qoo-static.com/VHB9bVB8cTcnqwnu0nJqKYbiutRclnbGxTpwnayKB4vMxZj8pk1220Rg-6oQ68DwAkqO=w300"
          },
          {
            "post_id": 3,
            "title": "도네이션 타이틀2",
            "description": "도네이션 설명2",
            "goal": 200000,
            "current_amount": 100000,
            "start_at": "2021-03-29: 01:01:01",
            "end_at": "2021-03-29: 01:01:01",
            "post_image": "http://pht.qoo-static.com/VHB9bVB8cTcnqwnu0nJqKYbiutRclnbGxTpwnayKB4vMxZj8pk1220Rg-6oQ68DwAkqO=w300"
          }
        ]
      }
    }
    """.trimIndent()



}