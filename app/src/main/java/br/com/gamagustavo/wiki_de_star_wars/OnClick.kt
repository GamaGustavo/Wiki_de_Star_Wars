package br.com.gamagustavo.wiki_de_star_wars

import android.content.Context
import br.com.gamagustavo.wiki_de_star_wars.adapter.AdapterPeople
import br.com.gamagustavo.wiki_de_star_wars.model.PeopleModel

interface OnClick {

    fun onClick(holder: AdapterPeople.PeopleViewHolder,people: PeopleModel,context: Context)

}