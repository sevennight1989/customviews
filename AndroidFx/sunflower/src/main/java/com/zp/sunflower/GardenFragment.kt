package com.zp.sunflower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zp.sunflower.adapters.GardenPlantingAdapter
import com.zp.sunflower.databinding.FragmentGardenBinding
import com.zp.sunflower.viewmodels.GardenPlantingListViewModel

class GardenFragment : Fragment() {
    private lateinit var binding: FragmentGardenBinding

    private val viewMode: GardenPlantingListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGardenBinding.inflate(inflater, container, false)
        val adapter = GardenPlantingAdapter()
        binding.gardenList.adapter = adapter
        binding.addPlant.setOnClickListener {

        }
        subscribeUi(adapter, binding)

        return binding.root
    }

    private fun subscribeUi(adapter: GardenPlantingAdapter, binding: FragmentGardenBinding) {
        viewMode.plantAndGardenPlantings.observe(viewLifecycleOwner) { result ->
            binding.hasPlantings = !result.isNullOrEmpty()
            adapter.submitList(result)
        }
    }
}