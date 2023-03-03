package br.com.gamagustavo.wiki_de_star_wars.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.gamagustavo.wiki_de_star_wars.R
import br.com.gamagustavo.wiki_de_star_wars.model.PeopleModel

class AdapterPeople(private val context: Context, private val peoples: MutableList<PeopleModel>) :
    RecyclerView.Adapter<AdapterPeople.PeopleViewHolder>() {

    inner class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.vl_name)
        val mass: TextView = itemView.findViewById(R.id.vl_mass)
        val height: TextView = itemView.findViewById(R.id.vl_height)
        val gender: TextView = itemView.findViewById(R.id.vl_gender)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.people_item, parent, false)
        return PeopleViewHolder(view)
    }

    override fun getItemCount(): Int = peoples.size

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        val people = peoples[position]
        holder.name.text = people.name
        holder.mass.text =
            if ("unknown" == people.mass || "n/a" == people.mass) {
                people.mass
            } else {
                people.mass.plus(" kg")
            }
        holder.height.text =
            if ("unknown" == people.height || "n/a" == people.height) {
                people.height
            } else {
                people.height.plus(" cm")
            }
        holder.gender.text = people.gender
    }
}