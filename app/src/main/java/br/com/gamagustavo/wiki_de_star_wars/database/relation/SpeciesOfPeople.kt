package br.com.gamagustavo.wiki_de_star_wars.database.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.People
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.PeopleSpecieCrossRef
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.Specie

data class SpeciesOfPeople(
    @Embedded val people: People,
    @Relation(
        parentColumn = "peopleId",
        entityColumn = "specieId",
        associateBy = Junction(PeopleSpecieCrossRef::class)
    ) val species: List<Specie>
)
