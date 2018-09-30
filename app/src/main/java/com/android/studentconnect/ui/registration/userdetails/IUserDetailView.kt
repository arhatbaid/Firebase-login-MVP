package com.android.studentconnect.ui.registration.userdetails

import com.android.studentconnect.data.University

interface IUserDetailView {

    fun onUniversityDataFetched(university: University?)
}