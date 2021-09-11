package com.zp.sunflower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zp.sunflower.databinding.FragmentGardenBinding
import com.zp.sunflower.viewmodels.GardenPlantingListViewModel

class GardenFragment : Fragment() {
    private lateinit var binding: FragmentGardenBinding

    private val viewMode : GardenPlantingListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGardenBinding.inflate(inflater, container, false)




        return binding.root
    }
}