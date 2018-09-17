package com.android.studentconnect.ui.registration.signin

import android.text.TextUtils
import android.util.Log
import com.android.studentconnect.R
import com.android.studentconnect.data.Users
import com.android.studentconnect.firebase.FetchUserData
import com.android.studentconnect.firebase.FirebaseTable
import com.android.studentconnect.utils.Utils
import com.android.studentconnect.utils.UtilsPrefsKey
import com.arhatbaid.uhcl.ui.registration.signin.ISignInView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.pixplicity.easyprefs.library.Prefs

class ISignInPresenterImpl(signInView: ISignInView, context: FragSignIn) : ISignInPresenter,
        FetchUserData.Callback {

    private var signInView: ISignInView? = null
    private var context: FragSignIn? = null

    init {
        this.signInView = signInView
        this.context = context
    }

    override fun validateCredentials(email: String, password: String, tag: Int) {
        if (signInView != null) {
            if (TextUtils.isEmpty(email)) {
                signInView?.errorInvalidEmailId(R.string.email_empty)
                return
            }

            if (!Utils.isEmailValid(email)) {
                signInView?.errorInvalidEmailId(R.string.email_invalid)
                return
            }

            if (TextUtils.isEmpty(password)) {
                signInView?.errorInvalidPassword(R.string.password_empty)
                return
            }

            if (!Utils.isPasswordValid(password)) {
                signInView?.errorInvalidPassword(R.string.password_invalid)
                return
            }

            signInView?.onSignInValidDataEntered(tag)
        }
    }

    override fun loginUser(email: String, password: String, tag: Int) {
        context!!.getFirebaseAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(FragSignIn::class.java.name, "signInWithEmail:success")
                        val fbUser = task.result.user
                        if (fbUser != null) {
                            when (tag) {
                                R.string.tag_login -> {
                                    onLoginClicked(fbUser)
                                }
                                R.string.tag_resend_email -> {
                                    onResendEmailClicked(fbUser)
                                }
                            }
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

    private fun onLoginError(task: Task<AuthResult>) {
        context?.hideProgress()
        signInView?.onError(task.exception!!.localizedMessage)
        Log.w(FragSignIn::class.java.name, "signInWithEmail:failure", task.exception)
    }

    private fun onLoginClicked(fbUser: FirebaseUser?) {
        if (fbUser!!.isEmailVerified) {
            Prefs.putBoolean(UtilsPrefsKey.is_login, true)
            FetchUserData(context!!.getActBase(), this).fetchUser(FirebaseTable.USER.email, fbUser.email!!)
//            FetchUniversityData(context!!.getActBase(), this).fetchUniversityList()
        } else {
            context?.hideProgress()
            onResendEmailClicked(fbUser)
            signInView?.onError(context?.getString(R.string.email_not_verified)!!)
        }
    }

    private fun onResendEmailClicked(user: FirebaseUser?) {
        user?.sendEmailVerification()?.addOnCompleteListener { task ->

            if (task.isSuccessful) {
                signInView?.onVerificationEmailSentSuccessfully("Email Sent successfully")
            } else {
                signInView?.onVerificationEmailFailed(task.exception!!.localizedMessage)
                Log.e(FragSignIn::class.java.name, "sendEmailVerification", task.exception)
            }

        }
    }

    override fun fetchedUserObj(user: Users?, isUserDataUploaded: Boolean) {
            if (isUserDataUploaded){
                if (user!!.is_phone_verified){
                    signInView?.goToHomeScreen()
                }else{
                    signInView?.goToNumberVerifyScreen()
                }
            }else{
                signInView?.goToUserDetailScreen()
            }
    }

    override fun forgotPassword(email: String) {

        if (TextUtils.isEmpty(email)) {
            signInView?.errorInvalidEmailId(R.string.email_empty)
            return
        }

        if (!Utils.isEmailValid(email)) {
            signInView?.errorInvalidEmailId(R.string.email_invalid)
            return
        }

        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        context!!.getActBase().showMessage(R.string.password_reset_email)
                    }else{
                        context!!.getActBase().onError(task.exception.toString())
                    }
                }
    }
}