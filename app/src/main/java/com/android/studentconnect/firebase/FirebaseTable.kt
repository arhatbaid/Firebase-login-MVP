package com.android.studentconnect.firebase

object FirebaseTable {

    val USER_TAG = "User"
    val UNIVERSITY_TAG = "University"
    val MAJOR_TAG = "Major"
    val ACCOMMODATION_ATG = "Accommodation"
    val ACCOMMODATION_FAVOURITE_TAG = "AccommodationFavourite"

    object USER {
        val id = "id"
        val first_name = "first_name"
        val email = "email"
        val country_code = "country_code"
        val phone_number = "phone_number"
        val is_phone_verified = "is_phone_verified"
        val gender = "gender"
        val nationality = "nationality"
        val dob = "dob"
        val major = "major"
        val major_id = "major_id"
        val university = "university"
        val university_id = "university_id"
        val profile_pic = "profile_pic"
        val device_model = "device_model"
        val os_version = "os_version"
        val app_version = "major_id"
        val platform = "platform"
    }

    class MAJOR {
        val id = "id"
        val name = "name"
        val university_id = "university_id"
        val course_career = "course_career"
    }


    class UNIVERSITY {
        val id = "id"
        val name = "name"
        val domain = "domain"
        val website = "website"
        val spam_link = "spam_link"
    }


    class ACCOMMODATION {
        val id = "id"
        val apartment_name = "apartment_name"
        val bathrooms = "bathrooms"
        val bedrooms = "bedrooms"
        val address = "address"
        val comment = "comment"
        val diet = "diet"
        val duration = "duration"
        val gender = "gender"
        val nationality = "nationality"
        val pet = "pet"
        val price = "price"
        val roommates = "roommates"
        val user_id = "user_id"
        val lat_long = "lat_long"
        val starts_from = "starts_from"
        val post_time = "post_time"
    }
}