package com.android.studentconnect.ui.registration.signin

import com.android.studentconnect.ui.base.ActBase
import com.arhatbaid.uhcl.ui.registration.signin.ISignInView
import com.google.firebase.firestore.FirebaseFirestore

class ISignInPresenterImpl(signInView: ISignInView, context: ActBase){

    private var signInView: ISignInView? = null
    private var context: ActBase? = null
    private val db = FirebaseFirestore.getInstance();

    init {
        this.signInView = signInView
        this.context = context
    }

}