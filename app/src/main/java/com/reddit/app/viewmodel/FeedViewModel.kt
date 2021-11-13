package com.reddit.app.viewmodel

import javax.inject.Inject
import com.reddit.app.client.RedditClient
import rx.subjects.BehaviorSubject
import com.reddit.app.model.RedditListing
import rx.functions.Func1
import com.reddit.app.model.RedditLink
import rx.Observable
import java.util.ArrayList

class FeedViewModel @Inject constructor(private val redditClient: RedditClient) {

    private val pageLimit = 25
    private var afterToken: String? = null
    private val postSubject = BehaviorSubject.create<List<PostViewModel>>(ArrayList())
    private val isLoadingSubject = BehaviorSubject.create(false)

    fun loadMorePosts(): Observable<List<PostViewModel>> {
        if (isLoadingSubject.value) {
            return Observable.empty()
        }
        isLoadingSubject.onNext(true)
        return redditClient
            .getTopAfter(
                afterToken,
                pageLimit
            )?.cast(RedditListing::class.java)?.doOnNext { listing: RedditListing ->
                afterToken = listing.after
            }
            ?.map<Any>(RedditListing::children)
            ?.flatMapIterable(Func1<Any, Iterable<*>?> { list: Any? -> listOf(list) })
            ?.filter { `object`: Any? -> `object` is RedditLink } // Transform model to viewmodel
            ?.map { link: Any? -> PostViewModel((link as RedditLink?)!!) } // Merge viewmodels into a single list to be emitted
            ?.toList() // Concatenate the new posts to the current posts list, then emit it via the post subject
            ?.doOnNext { list: List<PostViewModel>? ->
                val fullList: MutableList<PostViewModel> = ArrayList(postSubject.value)
                fullList.addAll(list!!)
                postSubject.onNext(fullList)
            }
            ?.doOnTerminate { isLoadingSubject.onNext(false) }!!
    }


    fun loadPosts(): Observable<List<PostViewModel>> {
        return redditClient
            .getTop()?.cast(RedditListing::class.java)?.doOnNext { listing: RedditListing ->
                afterToken = listing.after
            }
            ?.map<Any>(RedditListing::children)
            ?.flatMapIterable(Func1<Any, Iterable<*>?> { list: Any? -> listOf(list) })
            ?.filter { `object`: Any? -> `object` is RedditLink } // Transform model to viewmodel
            ?.map { link: Any? -> PostViewModel((link as RedditLink?)!!) } // Merge viewmodels into a single list to be emitted
            ?.toList() // Concatenate the new posts to the current posts list, then emit it via the post subject
            ?.doOnNext { list: List<PostViewModel>? ->
                val fullList: MutableList<PostViewModel> = ArrayList(postSubject.value)
                fullList.addAll(list!!)
                postSubject.onNext(fullList)
            }
            ?.doOnTerminate { isLoadingSubject.onNext(false) }!!
    }

    fun postsObservable(): Observable<List<PostViewModel>> {
        return postSubject.asObservable()
    }

    val isLoadingObservable: Observable<Boolean>
        get() = isLoadingSubject.asObservable()
}