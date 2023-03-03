package br.com.gamagustavo.wiki_de_star_wars.utils

import br.com.gamagustavo.wiki_de_star_wars.database.entitys.People
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.Planet
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.Specie
import br.com.gamagustavo.wiki_de_star_wars.http.PeopleWebClient
import br.com.gamagustavo.wiki_de_star_wars.http.PlanetWebClient
import br.com.gamagustavo.wiki_de_star_wars.http.SpecieWbeClient
import br.com.gamagustavo.wiki_de_star_wars.model.PeopleModel
import br.com.gamagustavo.wiki_de_star_wars.model.PlanetModel
import br.com.gamagustavo.wiki_de_star_wars.model.SpecieModel

object Pacer {
   fun toEntity(specieModel: SpecieModel): Specie {
        val url = specieModel.url.replace(SpecieWbeClient.path, "").replace("/", "")
        return Specie(
            specieId = url.toLong(),
            url = specieModel.url,
            name = specieModel.name,
            created = specieModel.created,
            edited = specieModel.edited
        )
    }

    fun toEntity(planetModel: PlanetModel): Planet {
        val url = planetModel.url.replace(PlanetWebClient.path, "").replace("/", "")
        return Planet(
            planetId = url.toLong(),
            url = planetModel.url,
            name = planetModel.name,
            created = planetModel.created,
            edited = planetModel.edited
        )
    }

    fun toEntuty(peopleModel: PeopleModel): People {
        val url = peopleModel.url.replace(PeopleWebClient.path, "").replace("/", "")
        val homeWold = peopleModel.homeworld.replace(PlanetWebClient.path, "").replace("/", "")
        return People(
            peopleId = url.toLong(),
            url = peopleModel.url,
            name = peopleModel.name,
            created = peopleModel.created,
            edited = peopleModel.edited,
            skinColor = peopleModel.skinColor,
            mass = peopleModel.mass,
            homeWorldId = homeWold.toLong(),
            height = peopleModel.height,
            hairColor = peopleModel.hairColor,
            gender = peopleModel.gender,
            eyeColor = peopleModel.eyeColor,
            birthYear = peopleModel.birthYear,
        )
    }
}


