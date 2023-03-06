package br.com.gamagustavo.wiki_de_star_wars

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import br.com.gamagustavo.wiki_de_star_wars.adapter.AdapterPeople
import br.com.gamagustavo.wiki_de_star_wars.database.AppDataBaseBuilder
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.Planet
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.Specie
import br.com.gamagustavo.wiki_de_star_wars.http.PeopleWebClient
import br.com.gamagustavo.wiki_de_star_wars.http.PlanetWebClient
import br.com.gamagustavo.wiki_de_star_wars.http.SpecieWbeClient
import br.com.gamagustavo.wiki_de_star_wars.model.PeopleModel
import br.com.gamagustavo.wiki_de_star_wars.model.PlanetModel
import br.com.gamagustavo.wiki_de_star_wars.model.SpecieModel
import br.com.gamagustavo.wiki_de_star_wars.utils.Pacer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

object GetData {
    private val scope = MainScope()
    private val scopeIo = CoroutineScope(SupervisorJob())
    private val peoples: MutableList<PeopleModel> = mutableListOf()

    private fun getPlanetName(peopleModel: PeopleModel) {
        scope.launch {
            peopleModel.nameHomeworld =
                PlanetWebClient.getPlanetByUrl(peopleModel.homeworld).name
        }
    }

    private fun getSpeciesNames(peopleModel: PeopleModel) {
        scope.launch {
            peopleModel.species.forEach {
                peopleModel.nameSpecies = SpecieWbeClient.getSpecieByUrl(it).name.plus("\n")
            }
        }
    }

    fun saveSpecieInDB(context: Context) {
        val speces: MutableList<SpecieModel> = mutableListOf()

        scopeIo.launch {
            var listSpecieModel = SpecieWbeClient.getFirtPageSpecies()
            if (!listSpecieModel.next.isNullOrEmpty()) {
                speces.addAll(listSpecieModel.results!!)
                while (!listSpecieModel.next.isNullOrEmpty()) {
                    listSpecieModel =
                        SpecieWbeClient.getNexPageSpecies(listSpecieModel.next!!)
                    speces.addAll(listSpecieModel.results!!)
                }
                val entity: MutableList<Specie> = mutableListOf()
                speces.forEach { entity.add(Pacer.toEntity(it)) }
                AppDataBaseBuilder.getInstance(context).specieDao().insertAll(entity)
            }

        }
    }

    fun savePlanetInDB(context: Context) {
        val planets: MutableList<PlanetModel> = mutableListOf()
        scopeIo.launch {
            var listPlanetModel = PlanetWebClient.getFirtPagePlanets()
            if (!listPlanetModel.next.isNullOrEmpty()) {
                planets.addAll(listPlanetModel.results)
                while (!listPlanetModel.next.isNullOrEmpty()) {
                    listPlanetModel =
                        PlanetWebClient.getNexPagePlanets(listPlanetModel.next!!)
                    planets.addAll(listPlanetModel.results)
                }
                val entity: MutableList<Planet> = mutableListOf()
                planets.forEach { entity.add(Pacer.toEntity(it)) }
                AppDataBaseBuilder.getInstance(context).planetDao().insertAll(entity)
            }

        }

    }


    fun getPagePeoples(context: Context, recyclerView: RecyclerView) {
        scope.launch {
            peoples.clear()
            var listPeopleModel = PeopleWebClient.getFirtPagePeoples()
            var listPeople = listPeopleModel.results
            if (!listPeople.isNullOrEmpty()) {
                peoples.addAll(listPeople)
                scope.launch {
                    listPeopleModel.results!!.forEach {
                        getPlanetName(it)
                        getSpeciesNames(it)
                    }
                }
                recyclerView.adapter = AdapterPeople(context, peoples)
                while (!listPeopleModel.next.isNullOrEmpty()) {
                    listPeopleModel =
                        PeopleWebClient.getNexPagePeoples(listPeopleModel.next.toString())
                    listPeople = listPeopleModel.results
                    if (!listPeople.isNullOrEmpty()) {
                        peoples.addAll(listPeople)
                        scope.launch {
                            listPeopleModel.results!!.forEach {
                                getPlanetName(it)
                                getSpeciesNames(it)
                            }
                        }
                        recyclerView.adapter = AdapterPeople(context, peoples)
                    }
                }
            }
        }
    }
}