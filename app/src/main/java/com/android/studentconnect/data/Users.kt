package com.android.studentconnect.data


data class Users(var id: String = "", var first_name: String = "", var last_name: String = "", var email: String = "",
                 var country_code: String = "", var phone_number: String = "", var is_phone_verified: Boolean = false,
                 var gender: String = "", var nationality: String = "", var dob: Long = 0, var major: String = "",
                 var major_id: String = "", var university: String = "", var university_id: String = "",
                 var profile_pic: String = "", var device_model: String = "", var os_version: String = "",
                 var app_version: String = "", var platform: String = "") {

}