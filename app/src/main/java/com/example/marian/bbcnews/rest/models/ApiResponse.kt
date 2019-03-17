package com.example.marian.bbcnews.rest.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss")
class ApiResponse {
    @field:Element(name = "channel") var channel: Channel? = null
}