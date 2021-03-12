package com.mashup.mobalmobal.ui.donationdetail.data

object DonationMockData {
    val donationData = """
        {
          "result": "1",
          "data": {
            "donation": {
              "donation_id": "1",
              "author": {
                "user_id": "jercy",
                "user_name": "Jercy",
                "profile_url": "https://interactive-examples.mdn.mozilla.net/media/cc0-images/grapefruit-slice-332-332.jpg"
              },
              "image":"
              "product": "PS5",
              "description": "안녕하세요. 한달..",
              "goal_price": 2000000,
              "donated_price": 153000,
              "donators": [
                {
                  "user_id": "jercy",
                  "user_name": "Jercy",
                  "profile_url": "https://interactive-examples.mdn.mozilla.net/media/cc0-images/grapefruit-slice-332-332.jpg"
                },
                {
                  "user_id": "jercy",
                  "user_name": "Jercy",
                  "profile_url": "https://interactive-examples.mdn.mozilla.net/media/cc0-images/grapefruit-slice-332-332.jpg"
                }
              ],
              "start_date": 1614582246303,
              "due_date": 1617260745293
            }
          }
        }
    """.trimIndent()
}