package br.com.gamagustavo.wiki_de_star_wars.http

import br.com.gamagustavo.wiki_de_star_wars.model.ListPlanetModel
import br.com.gamagustavo.wiki_de_star_wars.model.PlanetModel
import com.google.gson.Gson

object PlanetWebClient {
    private const val path = "https://swapi.dev/api/planets"
    private val gson = Gson()

    suspend fun getPlanetByUrl(url: String): PlanetModel {
        val json = WebClient.get(url)
        return gson.fromJson(json, PlanetModel::class.java)
    }

    suspend fun getFirtPagePlanets(): ListPlanetModel {
        val json = WebClient.get(path)
        return gson.fromJson(json, ListPlanetModel::class.java)
    }

    suspend fun getNexPagePlanets(nextPage: String): ListPlanetModel {
        val json = WebClient.get(nextPage)
        return gson.fromJson(json, ListPlanetModel::class.java)
    }
}
