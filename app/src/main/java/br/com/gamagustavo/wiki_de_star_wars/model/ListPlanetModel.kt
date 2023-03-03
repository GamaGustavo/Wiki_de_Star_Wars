package br.com.gamagustavo.wiki_de_star_wars.model

data class ListPlanetModel(
    val count: Int?,
    val next: String?,
    val previous: String?,
    val results: List<PlanetModel>,
)
