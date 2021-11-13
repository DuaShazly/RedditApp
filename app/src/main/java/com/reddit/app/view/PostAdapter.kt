package com.reddit.app.view

import androidx.recyclerview.widget.RecyclerView
import com.reddit.app.viewmodel.PostViewModel
import android.view.ViewGroup
import android.view.LayoutInflater
import com.reddit.app.R

import java.util.ArrayList

class PostAdapter : RecyclerView.Adapter<PostViewHolder>() {
    private var items: List<PostViewModel> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.view_item_post, parent, false
        )
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.hashCode().toLong()
    }

    fun getItem(position: Int): PostViewModel {
        return items[position]
    }

    fun setItems(items: List<PostViewModel>?) {
        if (items == null) {
            return
        }
        this.items = ArrayList(items)
        notifyDataSetChanged()
    }

    init {
        setHasStableIds(true)
    }
}