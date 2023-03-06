package br.com.gamagustavo.wiki_de_star_wars

import android.content.Context
import android.view.View
import br.com.gamagustavo.wiki_de_star_wars.adapter.AdapterPeople
import br.com.gamagustavo.wiki_de_star_wars.model.PeopleModel

object OnClickExpanded : OnClick {
    override fun onClick(
        holder: AdapterPeople.PeopleViewHolder,
        people: PeopleModel,
        context: Context
    ) {
        holder.expandeIten.visibility =
            if (holder.expandeIten.visibility != View.VISIBLE) View.VISIBLE else View.GONE
    }
}