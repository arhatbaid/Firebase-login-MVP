package com.android.studentconnect.ui.registration.signin

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.studentconnect.R
import com.android.studentconnect.ui.base.FragBase
import com.android.studentconnect.ui.registration.signup.FragSignUp
import com.arhatbaid.uhcl.ui.registration.signin.ISignInView
import kotlinx.android.synthetic.main.frag_sign_in.*

class FragSignIn : FragBase(),
        ISignInView,
        View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_sign_in, container, false)
    }

    override fun setUpView(view: View) {
        setTextSpannable()

        lblResendEmail.setOnClickListener(this)
        lblForgotPassword.setOnClickListener(this)
        btnLogin.setOnClickListener(this)
        lblHowToVerify.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            lblResendEmail -> {

            }
            lblForgotPassword -> {

            }
            btnLogin -> {

            }
            lblHowToVerify -> {

            }
        }
    }


    override fun errorInvalidEmailId(description: Int) {

    }

    override fun errorInvalidPassword(description: Int) {

    }

    override fun showNotVerifiedDialogue() {

    }

    override fun goToHomeScreen() {

    }

    override fun goToUserDetailScreen() {

    }

    override fun goToNumberVerifyScreen() {

    }

    override fun onVerificationEmailFailed(description: String) {

    }

    override fun onVerificationEmailSentSuccessfully(description: String) {

    }


    /*================================Misc=============================*/
    private fun setTextSpannable() {

        val sp = SpannableString(getString(R.string.not_a_member))
        sp.setSpan(StyleSpan(Typeface.BOLD), 14, sp.length, 0)
        sp.setSpan(object : ClickableSpan() {
            override fun onClick(v: View) {
                replaceFragment(FragSignUp(), null)
            }
        }, 14, sp.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        sp.setSpan(ForegroundColorSpan(resources.getColor(R.color.theme_text_color)), 14, sp.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        sp.setSpan(RelativeSizeSpan(1.05f), 14, sp.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        lblSignup.setText(sp)
        lblSignup.setMovementMethod(LinkMovementMethod.getInstance());
    }


}