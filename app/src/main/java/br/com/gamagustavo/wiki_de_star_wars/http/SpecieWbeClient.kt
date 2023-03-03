package br.com.gamagustavo.wiki_de_star_wars.http

import br.com.gamagustavo.wiki_de_star_wars.model.ListSpecieModel
import br.com.gamagustavo.wiki_de_star_wars.model.SpecieModel
import com.google.gson.Gson

object SpecieWbeClient {
    const val path = "https://swapi.dev/api/species/"
    private val gson = Gson()

    suspend fun getSpecieByUrl(url: String): SpecieModel {
        val json = WebClient.get(url)
        return gson.fromJson(json, SpecieModel::class.java)
    }
    suspend fun getFirtPageSpecies(): ListSpecieModel {
        val json = WebClient.get(path)
        return gson.fromJson(json, ListSpecieModel::class.java)
    }
    suspend fun getNexPageSpecies(nextPage: String): ListSpecieModel {
        val json = WebClient.get(nextPage)
        return gson.fromJson(json, ListSpecieModel::class.java)
    }
}