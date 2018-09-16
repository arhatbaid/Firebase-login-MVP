package com.android.studentconnect.ui.registration.signup

import com.google.firebase.auth.FirebaseUser

interface ISignUpPresenter {
    fun validateCredentials(email: String, emailExt: String, password: String, confPassword: String)

    fun sendValidationEmail(user: FirebaseUser?)
}