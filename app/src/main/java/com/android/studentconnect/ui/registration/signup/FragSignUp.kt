package com.android.studentconnect.ui.registration.signup

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
import com.android.studentconnect.data.University
import com.android.studentconnect.firebase.FetchUniversityData
import com.android.studentconnect.ui.base.FragBase
import com.android.studentconnect.ui.dialog.DialogSingleChoice
import com.android.studentconnect.utils.TextWatcher
import com.android.studentconnect.utils.UtilsPrefsKey
import com.google.firebase.auth.FirebaseUser
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.frag_sign_up.*

class FragSignUp : FragBase(),
        ISignUpView,
        View.OnClickListener,
        FetchUniversityData.Callback,
        DialogSingleChoice.Callback {

    private var signupPresenter: ISignUpPresenterImpl? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_sign_up, container, false)
    }

    override fun setUpView(view: View) {
        signupPresenter = ISignUpPresenterImpl(this, getActBase())

        setTextSpannable()

        txtUniversityName.addTextChangedListener(TextWatcher(getActBase(), inpTxtUniversityName, R.string.university_name))
        txtPassword.addTextChangedListener(TextWatcher(getActBase(), inpTxtPassowrd, R.string.password))
        txtEmail.addTextChangedListener(TextWatcher(getActBase(), inpTxtEmail,R.string.tag_empty_field_validation))
        txtConfPassword.addTextChangedListener(TextWatcher(getActBase(), inpTxtConfPassowrd, R.string.conf_password))

        txtUniversityName.setOnClickListener(this)
        btnSignUp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            txtUniversityName -> {
                FetchUniversityData(getActBase(), this).fetchUniversityList()
            }
            btnSignUp -> {
                showProgress()
                signupPresenter?.validateCredentials(txtEmail.text.toString().trim(),
                        lblEmailExt.text.toString().trim(),
                        txtPassword.text.toString().trim(),
                        txtConfPassword.text.toString().trim())
            }
        }
    }

    override fun fetchedUniversityList(arrUniversity: ArrayList<University>) {
        if (arrUniversity.isEmpty()) {
            //Show error
            return
        }
        val dialog = DialogSingleChoice(getActBase(), this, R.string.select_university)
        dialog.show(arrUniversity)
    }

    override fun onDialogItemSelected(items: ArrayList<*>, position: Int) {
        if (items[position] is University) {
            val university = items[position] as University
            txtUniversityName.setText(university.name)
            lblEmailExt.setText(university.domain)
            Prefs.putString(UtilsPrefsKey.university_id, university.id)
            Prefs.putString(UtilsPrefsKey.university_name, university.name)
            Prefs.putString(UtilsPrefsKey.university_domain, university.domain)
            Prefs.putString(UtilsPrefsKey.university_spamlink, university.spam_link)
            Prefs.putString(UtilsPrefsKey.university_website, university.website)
        } else {
            //Show error
        }
    }

    override fun errorInvalidUniversity(description: Int) {
        inpTxtUniversityName.error = getString(description)
        hideProgress()
    }

    override fun errorInvalidEmailId(description: Int) {
        inpTxtEmail.error = getString(description)
        hideProgress()
    }

    override fun errorInvalidPassword(description: Int) {
        inpTxtPassowrd.error = getString(description)
        hideProgress()
    }

    override fun errorInvalidConfPassword(description: Int) {
        inpTxtConfPassowrd.error = getString(description)
        hideProgress()
    }

    override fun onSignUpFailed(description: String?) {
        getActBase().onError(description!!)
        hideProgress()
    }

    override fun onSignUpValidDataEntered() {
        inpTxtEmail.error = null
        inpTxtPassowrd.error = null
        inpTxtConfPassowrd.error = null
    }

    override fun onSignUpSucessfull(user: FirebaseUser) {
        hideProgress()
        showMessage("Sign up successful")
        getActBase().supportFragmentManager.popBackStack()
    }

    override fun onVerificationEmailFailed(description: String) {

    }

    override fun onVerificationEmailSucessfull(description: String) {

    }

    /*=========================Misc=====================*/
    private fun setTextSpannable() {
        val spSignIn = SpannableString(getString(R.string.already_have_an_account))
        spSignIn.setSpan(StyleSpan(Typeface.BOLD), 24, spSignIn.length, 0)
        spSignIn.setSpan(object : ClickableSpan() {
            override fun onClick(v: View) {
                getActBase().supportFragmentManager.popBackStack()
            }
        }, 24, spSignIn.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spSignIn.setSpan(ForegroundColorSpan(resources.getColor(R.color.theme_text_color)), 24, spSignIn.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spSignIn.setSpan(RelativeSizeSpan(1.05f), 24, spSignIn.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        lblSignIn.setText(spSignIn)
        lblSignIn.setMovementMethod(LinkMovementMethod.getInstance());


        val spTnC = SpannableString(getString(R.string.tnc))
        spTnC.setSpan(StyleSpan(Typeface.BOLD), 37, spTnC.length, 0)
        spTnC.setSpan(object : ClickableSpan() {
            override fun onClick(v: View) {
                //Open Tnc in webview
            }
        }, 37, spTnC.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spTnC.setSpan(ForegroundColorSpan(resources.getColor(R.color.theme_text_color)), 37, spTnC.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spTnC.setSpan(RelativeSizeSpan(1.05f), 37, spTnC.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        lblTnC.setText(spTnC)
        lblTnC.setMovementMethod(LinkMovementMethod.getInstance());
    }


}