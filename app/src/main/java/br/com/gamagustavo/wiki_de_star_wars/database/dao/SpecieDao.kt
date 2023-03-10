package br.com.gamagustavo.wiki_de_star_wars.database.dao

import androidx.room.*
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.Specie

@Dao
interface SpecieDao {

    @Query("SELECT * FROM specie")
    fun getAll(): List<Specie>

    @Query(value = "SELECT * FROM specie WHERE name = :name")
    fun getOneByName(name: String): Specie

    @Query(value = "SELECT * FROM specie WHERE name Like :name")
    fun getManyByName(name: String): List<Specie>

    @Query(value = "SELECT * FROM specie WHERE specieId = :specieId")
    fun getOneByName(specieId: Long): Specie

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    fun insertAll(species: List<Specie>)

    @Delete
    fun delete(specie: Specie)

    @Query(value = "DELETE FROM specie WHERE specieId = :specieId ")
    fun deleteById(specieId: Long)

    @Query(value = "SELECT * FROM specie WHERE specieId = :specieId")
    fun getOneById(specieId: Long): Specie
}
