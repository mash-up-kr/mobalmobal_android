package com.mashup.mobalmobal.ui.profile.data

object ProfileMockData {
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

    val donationData = """
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