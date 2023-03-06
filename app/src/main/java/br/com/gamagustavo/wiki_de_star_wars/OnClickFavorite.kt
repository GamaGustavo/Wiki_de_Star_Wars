package br.com.gamagustavo.wiki_de_star_wars

import android.content.Context
import android.graphics.drawable.Icon
import br.com.gamagustavo.wiki_de_star_wars.adapter.AdapterPeople
import br.com.gamagustavo.wiki_de_star_wars.model.PeopleModel

object OnClickFavorite : OnClick {
    override fun onClick(
        holder: AdapterPeople.PeopleViewHolder,
        people: PeopleModel,
        context: Context
    ) {
        if (people.isFavorite) {
            holder.favoriteButton.setImageIcon(
                Icon.createWithResource(
                    context,
                    R.drawable.baseline_favorite_border_24
                )
            )
            people.isFavorite = false
        } else {
            holder.favoriteButton.setImageIcon(
                Icon.createWithResource(
                    context,
                    R.drawable.baseline_favorite_red
                )
            )
            people.isFavorite = true
        }
    }
}