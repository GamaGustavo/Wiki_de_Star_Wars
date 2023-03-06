package br.com.gamagustavo.wiki_de_star_wars.model

import com.google.gson.annotations.SerializedName

data class PeopleModel(
    val name: String,
    val height: String,
    val mass: String,
    @SerializedName(value = "hair_color") val hairColor: String,
    @SerializedName(value = "skin_color") val skinColor: String,
    @SerializedName(value = "eye_color") val eyeColor: String,
    @SerializedName(value = "birth_year") val birthYear: String,
    val gender: String,
    val homeworld: String,
    var nameSpecies:String,
    var nameHomeworld: String,
    val species: List<String>,
    val url: String,
    val created: String,
    val edited: String,
    var isFavorite: Boolean
)