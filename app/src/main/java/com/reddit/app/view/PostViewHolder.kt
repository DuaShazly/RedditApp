package com.reddit.app.view

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.reddit.app.R
import com.reddit.app.viewmodel.PostViewModel
import com.squareup.picasso.Picasso

import org.apache.commons.validator.routines.UrlValidator

class PostViewHolder(private val view: View) : RecyclerView.ViewHolder(
    view) {

    private val titleTextView: TextView = view.findViewById<View>(R.id.title) as TextView
    private val subtitleTextView: TextView = view.findViewById<View>(R.id.subtitle) as TextView
    private val subtitle2TextView: TextView = view.findViewById<View>(R.id.subtitle2) as TextView
    private val thumbnailImageView: ImageView = view.findViewById<View>(R.id.thumbnail) as ImageView

    fun bind(viewModel: PostViewModel) {
        titleTextView.text = viewModel.title
        subtitleTextView.text = String.format(
            "%s - %s - %s (%s)",
            viewModel.author,
            viewModel.createdOn,
            viewModel.subreddit,
            viewModel.domain
        )
        subtitle2TextView.text =
            String.format("%d points - %d comments", viewModel.score, viewModel.numComments)
        val urlValidator = UrlValidator()
        val hasThumbnail =
            urlValidator.isValid(viewModel.thumbnailUrl)


        thumbnailImageView.visibility = if (hasThumbnail) View.VISIBLE else View.GONE


        if (hasThumbnail) {
            Picasso.with(view.context).load(viewModel.thumbnailUrl).into(thumbnailImageView)
        }
    }

}