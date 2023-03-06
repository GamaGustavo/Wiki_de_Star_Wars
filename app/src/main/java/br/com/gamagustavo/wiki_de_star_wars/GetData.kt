package br.com.gamagustavo.wiki_de_star_wars

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import br.com.gamagustavo.wiki_de_star_wars.adapter.AdapterPeople
import br.com.gamagustavo.wiki_de_star_wars.database.AppDataBaseBuilder
import br.com.gamagustavo.wiki_de_star_wars.database.AppDatabase
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.People
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.PeopleSpecieCrossRef
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.Planet
import br.com.gamagustavo.wiki_de_star_wars.database.entitys.Specie
import br.com.gamagustavo.wiki_de_star_wars.http.PeopleWebClient
import br.com.gamagustavo.wiki_de_star_wars.http.PlanetWebClient
import br.com.gamagustavo.wiki_de_star_wars.http.SpecieWbeClient
import br.com.gamagustavo.wiki_de_star_wars.model.PeopleModel
import br.com.gamagustavo.wiki_de_star_wars.model.PlanetModel
import br.com.gamagustavo.wiki_de_star_wars.model.SpecieModel
import br.com.gamagustavo.wiki_de_star_wars.utils.Pacer
import com.github.kittinunf.fuel.core.HttpException
import kotlinx.coroutines.*

object GetData {
    private val scope = MainScope()
    private val scopeIo = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val peoples: MutableList<PeopleModel> = mutableListOf()

    private fun getPlanetName(peopleModel: PeopleModel, context: Context) {
        scope.launch {
            try {
                peopleModel.nameHomeworld =
                    PlanetWebClient.getPlanetByUrl(peopleModel.homeworld).name
            } catch (exep: HttpException) {
                val people = Pacer.toEntity(peopleModel).homeWorldId
                if (people != null) AppDataBaseBuilder.getInstance(context).planetDao()
                    .getOneById(people)
            }

        }
    }

    private fun getSpeciesNames(peopleModel: PeopleModel, context: Context) {
        scope.launch {
            peopleModel.listSpecie = mutableListOf()
            peopleModel.species.forEach {
                try {
                    val specie = SpecieWbeClient.getSpecieByUrl(it)
                    peopleModel.listSpecie.add(specie)
                    peopleModel.nameSpecies = specie.name.plus("\n")
                } catch (exep: HttpException) {
                    val specieId = it.replace(SpecieWbeClient.path, "").replace("/", "").toLong()
                    val entity =
                        AppDataBaseBuilder.getInstance(context).specieDao().getOneById(specieId)
                    if (entity != null) {
                        val specie = Pacer.toModel(entity)
                        peopleModel.listSpecie.add(specie)
                        peopleModel.nameSpecies = specie.name.plus("\n")
                    }
                }

            }

        }
    }

    fun saveSpecieInDB(context: Context) {
        val speces: MutableList<SpecieModel> = mutableListOf()
        scopeIo.launch {
            try {
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
            } catch (exep: HttpException) {
                if (speces.isNotEmpty()) {
                    val entity: MutableList<Specie> = mutableListOf()
                    speces.forEach { entity.add(Pacer.toEntity(it)) }
                    AppDataBaseBuilder.getInstance(context).specieDao().insertAll(entity)
                }
            }

        }
    }

    fun savePlanetInDB(context: Context) {
        val planets: MutableList<PlanetModel> = mutableListOf()
        scopeIo.launch {
            try {
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
            } catch (expt: HttpException) {
                if (planets.isNotEmpty()) {
                    val entity: MutableList<Planet> = mutableListOf()
                    planets.forEach { entity.add(Pacer.toEntity(it)) }
                    AppDataBaseBuilder.getInstance(context).planetDao().insertAll(entity)
                }
            }
        }

    }


    private fun savePeopleInDb(context: Context) {
        scopeIo.launch {
            val entitys: MutableList<People> = mutableListOf()
            val peopleSpecieCrossRef: MutableList<PeopleSpecieCrossRef> = mutableListOf()
            peoples.forEach { people ->
                val entity = Pacer.toEntity(people)
                entitys.add(entity)
                if (!people.listSpecie.isNullOrEmpty()) {
                    people.listSpecie.forEach {
                        val specie = Pacer.toEntity(it)
                        peopleSpecieCrossRef.add(
                            PeopleSpecieCrossRef(
                                entity.peopleId,
                                specie.specieId
                            )
                        )
                    }

                }
            }
            AppDataBaseBuilder.getInstance(context).peopleDao().insertAll(entitys)
            AppDataBaseBuilder.getInstance(context).peopleSpecieCrossRefDao()
                .insertAll(peopleSpecieCrossRef)
        }
    }

    fun getPagePeoples(context: Context, recyclerView: RecyclerView) {
        scope.launch {
            peoples.clear()
            try {
                var listPeopleModel = PeopleWebClient.getFirtPagePeoples()
                var listPeople = listPeopleModel.results
                if (!listPeople.isNullOrEmpty()) {
                    peoples.addAll(listPeople)
                    scope.launch {
                        listPeopleModel.results!!.forEach {
                            getPlanetName(it, context)
                            getSpeciesNames(it, context)
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
                                    getPlanetName(it, context)
                                    getSpeciesNames(it, context)
                                }
                            }
                            recyclerView.adapter = AdapterPeople(context, peoples)
                        }
                    }
                }
                savePeopleInDb(context)
            } catch (expt: HttpException) {
                scopeIo.launch {
                    val db = AppDataBaseBuilder.getInstance(context)
                    if (peoples.isEmpty()) {
                        getPeopleOnDB(db)
                    }else{
                        savePeopleInDb(context)
                        getPeopleOnDB(db)
                    }
                    scope.launch {
                        recyclerView.adapter = AdapterPeople(context, peoples)
                    }
                }
            }
        }
    }

    private fun getPeopleOnDB(db :AppDatabase ) {
        val entitys = db.peopleDao().getAll()
        if (entitys.isNotEmpty()) {
            peoples.clear()
            entitys.forEach { people ->
                val peopleModel = Pacer.toModel(people)
                val speciesOfPeople =
                    db.peopleDao().getSpeciesOfPeople(people.peopleId)
                if(speciesOfPeople != null) {
                    speciesOfPeople.species.forEach {
                        val specie = Pacer.toModel(it)
                        peopleModel.listSpecie.add(specie)
                        peopleModel.nameSpecies.plus(specie.name).plus("\n")
                    }
                }
                val planet =
                    db.planetDao().getOneById(people.homeWorldId!!.toLong())
                if (planet != null) {
                    peopleModel.nameHomeworld = planet.name
                }
                peoples.add(peopleModel)
            }
        }
    }
}