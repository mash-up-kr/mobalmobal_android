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
          "nickname": "리액션마스터",
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

    val postDataInPregress = """
    {
      "code": 1,
      "data": {
        "post": [
          {
        "post_id": 76,
        "user_id": 2,
        "title": "닌텐도 스위치",
        "description": "닌텐도 스위치 사주시면 뭐든 합니다!",
        "goal": 299999,
        "current_amount": 174800,
        "started_at": "2021-04-09T22:39:35.000Z",
        "end_at": "2021-04-16T23:39:39.000Z",
        "post_image": "http://cdn.011st.com/11dims/resize/600x600/quality/75/11src/pd/20/4/8/3/6/7/5/kwxib/3223483675_B.png",
        "createdAt": "2021-04-09T22:39:42.000Z",
        "updatedAt": "2021-04-10T05:23:14.000Z",
        "deleted_at": null
          },
          {
        "post_id": 81,
        "user_id": 1,
        "title": "플레이스테이션 5",
        "description": "정말 갖고싶은 플레이스테이션 5 사주세요 ㅠㅠ",
        "goal": 780000,
        "current_amount": 0,
        "started_at": "2021-04-10T00:00:00.000Z",
        "end_at": "2021-04-17T00:00:00.000Z",
        "post_image": "https://t1.daumcdn.net/thumb/R720x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/7nQg/image/YbwY672tjh1hUpZPthirtGOPCiI.jpg",
        "createdAt": "2021-04-10T05:33:20.000Z",
        "updatedAt": "2021-04-10T05:33:20.000Z",
        "deleted_at": null
                  
          }
        ]
      }
    }
    """.trimIndent()

    val postDataExpired = """
    {
      "code": 1,
      "data": {
        "post": [
          {
"post_id": 85,
        "user_id": 2,
        "title": "Galaxy Buds Pro",
        "description": "저 아직도 줄 이어폰 씁니다 ㅜㅜ 보은 하겠습니다 후원 부탁드려용,,",
        "goal": 230000,
        "current_amount": 100000,
        "started_at": "2021-04-10T00:00:00.000Z",
        "end_at": "2021-04-17T00:00:00.000Z",
        "post_image": "data:image/jpeg;base64,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",
        "createdAt": "2021-04-10T05:38:57.000Z",
        "updatedAt": "2021-04-10T05:40:14.000Z",
        "deleted_at": null
          },
          {
"post_id": 84,
        "user_id": 1,
        "title": "나인봇 맥스 G30 전동킥보드",
        "description": "친구들 다 갖고 있는데 저도 하나만 사주세요!!",
        "goal": 809000,
        "current_amount": 0,
        "started_at": "2021-04-10T00:00:00.000Z",
        "end_at": "2021-04-24T00:00:00.000Z",
        "post_image": "http://img.danawa.com/prod_img/500000/825/657/img/9657825_1.jpg?shrink=360:360&_v=20210125091838",
        "createdAt": "2021-04-10T05:36:42.000Z",
        "updatedAt": "2021-04-10T05:36:42.000Z",
        "deleted_at": null
                  
          }
        ]
      }
    }
    """.trimIndent()




}