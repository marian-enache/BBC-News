package com.example.marian.bbcnews

import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marian.bbcnews.rest.models.News
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import java.util.*


object BindingAdapters {

    @JvmStatic
    @BindingAdapter("visibleOrGone")
    fun visibleOrGone(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("navigationItemSelectedListener")
    fun setNavigationItemSelectedListener(
        view: BottomNavigationView,
        listener: BottomNavigationView.OnNavigationItemSelectedListener
    ) {
        view.setOnNavigationItemSelectedListener(listener)
    }

    @JvmStatic
    @BindingAdapter("newsImage")
    fun loadImage(view: ImageView, productImage: String?) {
        Picasso.with(view.context)
            .load(productImage)
            .placeholder(ContextCompat.getDrawable(view.context, R.drawable.ic_news_orange_24dp))
            .error(
                Objects.requireNonNull(
                    ContextCompat.getDrawable(
                        view.context,
                        R.drawable.ic_news_orange_24dp
                    )
                )
            )
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("setWebViewClient")
    fun setWebViewClient(view: WebView, client: WebViewClient) {
        view.webViewClient = client
    }

    @JvmStatic
    @BindingAdapter("loadUrl")
    fun loadUrl(view: WebView, url: String) {
        view.loadUrl(url)
    }

    @JvmStatic
    @BindingAdapter("hasSwipeLeft")
    fun setItemSwipeToRecyclerView(recyclerView: RecyclerView, ok: Boolean) {
        val context = recyclerView.context
            object: SwipeHelper(context, recyclerView){
                override fun instantiateUnderlayButton(
                    viewHolder: RecyclerView.ViewHolder,
                    underlayContainers: MutableList<SwipeHelper.UnderlayButton>) {

                    underlayContainers.add(SwipeHelper.UnderlayButton(
                        context,
                        context.resources.getColor(R.color.back_layer_color)
                    ))

                }
            }

    }
}