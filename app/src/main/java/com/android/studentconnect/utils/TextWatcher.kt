package com.android.studentconnect.utils

import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import com.android.studentconnect.R
import com.android.studentconnect.ui.base.ActBase

class TextWatcher(context: ActBase, inputText: TextInputLayout, tag: Int) : TextWatcher {

    private var context: ActBase? = null
    private var tag = 0
    private var inputText: TextInputLayout? = null

    init {
        this.context = context
        this.tag = tag
        this.inputText = inputText
    }

    override fun afterTextChanged(s: Editable?) {
        if (s.isNullOrEmpty()) return

        when (tag) {
            R.string.university_name -> {
                inputText?.error = null
            }
            R.string.email -> {
                if (Utils.isEmailValid(s.toString().trim())) {
                    inputText?.error = null
                } else
                    inputText?.error = context?.getString(R.string.email_invalid)
            }
            R.string.password -> {
                if (Utils.isPasswordValid(s.toString().trim())) {
                    inputText?.error = null
                } else
                    inputText?.error = context?.getString(R.string.password_invalid)
            }
            R.string.conf_password -> {
                if (Utils.isPasswordValid(s.toString().trim())) {
                    inputText?.error = null
                } else
                    inputText?.error = context?.getString(R.string.password_invalid)
            }
            R.string.tag_empty_field_validation->{
                if (!s.toString().trim().isNullOrEmpty()) {
                    inputText?.error = null
                } else
                    inputText?.error = context?.getString(R.string.email_invalid)
            }
        }

    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }
}