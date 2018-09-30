package com.android.studentconnect.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.studentconnect.R
import com.android.studentconnect.ui.registration.signin.FragSignIn
import com.android.studentconnect.ui.registration.userdetails.FragUserDetails
import com.android.studentconnect.utils.Utils
import com.arhatbaid.uhcl.ui.base.IBaseView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.act_base.*
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

open class ActBase : AppCompatActivity(),
        IBaseView,
        FragBase.Callback {

    private var mAuth: FirebaseAuth? = null

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO: DI init

        setContentView(R.layout.act_base)
        initFrag(FragSignIn())
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun onError(resId: Int) {
        Toast.makeText(this, resId, Toast.LENGTH_LONG).show()

    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    }

    override fun showMessage(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    }

    override fun isNetworkConnected(): Boolean {
        return Utils.isConnectedToNetwork(this)
    }

    override fun hideKeyboard() {
        Utils.hideKeyboard(this)
    }

    @Synchronized
    override fun getFirebaseAuth(): FirebaseAuth {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance()
        }
        return mAuth!!
    }

    @Synchronized
    override fun setFirebaseAuth(mAuth: FirebaseAuth) {
        this.mAuth = mAuth
    }

    override fun <T> changeActivity(classType: Class<T>?, bundle: Bundle?) {
        val intent = Intent(this, classType)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    override fun replaceFragment(frag: Fragment, bundle: Bundle?) {
        val ft = supportFragmentManager?.beginTransaction()
        if (bundle != null) frag.arguments = bundle
        ft?.addToBackStack(frag::class.java.getName())
        ft?.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left,
                R.anim.slide_in_right, R.anim.slide_out_right)
        ft?.replace(R.id.fragContainer, frag, frag::class.java.getName())
        ft?.commit()
    }

    override fun showDialogFragment(frag: DialogFragment, tag: String) {
        val ft = supportFragmentManager.beginTransaction()
        val prev = supportFragmentManager.findFragmentByTag(tag)
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        frag.show(ft, tag)
        frag.setCancelable(false)
    }

    override fun dismissDialogFragment(frag: DialogFragment) {
        frag.dismiss()
    }

    override fun addFragment(frag: Fragment, bundle: Bundle?) {
        val ft = supportFragmentManager?.beginTransaction()
        if (bundle != null) frag.arguments = bundle
        ft?.addToBackStack(frag::class.java.getName())
        ft?.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left,
                R.anim.slide_in_right, R.anim.slide_out_right)
        ft?.add(R.id.fragContainer, frag, frag::class.java.getName())
        ft?.commit()
    }

    override fun initFrag(frag: Fragment) {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragContainer, frag, frag::class.java.getName())
                .commit()
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {
        Log.d(tag, "Detached")
    }
}