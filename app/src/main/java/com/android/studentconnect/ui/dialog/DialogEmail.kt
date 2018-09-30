package com.android.studentconnect.ui.dialog

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.studentconnect.R
import com.android.studentconnect.ui.base.DialogBase
import com.android.studentconnect.utils.Utils
import kotlinx.android.synthetic.main.dialog_email.*

class DialogEmail : DialogBase(),
        View.OnClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_email, container, false)
    }


    override fun setUp(view: View) {
        lblCancel.setOnClickListener(this)
        lblOk.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            lblOk -> {
                getActBase().showProgress()
                getUniversityDetail()
            }
            lblCancel -> {
                getActBase().hideProgress()
                Utils.hideKeyboard(txtEmail)
                dismiss()
            }
        }
    }

    private fun getUniversityDetail() {
        val email = txtEmail.text.toString().trim()

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
    }

}