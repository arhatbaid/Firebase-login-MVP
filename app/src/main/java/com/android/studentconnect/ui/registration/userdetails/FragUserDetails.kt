package com.android.studentconnect.ui.registration.userdetails

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.android.studentconnect.R
import com.android.studentconnect.data.University
import com.android.studentconnect.ui.base.FragBase
import com.android.studentconnect.utils.UtilsPrefsKey
import com.esafirm.imagepicker.features.ImagePicker
import com.pixplicity.easyprefs.library.Prefs
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.frag_user_details.*
import java.io.File

class FragUserDetails : FragBase(),
        IUserDetailView,
        View.OnClickListener,
        DatePickerDialog.OnDateSetListener{

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_user_details, container, false)
    }

    override fun onClick(v: View?) {
        when (v) {
            imgUserProfile -> {
                onProfileImageClick()
            }
            txtDOB -> {

            }
        }
    }

    override fun setUpView(view: View) {

        //Call university api and then show the ui.
        getActBase().showProgress()

        imgUserProfile.setOnClickListener(this)
        txtDOB.setOnClickListener(this)
    }


    override fun onUniversityDataFetched(university: University?) {
        getActBase().hideProgress()
        if (university != null){
            txtUniversityName.setText(university.name)
            Prefs.putString(UtilsPrefsKey.university_id, university.id)
            Prefs.putString(UtilsPrefsKey.university_name, university.name)
            Prefs.putString(UtilsPrefsKey.university_domain, university.domain)
            Prefs.putString(UtilsPrefsKey.university_spamlink, university.spam_link)
            Prefs.putString(UtilsPrefsKey.university_website, university.website)
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image = ImagePicker.getFirstImageOrNull(data)

            Picasso.get().load(File(image.path))
                    .resize(300, 300)
                    .placeholder(R.drawable.ic_profile)
                    .onlyScaleDown()
                    .into(imgUserProfile);
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun onProfileImageClick() {
        ImagePicker.create(this) // Activity or Fragment
                .toolbarArrowColor(Color.WHITE)
                .includeVideo(false)
                .enableLog(true)
                .limit(1)
                .start()
    }

    private fun onDobClicked() {
      /*  val date = AppHelper.getDate()
        val datePickerDialog = DatePickerDialog(
                getActBase(), this, date.get(2), date.get(0), date.get(1))
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 410240038000 *//*(13 years in millis)*//*
//        datePickerDialog.getButton(Dialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.colorAccent));
        datePickerDialog.setOnDateSetListener(this)
        datePickerDialog.show()*/
    }
}