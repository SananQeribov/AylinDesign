package com.legalist.aylindesign.ui

// app/ui/RegisterFragment.kt
import android.app.DatePickerDialog
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.legalist.aylindesign.base.BaseFragment
import com.legalist.aylindesign.databinding.FragmentRegisterBinding
import com.legalist.aylindesign.viewmodel.RegisterViewModel
import com.legalist.aylindesign.viewmodel.RegisterViewModelFactory
import com.legalist.data.repository.UserRepositoryImpl
import com.legalist.data.model.User
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val registerViewModel: RegisterViewModel by viewModels {
        //RegisterViewModelFactory(UserRepositoryImpl("https://66a8396353c13f22a3d21b48.mockapi.io/api/v1/",requireContext()))
        RegisterViewModelFactory(get(named("userRepo")))
    }



    override fun setupUI() {
        setupPhoneNumberField()
        setupBirthdayField()

        binding.registerButton.setOnClickListener {
            val currentTimestamp = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Calendar.getInstance().time)

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

    private fun setupPhoneNumberField() {
        binding.phoneNumber.addTextChangedListener(object : TextWatcher {
            private var isEditing = false

            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                val currentText = charSequence.toString()

                if (currentText.startsWith("+994")) {
                    binding.phoneNumber.setSelection(binding.phoneNumber.text.length)
                } else if (!currentText.startsWith("+994") && currentText.isNotEmpty()) {
                    binding.phoneNumber.setText("+994$currentText")
                    binding.phoneNumber.setSelection(binding.phoneNumber.text.length)
                }
            }

            override fun afterTextChanged(editable: Editable?) {
                val currentText = editable.toString()
                if (currentText.count { it == '+' } == 2 && !isEditing) {
                    isEditing = true
                    binding.phoneNumber.setText("+994")
                    binding.phoneNumber.setSelection(binding.phoneNumber.text.length)
                    isEditing = false
                }
            }
        })
    }

    private fun setupBirthdayField() {
        binding.birthday.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.birthday.setText(selectedDate)
            }, year, month, day)

            datePickerDialog.show()
        }
    }

    private fun observeViewModel() {
        registerViewModel.registerResult.observe(viewLifecycleOwner) { result ->
            Toast.makeText(requireContext(), result, Toast.LENGTH_SHORT).show()
        }
    }
}



