package com.zp.sunflower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.zp.sunflower.adapters.GardenPlantingAdapter
import com.zp.sunflower.databinding.FragmentGardenBinding
import com.zp.sunflower.viewmodels.GardenPlantingListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GardenFragment : Fragment() {

    private lateinit var binding: FragmentGardenBinding

    private val viewModel: GardenPlantingListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGardenBinding.inflate(inflater, container, false)
        val adapter = GardenPlantingAdapter()
        binding.gardenList.adapter = adapter

        binding.addPlant.setOnClickListener {
            navigateToPlantListPage()
        }

        subscribeUi(adapter, binding)
        return binding.root
    }

    private fun subscribeUi(adapter: GardenPlantingAdapter, binding: FragmentGardenBinding) {
        viewModel.plantAndGardenPlantings.observe(viewLifecycleOwner) { result ->
            binding.hasPlantings = !result.isNullOrEmpty()
            adapter.submitList(result)
        }
    }

    // TODO: convert to data binding if applicable
    private fun navigateToPlantListPage() {
//        requireActivity().findViewById<ViewPager2>(R.id.view_pager).currentItem =
//            PLANT_LIST_PAGE_INDEX
    }
}