package br.com.gamagustavo.wiki_de_star_wars
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GetData.savePlanetInDB(this)
        GetData.saveSpecieInDB(this)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.rc_people)
        recyclerView.layoutManager = LinearLayoutManager(this)
        GetData.getPagePeoples(this, recyclerView)
    }

}