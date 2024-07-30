package com.example.myapplication.ui.utils

fun String?.isValidPassword(): Boolean {
    val fullRegex = "^[a-zA-Z0-9@$.!%*?&]+$".toRegex()
    val numberRegex = ".*[0-9].*".toRegex()
    val symbolsRegex = ".*[@$.!%*?&].*".toRegex()
    val uppercaseRegex = ".*[A-Z].*".toRegex()
    val lowercaseRegex = ".*[a-z].*".toRegex()

    return when {

        this.isNullOrBlank() -> false

        this.length < 8 || this.length > 20 -> false


        !this.contains(numberRegex) -> false

        !this.contains(symbolsRegex) -> false

        !this.contains(uppercaseRegex) -> false

        !this.contains(lowercaseRegex) -> false

        else -> this.matches(fullRegex)
    }
}

fun String?.isValidEmail(): Boolean {
    val regex = "^[a-z0-9+_.-]+@[a-z.-]+\\.[a-z0-9]{2,}$".toRegex()
    return if (this.isNullOrBlank()) {
        false
    } else {
        this.matches(regex)
    }
}

fun String?.isConfirmedPassword(confirmPassword: String?): Boolean {
    return this == confirmPassword
}

fun String.transformPrice(currency: String): String {
    val stringBuilder = StringBuilder()
    var count = 0
    stringBuilder.append("$currency ")

    for (i in this.indices) {
        if (this[i].isDigit()) {
            stringBuilder.append(this[i])
            count++

            if (count % 3 == 2 && i != this.lastIndex) {
                stringBuilder.append(".")
            }
        }
    }
    return stringBuilder.toString()
}
