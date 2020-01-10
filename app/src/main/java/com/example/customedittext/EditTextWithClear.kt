package com.example.customedittext

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat


/**
 * @context necessario para criar uma instancia de View Programaticamene
 * @attrs  necessario para inflar e aplicar atributos numa view de um layout XML
 * @defStyleAttr necessario para aplicar um estilo padrao a todos elementos da UI sem ter que
 * especificar em cada Layout file.
 */
class EditTextWithClear @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    private var clearButtonImage =
        ResourcesCompat.getDrawable(resources, R.drawable.ic_clear_opaque_24dp, null)

    init {

        // if clear(X) is clicked, clear text
        // if the text changes show/hide the clear button.

        setOnTouchListener { _, event ->

            if (compoundDrawablesRelative[2] != null) {
                val clearButtonStart: Int // Used for LTR languages
                val clearButtonEnd: Int // Used for RTL languages
                var isClearButtonClicked = false
                // : Detect the touch in RTL or LTR layout direction.
                if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                    clearButtonEnd = clearButtonImage!!.intrinsicWidth + paddingStart

                    if (event.x < clearButtonEnd) {
                        isClearButtonClicked = true
                    }
                } else {
                    // Layout is LTR
                    clearButtonStart = (width - paddingEnd - clearButtonImage!!.intrinsicWidth)
                    if (event.x > clearButtonStart) {
                        isClearButtonClicked = true
                    }
                }

                // : Check for actions if the button is tapped.
                if (isClearButtonClicked) {
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        // Switch to black version of button
                        clearButtonImage = ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_clear_black_24dp,
                            null
                        )
                        showClearButton()
                    }
                    //CHeck Action Up
                    if (event.action == MotionEvent.ACTION_UP) {
                        // Switch to opaque version
                        clearButtonImage = ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.ic_clear_opaque_24dp,
                            null
                        )
                        // clear the text and hide the clear button
                        text?.clear()
                        hideClearButton()
                        return@setOnTouchListener true
                    }
                } else {
                    return@setOnTouchListener false
                }

            }
            false
        }


    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        showClearButton()
    }

    private fun showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, clearButtonImage, null)
    }

    private fun hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
    }
}