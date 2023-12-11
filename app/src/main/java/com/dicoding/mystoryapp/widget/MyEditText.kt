package com.dicoding.mystoryapp.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dicoding.mystoryapp.R
import java.lang.Error

class MyEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : AppCompatEditText(
    context,attrs
), View.OnTouchListener {

    private lateinit var editText : Drawable
    private lateinit var editTextError : Drawable
    var Error: Boolean = false

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                validateInput()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateInput()
            }

            override fun afterTextChanged(s: Editable?) {
                validateInput()
            }
        })
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if (Error) {
            editTextError
        } else {
            editText
        }
    }

    private fun init() {
        editText = ContextCompat.getDrawable(context, R.drawable.bg_text)!!
        editTextError = ContextCompat.getDrawable(context, R.drawable.bg_error)!!
    }
    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        return false
    }

    private fun validateInput() {
        val validationType = getValidationType()
        val inputText = text.toString()
        var errorMessage: String? = null

        Error = when (validationType) {
            validationType.Name -> {
                if (inputText.isEmpty()) {
                    errorMessage = context.getString(R.string.field_error_null)
                    true
                } else {
                    false
                }
            }
             validationType.Email -> {
                 if (inputText.isEmpty()) {
                     errorMessage = context.getString(R.string.field_error_null)
                     true
                 } else if (!Patterns.EMAIL_ADDRESS.matcher(inputText).matches()) {
                     errorMessage = context.getString(R.string.email_error_validation)
                     true
                 } else false
             }
             validationType.Password -> {
                 if (inputText.isEmpty()) {
                     errorMessage = context.getString(R.string.field_error_null)
                     true
                 } else if (!Patterns.EMAIL_ADDRESS.matcher(inputText).matches()) {
                     errorMessage = context.getString(R.string.email_error_validation)
                     true
                 } else {
                     false
                 }
             }
             else -> {
                 false
             }
        }

        setErrorIf(Error, errorMessage)
    }

    private fun setErrorIf(error: Boolean, errorMesage: String?) {
        if(error) {
            setError(errorMesage, null)
        }
    }
    private fun getValidationType(): ValidationType {
        return when (this.id) {
            R.id.nameEditText -> ValidationType.Name
            R.id.emailEditText -> ValidationType.Email
            R.id.passwordEditText -> ValidationType.Password

            else -> ValidationType.None
        }
    }
    private enum class ValidationType {
        None,
        Name,
        Email,
        Password
    }
}