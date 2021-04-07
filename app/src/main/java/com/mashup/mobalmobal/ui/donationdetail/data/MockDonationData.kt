package com.mashup.mobalmobal.ui.donationdetail.data

object MockDonationData {
    val postData = """
        {
          "data": {
            "post": {
              "post_id": 1,
              "user": {
                "id": "jercy",
                "nick_name": "Jercy",
                "profile_url": "http://post-phinf.pstatic.net/MjAyMDA5MDNfMjk1/MDAxNTk5MDY3MDY1MjU1.lUJqQWg1FwJPymmE0xcO_Q9Y2W8b5ElqYxqNcVeEtVgg.KOVJ04J1j1aX-cNCjwCp8VCs17bto6bO3R7loOrHyP4g.JPEG/17.jpg?type=w1200"
              },
              "donate_users": [
                {
                  "id": "jercy",
                  "nick_name": "Jercy",
                  "profile_url": "http://img.youtube.com/vi/ywLJOsjckFQ/hqdefault.jpg"
                },
                {
                  "id": "jercy",
                  "nick_name": "Jercy",
                  "profile_url": "http://i.ytimg.com/vi/fvpWfMPALbQ/maxresdefault.jpg"
                },
                {
                  "id": "jercy",
                  "nick_name": "Jercy",
                  "profile_url": "http://i.ytimg.com/vi/fvpWfMPALbQ/maxresdefault.jpg"
                }
              ],
              "title": "PS5",
              "description": "안녕하세요. 한달..",
              "goal": 200000,
              "current_amount": 100000,
              "started_at": "2021-03-18T00:00:00.000Z",
              "end_at": "2021-03-19T00:00:00.000Z",
              "post_image": "http://cdn.gukjenews.com/news/photo/202011/2108182_2102499_193.jpg",
              "createdAt": "2021-03-11T08:37:35.000Z"
            }
          },
          "code": 200
        }
    """.trimIndent()

}