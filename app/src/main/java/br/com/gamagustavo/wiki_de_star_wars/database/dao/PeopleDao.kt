package br.com.gamagustavo.wiki_de_star_wars.database.dao

import androidx.room.*
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.People
import br.com.gamagustavo.wiki_de_star_wars.database.relation.SpeciesOfPeople

@Dao
interface PeopleDao {

    @Query("SELECT * FROM people")
    fun getAll(): List<People>

    @Query(value = "SELECT * FROM people WHERE name = :name")
    fun getOneByName(name: String): People

    @Query(value = "SELECT * FROM people WHERE name Like :name")
    fun getManyByName(name: String): List<People>

    @Query(value = "SELECT * FROM people WHERE name = :peopleId")
    fun getById(peopleId: Long): People

    @Transaction
    @Query("SELECT * FROM people")
    fun getSpeciesOfPeople(): List<SpeciesOfPeople>

    @Insert
    fun insertAll(peoples: List<People>)

    @Delete
    fun delete(people: People)

    @Query(value = "DELETE FROM people WHERE peopleId = :peopleId ")
    fun deleteById(peopleId: Long)
}
