package com.arhatbaid.uhcl.ui.registration.signin

import com.arhatbaid.uhcl.ui.base.IBaseView
import com.google.firebase.auth.FirebaseUser

/**
 * Created by Arhat on 2/18/2018.
 */
interface ISignInView : IBaseView {
    fun errorInvalidEmailId(description: Int)

    fun errorInvalidPassword(description: Int)

    fun onSignInValidDataEntered(tag: Int)

    fun showNotVerifiedDialogue()

    fun goToHomeScreen()

    fun goToUserDetailScreen()

    fun goToNumberVerifyScreen()

    fun onVerificationEmailFailed(description: String)

    fun onVerificationEmailSentSuccessfully(description: String)
}