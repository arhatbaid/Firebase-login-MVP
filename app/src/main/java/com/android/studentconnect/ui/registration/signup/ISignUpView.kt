package com.android.studentconnect.ui.registration.signup

import com.arhatbaid.uhcl.ui.base.IBaseView
import com.google.firebase.auth.FirebaseUser

interface ISignUpView : IBaseView {

    fun errorInvalidUniversity(description: Int)

    fun errorInvalidEmailId(description: Int)

    fun errorInvalidPassword(description: Int)

    fun errorInvalidConfPassword(description: Int)

    fun onSignUpFailed(description: String?)

    fun onSignUpValidDataEntered()

    fun onSignUpSucessfull(user: FirebaseUser)

    fun onVerificationEmailFailed(description: String)

    fun onVerificationEmailSucessfull(description: String)
}