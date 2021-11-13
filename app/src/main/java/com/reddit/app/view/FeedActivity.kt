package com.reddit.app.view

import android.annotation.SuppressLint
import org.androidannotations.annotations.EActivity

import org.androidannotations.annotations.OptionsMenu
import androidx.appcompat.app.AppCompatActivity
import org.androidannotations.annotations.ViewById
import androidx.recyclerview.widget.RecyclerView
import org.androidannotations.annotations.OptionsMenuItem
import javax.inject.Inject
import com.reddit.app.viewmodel.FeedViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import rx.subscriptions.CompositeSubscription
import android.os.Bundle
import android.view.MenuItem
import com.reddit.app.MvvmRedditApplication
import com.reddit.app.R
import org.androidannotations.annotations.AfterViews
import rx.android.schedulers.AndroidSchedulers
import com.reddit.app.viewmodel.PostViewModel
import rx.Observable
import rx.Subscriber

@SuppressLint("NonConstantResourceId")
@EActivity(R.layout.activity_feed)
@OptionsMenu(R.menu.feed)
class FeedActivity : AppCompatActivity() {

    @ViewById(R.id.post_list)
    var postList: RecyclerView? = null
    @OptionsMenuItem(R.id.progress)
    var loadingMenuItem: MenuItem? = null
    @Inject
    var viewModel: FeedViewModel? = null
    private var postAdapter: PostAdapter? = null
    private var postListLayoutManager: LinearLayoutManager? = null
    private var subscriptions: CompositeSubscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MvvmRedditApplication).androidInjector()?.inject(this)
        subscriptions = CompositeSubscription()
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions!!.unsubscribe()
    }

    @AfterViews
    fun init() {
        initViews()
        initBindings()

        loadPosts()
    }

    private fun initViews() {
        postListLayoutManager = LinearLayoutManager(this)
        postList!!.layoutManager = postListLayoutManager
        postAdapter = PostAdapter()
        postList!!.adapter = postAdapter
    }

    private fun initBindings() {
        // Observable that emits when the RecyclerView is scrolled to the bottom
        val infiniteScrollObservable = Observable.create { subscriber: Subscriber<in Void?> ->
            postList!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val totalItemCount = postListLayoutManager!!.itemCount
                    val visibleItemCount = postListLayoutManager!!.childCount
                    val firstVisibleItem = postListLayoutManager!!.findFirstVisibleItemPosition()
                    if (visibleItemCount + firstVisibleItem >= totalItemCount) {
                        subscriber.onNext(null)
                        loadNextPage()
                    }
                }
            })
        }
        subscriptions!!.addAll(
            viewModel!!.postsObservable().observeOn(AndroidSchedulers.mainThread())
                .subscribe { items: List<PostViewModel?>? -> postAdapter!!.setItems(items as List<PostViewModel>?) },
            viewModel!!.isLoadingObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe { isLoading: Boolean -> setIsLoading(isLoading) },
            infiniteScrollObservable.subscribe { x: Void? -> loadNextPage() }
        )
    }

    private fun loadNextPage() {
        subscriptions!!.add(
            viewModel!!.loadMorePosts().subscribe()
        )
    }

    private fun loadPosts() {
        subscriptions!!.add(
            viewModel!!.loadPosts().subscribe()
        )
    }
    private fun setIsLoading(isLoading: Boolean) {
        if (loadingMenuItem != null) {
            loadingMenuItem!!.isVisible = isLoading
        }
    }
}