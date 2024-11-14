package com.legalist.aylindesign.ui

// app/ui/RegisterFragment.kt
import android.app.DatePickerDialog
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.legalist.aylindesign.R
import com.legalist.aylindesign.base.BaseFragment
import com.legalist.aylindesign.databinding.FragmentRegisterBinding
import com.legalist.aylindesign.viewmodel.RegisterViewModel
import com.legalist.common.extensions.addPhoneNumberFormatting
import com.legalist.common.extensions.showDatePicker
import com.legalist.common.viewmodels.CommonViewModelFactory
import com.legalist.data.model.User
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    //    private val registerViewModel: RegisterViewModel by viewModels {
//        //RegisterViewModelFactory(UserRepositoryImpl("https://66a8396353c13f22a3d21b48.mockapi.io/api/v1/",requireContext()))
//        RegisterViewModelFactory(get(named("userRepo")))
//    }
    private val registerViewModel: RegisterViewModel by viewModels {
        CommonViewModelFactory {
            RegisterViewModel(
                requireActivity().application,
                get(
                    named("registerRepo")
                )
            )
        }
    }


    override fun setupUI() {

        binding.phoneNumber.addPhoneNumberFormatting()

        binding.birthday.setOnClickListener { showDatePicker(binding.birthday) }


        navigateTo(binding.loginLink, R.id.loginFragment)

        binding.registerButton.setOnClickListener {
            val currentTimestamp = SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
            ).format(Calendar.getInstance().time)

            val user = User(
                created = currentTimestamp.toString(),
                fullname = binding.username.text.toString(),
                birthday = binding.birthday.text.toString(),
                email = binding.email.text.toString(),
                number = binding.phoneNumber.text.toString(),
                password = binding.password.text.toString(),
                role = "User",
                isDeleted = false,
                id = ""
            )
            registerViewModel.registerUser(user)

        }

        observeViewModel()


    }


    private fun observeViewModel() {
        registerViewModel.result.observe(viewLifecycleOwner) { result ->
            Toast.makeText(requireContext(), result.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}



