package com.legalist.aylindesign.ui

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.legalist.aylindesign.R
import com.legalist.aylindesign.base.BaseFragment
import com.legalist.aylindesign.databinding.FragmentLoginBinding
import com.legalist.aylindesign.viewmodel.LoginViewModel
import com.legalist.aylindesign.viewmodel.LoginViewModelFactory
import com.legalist.data.model.Login
import com.legalist.data.repository.LoginRepositoryImpl


import androidx.lifecycle.lifecycleScope
import com.legalist.data.model.User
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(LoginRepositoryImpl("https://66a8396353c13f22a3d21b48.mockapi.io/api/v1/", requireContext()))
    }

    override fun setupUI() {
        navigateTo(binding.loginLink, R.id.loginFragment)

        binding.loginButton.setOnClickListener {
            val login = Login(
                email = binding.email.text.toString(),
                password = binding.password.text.toString()

            )
            loginViewModel.loginUser(login)
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            loginViewModel.loginResult.collectLatest { result ->
                result?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
