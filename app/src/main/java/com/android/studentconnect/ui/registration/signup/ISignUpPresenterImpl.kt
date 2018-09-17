package com.android.studentconnect.ui.registration.signup

import android.text.TextUtils
import android.util.Log
import com.android.studentconnect.R
import com.android.studentconnect.ui.base.ActBase
import com.android.studentconnect.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ISignUpPresenterImpl(signUpView: ISignUpView, mContext: ActBase) : ISignUpPresenter {

    private var signUpView: ISignUpView? = null
    private var mContext: ActBase? = null
    private var mAuth: FirebaseAuth? = null

    init {
        this.signUpView = signUpView
        this.mContext = mContext
        this.mAuth = FirebaseAuth.getInstance()
    }

    override fun validateCredentials(email: String, emailExt: String, password: String, confPassword: String) {
        var emailFinal = ""
        if (signUpView != null) {

            if (email.contains("@")) {
                var arrEmail = email.split("@")
                if (arrEmail.size >= 2) {
                    emailFinal = arrEmail[0].plus(emailExt)
                }
            } else {
                emailFinal = email.plus(emailExt)
            }

            if (emailExt.equals("xxx.edu")) {
                signUpView?.errorInvalidUniversity(R.string.error_field_required)
                return
            }

            if (TextUtils.isEmpty(emailFinal)) {
                signUpView?.errorInvalidEmailId(R.string.email_empty)
                return
            }

            if (!Utils.isEmailValid(emailFinal)) {
                signUpView?.errorInvalidEmailId(R.string.email_invalid)
                return
            }

            if (TextUtils.isEmpty(password)) {
                signUpView?.errorInvalidPassword(R.string.password_empty)
                return
            }

            if (!Utils.isPasswordValid(password)) {
                signUpView?.errorInvalidPassword(R.string.password_invalid)
                return
            }

            if (TextUtils.isEmpty(confPassword)) {
                signUpView?.errorInvalidConfPassword(R.string.password_empty)
                return
            }

            if (!Utils.isPasswordValid(confPassword)) {
                signUpView?.errorInvalidConfPassword(R.string.password_invalid)
                return
            }

            if (!password.equals(confPassword)) {
                signUpView?.errorInvalidConfPassword(R.string.password_mismatch)
                return
            }
            signUpView?.onSignUpValidDataEntered()
            onSignUpClicked(emailFinal, password)
        }
    }

    private fun onSignUpClicked(email: String, password: String) {

        mAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        signUpView?.onSignUpSucessfull(task.result.user)
                    } else {
                        Log.w(FragSignUp::class.java.name, "signUpWithEmail:failure", task.exception)
                        signUpView?.onSignUpFailed(task.exception?.message)
                    }
                }
    }

    override fun sendValidationEmail(user: FirebaseUser?) {
        user?.sendEmailVerification()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                signUpView?.onVerificationEmailSucessfull("")
            } else {
                Log.e(FragSignUp::class.java.name, "sendEmailVerification", task.exception)
            }
        }
    }
}