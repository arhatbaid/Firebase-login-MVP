package com.android.studentconnect.data

data class University(val id: String = "", val name: String= "", val domain: String = "",
                      val website: String = "", val spam_link: String = ""){

    override fun toString(): String {
        return name
    }

}