package com.reddit.app.model

import lombok.Data

@Data
class RedditListing : RedditObject {
     val children: List<RedditObject>? = null
     val before: String? = null
    val after: String? = null
}