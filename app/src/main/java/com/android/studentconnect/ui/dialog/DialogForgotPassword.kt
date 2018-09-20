package com.android.studentconnect.ui.dialog

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.studentconnect.R
import com.android.studentconnect.ui.base.DialogBase
import com.android.studentconnect.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.dialog_foget_password.*

class DialogForgotPassword : DialogBase(),
        View.OnClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_foget_password, container, false)
    }

    override fun setUp(view: View) {
        lblCancel.setOnClickListener(this)
        lblResetPassword.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            lblResetPassword -> {
                getActBase().showProgress()
                forgotPassword()
            }
            lblCancel -> {
                getActBase().hideProgress()
                Utils.hideKeyboard(txtEmail)
                dismiss()
            }
        }
    }

    fun forgotPassword() {
        val email = txtEmail.text.toString().trim()

        if (TextUtils.isEmpty(email)) {
            inpTxtEmail.error = getString(R.string.email_empty)
            getActBase().hideProgress()
            return
        }

        if (!Utils.isEmailValid(email)) {
            inpTxtEmail.error = getString(R.string.email_invalid)
            getActBase().hideProgress()
            return
        }
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    getActBase().hideProgress()
                    if (task.isSuccessful) {
                        getActBase().showMessage(R.string.password_reset_email)
                    } else {
                        getActBase().onError(task.exception.toString())
                    }
                }
    }

}