package com.reddit.app.deserializer

import com.google.gson.JsonDeserializer
import kotlin.Throws
import com.google.gson.JsonParseException
import com.google.gson.JsonElement
import com.google.gson.JsonDeserializationContext
import java.lang.reflect.Type
import java.util.*


class RedditDateDeserializer : JsonDeserializer<Any?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Any? {
        return Date(json.asJsonPrimitive.asLong * 1000)
    }
}