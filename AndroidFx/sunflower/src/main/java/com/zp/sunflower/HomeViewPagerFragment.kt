package com.zp.sunflower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.zp.sunflower.databinding.FragmentViewPagerBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentViewPagerBinding.inflate(inflater,container,false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        return binding.root;
    }
}