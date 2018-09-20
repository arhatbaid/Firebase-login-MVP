package com.android.studentconnect.ui.dialog

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.studentconnect.R
import com.android.studentconnect.ui.base.DialogBase
import com.android.studentconnect.ui.registration.signin.FragSignIn
import com.android.studentconnect.utils.TextWatcher
import com.android.studentconnect.utils.Utils
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.dialog_resend_email.*

class DialogResendEmail : DialogBase(),
        View.OnClickListener {

    override fun onActivityCreated(arg0: Bundle?) {
        super.onActivityCreated(arg0)
        dialog.window!!
                .attributes.windowAnimations = R.style.AppTheme_DialogAnimation
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_resend_email, container, false)
    }

    override fun setUp(view: View) {
        txtEmail.addTextChangedListener(TextWatcher(getActBase(), inpTxtEmail, R.string.email))
        txtPassword.addTextChangedListener(TextWatcher(getActBase(), inpTxtPassword, R.string.password))

        lblCancel.setOnClickListener(this)
        lblSendMail.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v) {
            lblSendMail -> {
                getActBase().showProgress()
                onResendEmailClicked()
            }
            lblCancel -> {
                Utils.hideKeyboard(txtEmail)
                Utils.hideKeyboard(txtPassword)
                dismiss()
            }
        }
    }

    private fun onResendEmailClicked() {

        val email = txtEmail.text.toString().trim()
        val password = txtPassword.text.toString().trim()

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

        if (TextUtils.isEmpty(password)) {
            inpTxtPassword.error = getString(R.string.password_empty)
            getActBase().hideProgress()
            return
        }

        if (!Utils.isPasswordValid(password)) {
            inpTxtPassword.error = getString(R.string.password_invalid)
            getActBase().hideProgress()
            return
        }

        loginUser(email, password)
    }


    fun loginUser(email: String, password: String) {
        getActBase().getFirebaseAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(FragSignIn::class.java.name, "signInWithEmail:success")
                        val fbUser = task.result.user
                        if (fbUser != null) {
                            resendEmail(fbUser)
                        } else {
                            // If sign in fails, display a message to the user.
                            onLoginError(task)
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        onLoginError(task)
                    }
                }
    }

    private fun resendEmail(user: FirebaseUser?) {
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            getActBase().hideProgress()
            if (task.isSuccessful) {
                getActBase().showMessage("Email Sent successfully")
                Utils.hideKeyboard(txtEmail)
                Utils.hideKeyboard(txtPassword)
                dismiss()
            } else {
                getActBase().showMessage(task.exception!!.localizedMessage)
                Log.e(FragSignIn::class.java.name, "sendEmailVerification", task.exception)
            }
        }
    }

    private fun onLoginError(task: Task<AuthResult>) {
        getActBase().hideProgress()
        getActBase().onError(task.exception!!.localizedMessage)
        Log.w(FragSignIn::class.java.name, "signInWithEmail:failure", task.exception)
    }


}