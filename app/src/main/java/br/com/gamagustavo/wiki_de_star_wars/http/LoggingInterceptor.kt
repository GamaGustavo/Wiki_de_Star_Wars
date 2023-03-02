package br.com.gamagustavo.wiki_de_star_wars.http

import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response

object LoggingInterceptor {

    fun interceptRequest(request: Request): Request {
        println("[Request] $request")
        return request
    }

    fun interceptResponse(response: Response): Response {
        println("[Response] $response")
        return response
    }

}