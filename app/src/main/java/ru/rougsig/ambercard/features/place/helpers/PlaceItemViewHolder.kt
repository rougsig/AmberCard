package ru.rougsig.ambercard.features.place.helpers

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.rougsig.ambercard.databinding.PlaceItemBinding
import ru.rougsig.ambercard.features.place.data.PlaceModel

/**
 * Created by rougs on 16-Oct-17.
 */
class PlaceAdapter(val array: List<PlaceModel>) : RecyclerView.Adapter<PlaceAdapter.PlaceItemViewHolder>() {
    override fun getItemCount() = array.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceItemViewHolder {
        val infalter = LayoutInflater.from(parent.context)
        val binding = PlaceItemBinding.inflate(infalter, parent, false)
        return PlaceItemViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: PlaceItemViewHolder, position: Int) {
        holder.binding.place = array[position]
    }

    class PlaceItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: PlaceItemBinding = DataBindingUtil.bind(itemView)
    }
}