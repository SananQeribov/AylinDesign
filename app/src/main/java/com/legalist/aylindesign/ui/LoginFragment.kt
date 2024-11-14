package com.legalist.aylindesign.ui

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.legalist.aylindesign.R
import com.legalist.aylindesign.base.BaseFragment
import com.legalist.aylindesign.databinding.FragmentLoginBinding
import com.legalist.aylindesign.viewmodel.LoginViewModel
import com.legalist.common.viewmodels.CommonViewModelFactory
import com.legalist.data.model.Login
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val loginViewModel: LoginViewModel by viewModels {
        CommonViewModelFactory {
            LoginViewModel(
                requireActivity().application,
                get(
                    named("loginRepo")
                )
            )
        }
    }

    override fun setupUI() {


        binding.loginButton.setOnClickListener {
            val login = Login(
                email = binding.email.text.toString(),
                password = binding.password.text.toString()

            )
            loginViewModel.loginUser(login)
        }
        navigateTo(binding.registerLink, R.id.registerFragment)

        observeViewModel()
    }

    private fun observeViewModel() {

        viewLifecycleOwner.lifecycleScope.launch {
            loginViewModel.loginResult.collectLatest { result ->
                result?.let { (isSuccess, message) ->

                    if (isSuccess) {
                        navigateTo(null, R.id.registerFragment)
                    } else {
                        Snackbar.make(
                            binding.root,
                            "Login failed: $message",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}
