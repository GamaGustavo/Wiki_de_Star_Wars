package br.com.gamagustavo.wiki_de_star_wars.database.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class People(
    @PrimaryKey(autoGenerate = false) val peopleId: Long,
    val name: String,
    val height: String,
    val mass: String,
    @ColumnInfo(name = "hair_color") val hairColor: String,
    @ColumnInfo(name = "skin_color") val skinColor: String,
    @ColumnInfo(name = "eye_color") val eyeColor: String,
    @ColumnInfo(name = "birth_year") val birthYear: String,
    val gender: String,
    @ColumnInfo(name = "homeworld") val homeWorldId: Long?,
    val url: String,
    val created: String,
    val edited: String
)