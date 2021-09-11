package com.zp.sunflower.adapters

import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zp.sunflower.data.PlantAndGardenPlantings
import com.zp.sunflower.databinding.ListItemGardenPlantingBinding

class GardenPlantingAdapter :
    ListAdapter<PlantAndGardenPlantings,>(){


        class ViewHolder(
            private val binding : ListItemGardenPlantingBinding
        ):RecyclerView.ViewHolder(binding.root){
            init {

            }
        }



}



private class GardenPlantDiffCallback : DiffUtil.ItemCallback<PlantAndGardenPlantings>() {
    override fun areItemsTheSame(
        oldItem: PlantAndGardenPlantings,
        newItem: PlantAndGardenPlantings
    ): Boolean {
        return oldItem.plant.plantId == newItem.plant.plantId
    }

    override fun areContentsTheSame(
        oldItem: PlantAndGardenPlantings,
        newItem: PlantAndGardenPlantings
    ): Boolean {
        return oldItem == newItem
    }


}