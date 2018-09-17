package com.android.studentconnect.utils

import android.content.Context
import android.net.ConnectivityManager
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import com.android.studentconnect.ui.base.ActBase
import java.util.regex.Pattern


object Utils {

    fun isPasswordValid(password: String): Boolean {
        if (password.length < 6) {
            return false
        }
        val regex = "^[a-zA-Z0-9]+$"

        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isConnectedToNetwork(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    fun hideKeyboard(context: ActBase) {
        val view = context.currentFocus
        if (view != null) {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}