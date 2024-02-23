package com.oqq.pocketrecipe.utils

import android.text.InputFilter
import android.text.Spanned
import android.util.Patterns

class EmailInputFilter: InputFilter {
    override fun filter(
        p0: CharSequence?,
        p1: Int,
        p2: Int,
        p3: Spanned?,
        p4: Int,
        p5: Int
    ): CharSequence? {
        val input = p0.toString()
        val newText = p3.toString().substring(0,p4)+ input + p3.toString().substring(p5)
        return if (isValidEmail(newText)){
            null
        }
        else {
            ""
        }
    }

    private fun isValidEmail(email:String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}