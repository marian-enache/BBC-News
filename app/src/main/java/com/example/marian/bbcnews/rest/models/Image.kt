package com.example.marian.bbcnews.rest.models

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "thumbnail")
open class Image(){
    @field:Attribute var width: Int? = null
    @field:Attribute var height: Int? = null
    @field:Attribute var url: String? = null
}