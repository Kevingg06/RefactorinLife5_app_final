package com.example.myapplication.ui.utils

fun String?.isValidPassword(): Boolean {
    val fullRegex = "^[a-zA-Z0-9@$.!%*?&]+$".toRegex()
    val numberRegex = ".*[0-9].*".toRegex()
    val symbolsRegex = ".*[@$.!%*?&].*".toRegex()
    val uppercaseRegex = ".*[A-Z].*".toRegex()
    val lowercaseRegex = ".*[a-z].*".toRegex()

    return when {
        this.isNullOrEmpty() -> false

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
    return if (this.isNullOrEmpty()) {
        false
    } else {
        this.matches(regex)
    }
}