package com.android.studentconnect.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings


/*TODO check the offline API call*/
open class DB {
    private var instance: FirebaseFirestore? = null

    fun getInstance(): FirebaseFirestore {
        if (instance == null) {
            instance = FirebaseFirestore.getInstance()
            val settings = FirebaseFirestoreSettings.Builder()
                    .setTimestampsInSnapshotsEnabled(true)
                    .setPersistenceEnabled(true)
                    .build()
            instance!!.setFirestoreSettings(settings)
        }
        return instance as FirebaseFirestore
    }
}
