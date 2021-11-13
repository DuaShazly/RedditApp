package com.reddit.app.model

import lombok.Data
import java.util.*

@Data
class RedditLink : RedditObject {
     val id: String? = null
     val title: String? = null
     val domain: String? = null
     val subreddit: String? = null
     val subredditId: String? = null
     val linkFlairText: String? = null
     val author: String? = null
     val thumbnail: String? = null
     val permalink: String? = null
     val url: String? = null
     val score = 0
     val ups = 0
     val downs = 0
     val numComments = 0
     val over18 = false
     val hideScore = false
     val created: Date? = null
     val createdUtc: Date? = null
}