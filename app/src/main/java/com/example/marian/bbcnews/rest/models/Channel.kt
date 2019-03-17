package com.example.marian.bbcnews.rest.models

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel")
class Channel() {

    @field:ElementList(name = "item", inline = true, required = false)
    public var news: MutableList<News>? = null
}