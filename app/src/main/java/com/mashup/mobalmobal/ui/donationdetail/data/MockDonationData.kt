package com.mashup.mobalmobal.ui.donationdetail.data

object MockDonationData {
    val donationData = """
        {
          "result": "1",
          "data": {
              "donation_id": "1",
              "author": {
                "id": "jercy",
                "nick_name": "Jercy",
                "profile_url": "http://post-phinf.pstatic.net/MjAyMDA5MDNfMjk1/MDAxNTk5MDY3MDY1MjU1.lUJqQWg1FwJPymmE0xcO_Q9Y2W8b5ElqYxqNcVeEtVgg.KOVJ04J1j1aX-cNCjwCp8VCs17bto6bO3R7loOrHyP4g.JPEG/17.jpg?type=w1200"
              },
              "image":"http://cdn.gukjenews.com/news/photo/202011/2108182_2102499_193.jpg",
              "product": "PS5",
              "description": "안녕하세요. 한달..",
              "goal_price": 200000,
              "donated_price": 153000,
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
              "start_date": 1614582246303,
              "due_date": 1617260745293          
          }
        }
    """.trimIndent()
}