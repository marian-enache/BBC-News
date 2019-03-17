package com.example.marian.bbcnews.rest.models

import com.example.marian.bbcnews.db.NewsModel
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.text.SimpleDateFormat
import java.util.*

@Root(name = "item")
class News() {

    @field:Element var title: String? = null
    @field:Element var description: String? = null
    @field:Element var link: String? = null
    @field:Element(name = "pubDate")  var date: String? = null
    @field:Element(name = "thumbnail") var image: Image? = null

    override fun equals(other: Any?): Boolean {
        var otherNewsItem = other as? News
        otherNewsItem?.let {
            return this.title.equals(otherNewsItem.title)
                    && this.description.equals(otherNewsItem.description)
                    && this.link.equals(otherNewsItem.link)
                    && this.date.equals(otherNewsItem.date)
        }

        return false
    }

    fun getFormattedDate(): String {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
        cal.time = sdf.parse(date)

        return String.format("%s, %d %s %d",
            cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.US),
            cal.get(Calendar.DAY_OF_MONTH),
            cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US),
            cal.get(Calendar.YEAR))
    }

    constructor(newsModel: NewsModel) : this() {
        fromNewsModel(newsModel)
    }

    fun fromNewsModel(newsModel: NewsModel) : News {
        title = newsModel.title
        description = newsModel.description
        link = newsModel.link
        date = newsModel.date

        image = Image()
        image!!.width = newsModel.width
        image!!.height = newsModel.height
        image!!.url = newsModel.imageUrl

        return this
    }
}