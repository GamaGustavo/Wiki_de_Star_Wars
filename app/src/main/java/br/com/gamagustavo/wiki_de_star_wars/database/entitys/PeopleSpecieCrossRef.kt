package br.com.gamagustavo.wiki_de_star_wars.database.entitys

import androidx.room.Entity

@Entity(primaryKeys = ["peopleId", "specieId"])
data class PeopleSpecieCrossRef(
    val peopleId: Long,
    val specieId: Long
)
