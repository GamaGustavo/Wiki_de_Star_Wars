package br.com.gamagustavo.wiki_de_star_wars.http

import br.com.gamagustavo.wiki_de_star_wars.model.ListPeopleModel
import com.google.gson.Gson

object PeopleWebClient {
    const val path = "https://swapi.dev/api/people/"
    private val gson = Gson()

    suspend fun getFirtPagePeoples(): ListPeopleModel {
        val json = WebClient.get(path)
        return gson.fromJson(json, ListPeopleModel::class.java)
    }

    suspend fun getNexPagePeoples(nextPage: String): ListPeopleModel {
        val json = WebClient.get(nextPage)
        return gson.fromJson(json, ListPeopleModel::class.java)
    }

    suspend fun getPeoplByName(name: String): ListPeopleModel {
        val parameters = ArrayList<Pair<String, String>>()
        parameters.add(Pair("search", name))
        val json = WebClient.get(path, parameters)
        return gson.fromJson(json, ListPeopleModel::class.java)
    }
}