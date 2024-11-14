package com.legalist.aylindesign.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.legalist.aylindesign.R
import com.legalist.aylindesign.adapter.CarouselAdapter
import com.legalist.aylindesign.base.BaseFragment
import com.legalist.aylindesign.databinding.FragmentBoardingBinding


class BoardingFragment : BaseFragment<FragmentBoardingBinding>(FragmentBoardingBinding::inflate) {



    override fun setupUI() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = CarouselLayoutManager()
        CarouselSnapHelper().attachToRecyclerView(binding.recyclerView)

        val imageList = mutableListOf<Int>()
        imageList.add(R.drawable.one)
        imageList.add(R.drawable.two)
        imageList.add(R.drawable.three)
        imageList.add(R.drawable.four)


        val adapter = CarouselAdapter(imageList)
        binding.recyclerView.adapter = adapter
        navigateTo(binding.button,R.id.loginFragment)
    }




}
