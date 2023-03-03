package br.com.gamagustavo.wiki_de_star_wars.database.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Specie(
    @PrimaryKey(autoGenerate = false) val specieId: Long,
    val name: String,
    val url: String,
    val created: String,
    val edited: String,
)


