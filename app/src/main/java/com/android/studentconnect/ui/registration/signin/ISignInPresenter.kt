package com.android.studentconnect.ui.registration.signin

import com.google.firebase.auth.FirebaseUser

interface ISignInPresenter {

    fun validateCredentials(email: String, password: String)

}