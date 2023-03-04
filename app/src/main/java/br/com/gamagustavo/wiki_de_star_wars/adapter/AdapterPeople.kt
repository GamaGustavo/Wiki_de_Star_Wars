package br.com.gamagustavo.wiki_de_star_wars.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import br.com.gamagustavo.wiki_de_star_wars.R
import br.com.gamagustavo.wiki_de_star_wars.model.PeopleModel

class AdapterPeople(
    private val context: Context,
    private val peoples: MutableList<PeopleModel>
) :
    RecyclerView.Adapter<AdapterPeople.PeopleViewHolder>() {

    inner class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.vl_name)
        val mass: TextView = itemView.findViewById(R.id.vl_mass)
        val height: TextView = itemView.findViewById(R.id.vl_height)
        val gender: TextView = itemView.findViewById(R.id.vl_gender)
        val card: CardView = itemView.findViewById(R.id.cv_item)
        val hairColor: TextView = itemView.findViewById(R.id.vl_hair_color)
        val skinColor: TextView = itemView.findViewById(R.id.vl_skin_color)
        val eyeColor: TextView = itemView.findViewById(R.id.vl_eye_color)
        val birthYear: TextView = itemView.findViewById(R.id.vl_birth_year)
        val homeworld: TextView = itemView.findViewById(R.id.vl_homeworld)
        val species: TextView = itemView.findViewById(R.id.vl_specie)
        val expandeIten: LinearLayout = itemView.findViewById(R.id.ln_expande_itens)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.people_item, parent, false)
        return PeopleViewHolder(view)
    }

    override fun getItemCount(): Int = peoples.size

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.card.setOnClickListener {
            holder.expandeIten.visibility =
                if (holder.expandeIten.visibility != View.VISIBLE) View.VISIBLE else View.GONE
        }
        val people = peoples[position]
        holder.name.text = people.name
        holder.mass.text = insertUnit(people.mass, " Kg")
        holder.height.text = insertUnit(people.height, " cm")
        holder.gender.text = people.gender
        holder.birthYear.text = people.birthYear
        holder.eyeColor.text = people.eyeColor
        holder.homeworld.text = people.name
        holder.hairColor.text = people.hairColor
        holder.skinColor.text = people.skinColor
        holder.species.text = people.name
    }

    fun insertUnit(string: String, unit: String): String {
        return if ("unknown" == string || "n/a" == string) string else string.plus(unit)
    }
}

interface ClickPeopleIten {
    fun clickPeopleIten(context: Context, peopleModel: PeopleModel)
}