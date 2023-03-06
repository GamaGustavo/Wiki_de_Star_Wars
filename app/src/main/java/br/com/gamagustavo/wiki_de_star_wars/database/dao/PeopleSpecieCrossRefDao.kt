package br.com.gamagustavo.wiki_de_star_wars.database.dao

import androidx.room.*
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.PeopleSpecieCrossRef

@Dao
interface PeopleSpecieCrossRefDao {
    @Query("SELECT * FROM peoplespeciecrossref")
    fun getAll(): List<PeopleSpecieCrossRef>

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun insertAll(peoplespeciecrossref: List<PeopleSpecieCrossRef>)

    @Delete
    fun delete(peoplespeciecrossref: PeopleSpecieCrossRef)
}
