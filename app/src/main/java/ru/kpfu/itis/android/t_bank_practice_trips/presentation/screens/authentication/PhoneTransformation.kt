package ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.authentication

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation


class PhoneTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val digits = text.text.take(10)
        val (formatted, digitPositions) = formatPhoneWithPositions(digits)

        return TransformedText(
            AnnotatedString(formatted),
            PhoneOffsetMapping(digits, digitPositions)
        )
    }

    private fun formatPhoneWithPositions(phone: String): Pair<String, List<Int>> {
        val formatted = StringBuilder()
        val digitPositions = mutableListOf<Int>()

        when {
            phone.isEmpty() -> return "" to emptyList()
            phone.length <= 3 -> {
                phone.forEach { char ->
                    formatted.append(char)
                    digitPositions.add(formatted.length - 1)
                }
            }
            phone.length <= 6 -> {
                formatted.append("(")
                phone.take(3).forEach { char ->
                    formatted.append(char)
                    digitPositions.add(formatted.length - 1)
                }
                formatted.append(") ")
                phone.drop(3).forEach { char ->
                    formatted.append(char)
                    digitPositions.add(formatted.length - 1)
                }
            }
            phone.length <= 8 -> {
                formatted.append("(")
                phone.take(3).forEach { char ->
                    formatted.append(char)
                    digitPositions.add(formatted.length - 1)
                }
                formatted.append(") ")
                phone.substring(3, 6).forEach { char ->
                    formatted.append(char)
                    digitPositions.add(formatted.length - 1)
                }
                formatted.append("-")
                phone.drop(6).forEach { char ->
                    formatted.append(char)
                    digitPositions.add(formatted.length - 1)
                }
            }
            else -> {
                formatted.append("(")
                phone.take(3).forEach { char ->
                    formatted.append(char)
                    digitPositions.add(formatted.length - 1)
                }
                formatted.append(") ")
                phone.substring(3, 6).forEach { char ->
                    formatted.append(char)
                    digitPositions.add(formatted.length - 1)
                }
                formatted.append("-")
                phone.substring(6, 8).forEach { char ->
                    formatted.append(char)
                    digitPositions.add(formatted.length - 1)
                }
                formatted.append("-")
                phone.drop(8).forEach { char ->
                    formatted.append(char)
                    digitPositions.add(formatted.length - 1)
                }
            }
        }

        return formatted.toString() to digitPositions
    }

    private class PhoneOffsetMapping(
        private val digits: String,
        private val digitPositions: List<Int>
    ) : OffsetMapping {
        private val n = digits.length

        override fun originalToTransformed(offset: Int): Int {
            return when {
                offset < 0 -> 0
                offset < n -> digitPositions.getOrElse(offset) { 0 }
                else -> digitPositions.lastOrNull()?.plus(1) ?: 0
            }
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset < 0) return 0
            return digitPositions.count { it < offset }
        }
    }
}