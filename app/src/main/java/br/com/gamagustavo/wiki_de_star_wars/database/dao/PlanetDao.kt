package br.com.gamagustavo.wiki_de_star_wars.database.dao


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.Planet

@Dao
interface PlanetDao {

    @Query("SELECT * FROM planet")
    fun getAll(): List<Planet>

    @Query(value = "SELECT * FROM planet WHERE name = :name")
    fun getOneByName(name: String): Planet

    @Query(value = "SELECT * FROM planet WHERE name Like :name")
    fun getManyByName(name: String): List<Planet>

    @Query(value = "SELECT * FROM planet WHERE planetId = :planetId")
    fun getOneById(planetId: Long): Planet

    @Insert
    fun insertAll(planets: List<Planet>)

    @Delete
    fun delete(planet: Planet)

    @Query(value = "DELETE FROM planet WHERE planetId = :planetId ")
    fun deleteById(planetId: Long)

}
