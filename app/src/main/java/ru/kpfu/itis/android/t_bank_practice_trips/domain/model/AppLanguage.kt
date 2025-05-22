package ru.kpfu.itis.android.t_bank_practice_trips.domain.model

enum class AppLanguage(
    val code: String,
    val displayName: String
) {
    RUSSIAN("ru", "Русский"),
    ENGLISH("en", "English");

    companion object {
        private fun fromCodeToDisplayName(code: String): AppLanguage? {
            return entries.find { it.code == code }
        }

        private fun fromDisplayNameToCode(displayName: String): AppLanguage? {
            return entries.find { it.displayName == displayName }
        }

        fun getDisplayNames(): List<String> {
            return entries.map { it.displayName }
        }

        fun String.toLanguageDisplayName(): String =
            AppLanguage.fromCodeToDisplayName(this)?.displayName ?: this

        fun String.toLanguageCode(): String =
            AppLanguage.fromDisplayNameToCode(this)?.code ?: this
    }
}