package com.legalist.aylindesign.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.legalist.aylindesign.R
import com.legalist.aylindesign.base.BaseFragment
import com.legalist.aylindesign.databinding.FragmentSplashBinding
import com.legalist.aylindesign.viewmodel.SplashViewmodel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private lateinit var viewModel: SplashViewmodel

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSplashBinding {
        return FragmentSplashBinding.inflate(inflater, container, false)
    }

    override fun onResume() {
        super.onResume()
        viewModel = ViewModelProvider(this)[SplashViewmodel::class.java]
        viewModel.splash()

        lifecycleScope.launch {
            viewModel.splashTime.collectLatest { timeout ->
                if (timeout) {
                    navigateToFragment()
                }
            }
        }
    }

    private fun navigateToFragment() {
        findNavController().navigate(R.id.boardingFragment)
    }
}
