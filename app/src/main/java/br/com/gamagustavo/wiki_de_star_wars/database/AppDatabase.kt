package br.com.gamagustavo.wiki_de_star_wars.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.gamagustavo.wiki_de_star_wars.database.dao.PeopleDao
import br.com.gamagustavo.wiki_de_star_wars.database.dao.PeopleSpecieCrossRefDao
import br.com.gamagustavo.wiki_de_star_wars.database.dao.PlanetDao
import br.com.gamagustavo.wiki_de_star_wars.database.dao.SpecieDao
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.People
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.PeopleSpecieCrossRef
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.Planet
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.Specie

@Database(
    entities = [People::class, Planet::class, Specie::class, PeopleSpecieCrossRef::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun peopleDao(): PeopleDao
    abstract fun planetDao(): PlanetDao
    abstract fun specieDao(): SpecieDao
    abstract fun peopleSpecieCrossRefDao(): PeopleSpecieCrossRefDao

}
