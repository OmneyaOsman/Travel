package com.omni.travel.presentation.features.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.omni.travel.R
import com.omni.travel.entities.TravelDeal
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.deal_list_item.view.*


class DealsAdapter(
    options: FirebaseRecyclerOptions<TravelDeal>
    ) : FirebaseRecyclerAdapter<TravelDeal, DealsAdapter.ViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.deal_list_item, parent, false)
            .let { ViewHolder(it) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: TravelDeal) {
        holder.bindView(model)
    }


    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        fun bindView(travelDeal: TravelDeal) {
            itemView.dealName.text = travelDeal.title
            itemView.dealPrice.text = travelDeal.price
            showImage(travelDeal.imageUrl)
            itemView.descName.text = travelDeal.description
        }

        private fun showImage(url: String? ) {
            if (url != null && url.isNotEmpty()) {
                Picasso.get()
                    .load(url)
                    .resize(160, 160)
                    .centerCrop()
                    .into(itemView.dealImage)
            }
        }

    }

}