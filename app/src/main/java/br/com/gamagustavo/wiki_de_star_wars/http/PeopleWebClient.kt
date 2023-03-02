package br.com.gamagustavo.wiki_de_star_wars.http

import br.com.gamagustavo.wiki_de_star_wars.model.ListPeopleModel
import br.com.gamagustavo.wiki_de_star_wars.model.PeopleModel
import com.google.gson.Gson

object PeopleWebClient {
    private const val PATH = "https://swapi.dev/api/people"
    private val GSON = Gson()

    suspend fun getAllPeoples(): ListPeopleModel {
        val json = WebClient.get(PATH)
        return GSON.fromJson(json, ListPeopleModel::class.java)
    }

    suspend fun getPeoplByName(name: String): ListPeopleModel {
        val parameters = ArrayList<Pair<String, String>>()
        parameters.add(Pair("search", name))
        val json = WebClient.get(PATH, parameters)
        return GSON.fromJson(json, ListPeopleModel::class.java)
    }

    suspend fun getPeopleById(id: Int): PeopleModel {
        val pathWithId = "$PATH/$id/"
        val json = WebClient.get(pathWithId)
        return GSON.fromJson(json, PeopleModel::class.java)
    }
}