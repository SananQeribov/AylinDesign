package com.legalist.common.extensions

import android.app.DatePickerDialog
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.fragment.app.Fragment
import java.util.Calendar

fun Fragment.showDatePicker(editText: EditText) {
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
        val selectedDate = "$dayOfMonth/${month + 1}/$year"
        editText.setText(selectedDate)
    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

    datePickerDialog.show()
}

fun EditText.addPhoneNumberFormatting() {
    this.addTextChangedListener(object : TextWatcher {
        private var isEditing = false

        override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
            if (!isEditing) {
                isEditing = true
                if (!charSequence.startsWith("+994") && charSequence.isNotEmpty()) {
                    setText("+994$charSequence")
                    setSelection(this@addPhoneNumberFormatting.text.length)
                }
                isEditing = false
            }
        }

        override fun afterTextChanged(editable: Editable?) {
            val text = editable.toString()
            if (text.count { it == '+' } > 1) {
                setText("+994")
                setSelection(this@addPhoneNumberFormatting.text.length)
            }
        }
    })
}