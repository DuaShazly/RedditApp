package com.reddit.app.viewmodel

import com.reddit.app.model.RedditLink
import lombok.Data
import org.ocpsoft.prettytime.PrettyTime

@Data
class PostViewModel(redditLink: RedditLink) {
     val id: String = redditLink.id.toString()
    val title: String = redditLink.title.toString()
    val author: String = redditLink.author.toString()
    val thumbnailUrl: String = redditLink.thumbnail.toString()
    val createdOn: String
     val subreddit: String = redditLink.subreddit.toString()
    val domain: String = redditLink.domain.toString()
    val numComments: Int = redditLink.numComments
    val score: Int = redditLink.score

    init {
        val pt = PrettyTime()
        createdOn = pt.format(redditLink.created)
    }
}