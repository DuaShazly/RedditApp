package com.reddit.app.deserializer

import android.util.Log
import com.google.gson.JsonDeserializer
import kotlin.Throws
import com.google.gson.JsonParseException
import com.google.gson.JsonElement
import com.google.gson.JsonDeserializationContext
import com.reddit.app.model.RedditObject
import com.reddit.app.model.RedditListing
import com.reddit.app.model.RedditComment
import com.reddit.app.model.RedditLink
import java.lang.reflect.Type


class RedditObjectJsonDeserializer : JsonDeserializer<Any?> {


    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): RedditObject? {
        return if (!json.isJsonObject) {
            null
        } else try {
            val jsonObject = json.asJsonObject
            val kind = jsonObject["kind"].asString
            context.deserialize<RedditObject>(jsonObject["data"], getClassForKind(kind))
        } catch (e: JsonParseException) {
            Log.e(TAG, String.format("Could not deserialize Reddit element: %s", json.toString()))
            null
        }
    }


    private fun getClassForKind(kind: String): Class<*>? {
        return when (kind) {
            "Listing" -> RedditListing::class.java
            "t1" -> RedditComment::class.java
            "t3" -> RedditLink::class.java
            else -> {
                Log.e(
                    TAG,
                    String.format("Unsupported Reddit kind: %s", kind)
                )
                null
            }
        }
    }

    companion object {
         const val TAG = "RedditDeserializer"
    }
}