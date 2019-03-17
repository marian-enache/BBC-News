package com.example.marian.bbcnews.ui

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marian.bbcnews.Constants
import com.example.marian.bbcnews.databinding.CellNewsBinding
import com.example.marian.bbcnews.rest.models.News
import com.example.marian.bbcnews.viewmodels.InjectorUtils

class NewsAdapter : ListAdapter<News, NewsAdapter.ViewHolder>(NewsDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = getItem(position)
        holder.apply {
            bind(createOnClickListener(newsItem.link), newsItem)
            itemView.tag = newsItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CellNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun getItemFromAdapter(pos: Int) = getItem(pos)

    private fun createOnClickListener(link: String?): View.OnClickListener {
        return View.OnClickListener {
            var context = it.context
            var repository = InjectorUtils.getSettingsRepository(context)
            link?.let {
                when (repository.getOpenType()) {
                    Constants.OPEN_TYPE_APP -> {
                        val intent = Intent(context, WebViewActivity::class.java)
                        intent.putExtra(Constants.INTENT_EXTRA_LINK, link)
                        context.startActivity(intent)
                    }
                    Constants.OPEN_TYPE_BROWSER -> {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                        context.startActivity(browserIntent)
                    }
                }
            }
        }
    }

    class ViewHolder(private val binding: CellNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, newsItem: News) {
            binding.apply {
                clickListener = listener
                item = newsItem
                executePendingBindings()
            }
        }
    }
}

private class NewsDiffCallback : DiffUtil.ItemCallback<News>() {

    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.title.equals(newItem.title)
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }
}