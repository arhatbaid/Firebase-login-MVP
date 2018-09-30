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
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.android.studentconnect.R
import com.android.studentconnect.ui.base.FragBase
import com.android.studentconnect.ui.dialog.DialogEmail
import com.android.studentconnect.ui.dialog.DialogForgotPassword
import com.android.studentconnect.ui.dialog.DialogResendEmail
import com.android.studentconnect.ui.registration.signup.FragSignUp
import com.android.studentconnect.utils.TextWatcher
import com.arhatbaid.uhcl.ui.registration.signin.ISignInView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.frag_sign_in.*
import com.thefinestartist.finestwebview.FinestWebView



class FragSignIn : FragBase(),
        ISignInView,
        View.OnClickListener,
        View.OnFocusChangeListener {

    private var signinPresenter: ISignInPresenterImpl? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //Sign out as soon as the user enters the sign in screen
        FirebaseAuth.getInstance().signOut()

        return inflater.inflate(R.layout.frag_sign_in, container, false)
    }

    override fun setUpView(view: View) {
        signinPresenter = ISignInPresenterImpl(this, this)

        setTextSpannable()

        txtEmail.addTextChangedListener(TextWatcher(getActBase(), inpTxtEmail, R.string.email))
        txtPassword.addTextChangedListener(TextWatcher(getActBase(), inpTxtPassowrd, R.string.password))

        txtEmail.setOnFocusChangeListener(this)
        txtPassword.setOnFocusChangeListener(this)

        lblResendEmail.setOnClickListener(this)
        lblForgotPassword.setOnClickListener(this)
        btnLogin.setOnClickListener(this)
        lblHowToVerify.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            lblResendEmail -> {
                showDialogFragment(DialogResendEmail(), getString(R.string.tag_resend_email))
            }
            lblForgotPassword -> {
                showDialogFragment(DialogForgotPassword(), getString(R.string.tag_forgot_password))
            }
            btnLogin -> {
                signinPresenter?.validateCredentials(txtEmail.text.toString().trim(),
                        txtPassword.text.toString().trim())
            }
            lblHowToVerify -> {
                //Open the spam link
                showDialogFragment(DialogEmail(), getString(R.string.how_to_verify))
                FinestWebView.Builder(getActBase()).show("https://myspam.uhcl.edu:28443/")
            }
        }
    }

    override fun errorInvalidEmailId(description: Int) {
        inpTxtEmail.error = getString(description)
    }

    override fun errorInvalidPassword(description: Int) {
        inpTxtPassowrd.error = getString(description)
    }

    override fun showNotVerifiedDialogue() {

    }

    override fun goToHomeScreen() {
        showMessage("Home Screen")
    }

    override fun goToUserDetailScreen() {
        showMessage("User Details")
    }

    override fun goToNumberVerifyScreen() {
        showMessage("Number Verification")
    }

    override fun onVerificationEmailFailed(description: String) {
        onError(description)
    }

    override fun onVerificationEmailSentSuccessfully(description: String) {
        onError(description)
    }

    //TODO edittext border color when focused
    override fun onFocusChange(v: View?, p1: Boolean) {
        inpTxtEmail.setBoxStrokeColor(resources.getColor(R.color.theme_color_light))
        inpTxtPassowrd.setBoxStrokeColor(resources.getColor(R.color.theme_color_light))
        when(v){
            txtEmail -> {
                inpTxtEmail.setBoxStrokeColor(resources.getColor(R.color.theme_color))
            }
            txtPassword -> {
                inpTxtPassowrd.setBoxStrokeColor(resources.getColor(R.color.theme_color))
            }
        }
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