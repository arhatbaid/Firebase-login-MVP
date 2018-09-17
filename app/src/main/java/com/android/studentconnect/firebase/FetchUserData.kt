package com.android.studentconnect.firebase

import com.android.studentconnect.data.University
import com.android.studentconnect.data.Users
import com.android.studentconnect.ui.base.ActBase

class FetchUserData(val context: ActBase, var callback: FetchUserData.Callback) {

    fun fetchUser(key: String, value: String) {
        val docRef = DB().getInstance().collection(FirebaseTable.USER_TAG).whereEqualTo(key, value)
        docRef.get().addOnCompleteListener {
            if (it.isSuccessful && !it.result.isEmpty) {
                val user = it.result.toObjects(Users::class.java)[0]
                callback.fetchedUserObj(user, true)
            } else {
                callback.fetchedUserObj(null, false)
            }
        }
    }

    interface Callback {
        fun fetchedUserObj(user: Users?, isUserDataUploaded: Boolean) {}
        fun fetchedUserList(arrUniversity: ArrayList<University>) {}
    }
}