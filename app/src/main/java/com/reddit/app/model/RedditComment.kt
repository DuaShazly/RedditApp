package com.reddit.app.model

import lombok.Data
import java.util.*

@Data
class RedditComment : RedditObject {
    private val id: String? = null
    private val body: String? = null
    private val bodyHtml: String? = null
    private val author: String? = null
    private val subredditId: String? = null
    private val linkId: String? = null
    private val parentId: String? = null
    private val score: String? = null
    private val ups: String? = null
    private val downs: String? = null
    private val created: Date? = null
    private val createdUtc: Date? = null
}