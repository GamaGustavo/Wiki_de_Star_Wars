package br.com.gamagustavo.wiki_de_star_wars.http

import br.com.gamagustavo.wiki_de_star_wars.http.interceptors.LoggingInterceptor
import com.github.kittinunf.fuel.core.HttpException
import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.httpGet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object WebClient {

    suspend fun get(path: String): String {
        return get(path, null)
    }

    suspend fun get(path: String, parameters: Parameters?): String {
        return withContext(Dispatchers.Default) {
            val (request, response, result) = path.httpGet(parameters).response()
            LoggingInterceptor.interceptRequest(request)

            if (response.statusCode in 200..299) {
                LoggingInterceptor.interceptResponse(response)
                val (bytes, error) = result
                if (bytes != null) return@withContext String(bytes)
                if (error != null) throw error
            }
            if (response.statusCode < 0) throw HttpException(response.statusCode,"Connection Error")
            throw HttpException(response.statusCode,response.responseMessage)
        }
    }
}