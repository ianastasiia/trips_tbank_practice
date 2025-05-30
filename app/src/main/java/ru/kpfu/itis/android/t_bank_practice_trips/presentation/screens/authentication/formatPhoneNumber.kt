package ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.authentication

fun formatPhoneNumber(phone: String): String {
    if (phone.isEmpty()) return ""

    val cleanPhone = phone.replace(Regex("[^0-9]"), "").take(11)

    return when {
        cleanPhone.length <= 1 -> ""
        cleanPhone.length <= 4 -> "(${cleanPhone.substring(1)}"
        cleanPhone.length <= 7 -> "(${cleanPhone.substring(1, 4)}) ${cleanPhone.substring(4)}"
        cleanPhone.length <= 9 -> "(${cleanPhone.substring(1, 4)}) ${
            cleanPhone.substring(
                4, 7
            )
        }-${cleanPhone.substring(7)}"

        else -> "(${cleanPhone.substring(1, 4)}) ${
            cleanPhone.substring(
                4, 7
            )
        }-${cleanPhone.substring(7, 9)}-${cleanPhone.substring(9)}"
    }
}

fun cleanPhoneNumber(phone: String): String {
    return "7${phone.replace(Regex("[^0-9]"), "").take(10)}"
}