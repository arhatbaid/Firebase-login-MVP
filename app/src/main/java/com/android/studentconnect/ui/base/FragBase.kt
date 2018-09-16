package com.android.studentconnect.ui.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.arhatbaid.uhcl.ui.base.IBaseView
import com.google.firebase.auth.FirebaseAuth

abstract class FragBase : Fragment(),
        IBaseView {

    private var mActivity: ActBase? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ActBase) {
            this.mActivity = context
            getActBase().onFragmentAttached()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
    }

    override fun showProgress() {
        getActBase().showProgress()
    }

    override fun hideProgress() {
        getActBase().hideProgress()
    }

    override fun onError(resId: Int) {
        getActBase().onError(resId)
    }

    override fun onError(message: String) {
        getActBase().onError(message)
    }

    override fun showMessage(message: String) {
        getActBase().showMessage(message)
    }

    override fun showMessage(resId: Int) {
        getActBase().showMessage(resId)
    }

    override fun isNetworkConnected(): Boolean {
        return getActBase().isNetworkConnected()
    }

    override fun hideKeyboard() {
        getActBase().hideKeyboard()
    }

    protected abstract fun setUpView(view: View)

    fun getActBase(): ActBase {
        return mActivity!!
    }

    @Synchronized
    override fun getFirebaseAuth(): FirebaseAuth {
        return getActBase().getFirebaseAuth()
    }

    @Synchronized
    override fun setFirebaseAuth(mAuth: FirebaseAuth) {
        getActBase().setFirebaseAuth(mAuth)
    }

    override fun <T> changeActivity(classType: Class<T>?, bundle: Bundle?) {
        getActBase().changeActivity(classType, bundle)
    }

    override fun replaceFragment(frag: Fragment, bundle: Bundle?) {
        getActBase().replaceFragment(frag, bundle)
    }

    override fun addFragment(frag: Fragment, bundle: Bundle?) {
        getActBase().addFragment(frag, bundle)
    }

    override fun initFrag(frag: Fragment) {
        getActBase().initFrag(frag)
    }


    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }

}