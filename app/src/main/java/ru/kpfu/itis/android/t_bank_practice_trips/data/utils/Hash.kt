package ru.kpfu.itis.android.t_bank_practice_trips.data.utils

import java.security.MessageDigest

fun String.sha256(): String {
    val bytes = this.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("") { str, it -> str + "%02x".format(it) }
}