package br.com.gamagustavo.wiki_de_star_wars

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.gamagustavo.wiki_de_star_wars.adapter.AdapterPeople
import br.com.gamagustavo.wiki_de_star_wars.http.PeopleWebClient
import br.com.gamagustavo.wiki_de_star_wars.model.PeopleModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val scope = MainScope()
    private val peoples: MutableList<PeopleModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.rc_people)
        recyclerView.layoutManager = LinearLayoutManager(this)
        getPagePeoples(this, recyclerView)

    }

    private fun getPagePeoples(context: Context, recyclerView: RecyclerView) {
        scope.launch {
            var listPeopleModel = PeopleWebClient.getFirtPagePeoples()
            var listPeople = listPeopleModel.results
            if (!listPeople.isNullOrEmpty()) {
                peoples.addAll(listPeople)
                while (!listPeopleModel.next.isNullOrEmpty()) {
                    listPeopleModel =
                        PeopleWebClient.getNexPagePeoples(listPeopleModel.next.toString())
                    listPeople = listPeopleModel.results
                    if (!listPeople.isNullOrEmpty()) {
                        peoples.addAll(listPeople)
                    }
                    recyclerView.adapter = AdapterPeople(context, peoples)
                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}