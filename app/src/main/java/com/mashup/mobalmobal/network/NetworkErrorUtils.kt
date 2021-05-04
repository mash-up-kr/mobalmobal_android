package com.mashup.mobalmobal.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.IOException

object NetworkErrorUtils {

    private const val TAG = "NetworkErrorUtils"

    fun <T : Any> parseErrorResponse(t: Throwable, tag: String? = null): Response<T>? {
        val actualTag = tag ?: TAG
        try {
            if (t is retrofit2.HttpException) {
                try {
                    val body = t.response()?.errorBody()?.string()
                    try {
                        val response = Gson().fromJson(body, Response::class.java)
                        if (response?.message != null) {
                            return Response(message = response.message)
                        }
                    } catch (e: JsonSyntaxException) {
                        Log.w(actualTag, "error $body", e)
                    }
                } catch (e: IOException) {
                    Log.w(actualTag, "error ", e)
                }
            }
            return null
        } catch (e: Exception) {
            Log.e(actualTag, "Unknown error", e)
            return null
        }
    }

    fun <T : Any> Throwable.toErrorResponse(tag: String? = null): Response<T>? =
        parseErrorResponse(this, tag)
}