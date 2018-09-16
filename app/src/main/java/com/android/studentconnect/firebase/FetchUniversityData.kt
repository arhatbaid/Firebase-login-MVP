package com.android.studentconnect.firebase

import com.android.studentconnect.data.University
import com.android.studentconnect.ui.base.ActBase
import com.google.firebase.firestore.FirebaseFirestore


class FetchUniversityData(val context: ActBase, var callback: Callback) {


    fun fetchUniversityList() {
        val arrUniversity = ArrayList<University>()

        DB().getInstance().collection(FirebaseTable.UNIVERSITY_TAG)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (document in it.result) {
                            arrUniversity.add(document.toObject(University::class.java))
                        }
                        callback.fetchedUniversityList(arrUniversity)
                    } else {
                        context.onError(it.exception?.localizedMessage ?: "")
                    }
                }
    }


    fun fetchUniversityDetail(key: String, value: String) {
        val docRef = DB().getInstance().collection(FirebaseTable.UNIVERSITY_TAG).whereEqualTo(key, value).limit(1)

        docRef.get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (document in it.result) {
                            callback.fetchedUniversityObj(document.toObject(University::class.java))
                            break
                        }
                    } else {
                        context.onError(it.exception?.localizedMessage ?: "")
                    }
                }
    }


    interface Callback {
        fun fetchedUniversityObj(university: University?) {}
        fun fetchedUniversityList(arrUniversity: ArrayList<University>) {}
    }
}