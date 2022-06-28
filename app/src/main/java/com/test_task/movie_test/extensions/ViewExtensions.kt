package com.test_task.movie_test.extensions

import android.R
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

fun View.isVisible(): Boolean = this.visibility == View.VISIBLE

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.hideKeyboard() {
    requestFocus()
    val inputManager =
            this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputManager?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun EditText.showSoftKeyboard(){
    (this.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
        .showSoftInput(this, InputMethodManager.SHOW_FORCED)
}

fun View.showKeyBoard() {
    requestFocus()
    val inputManager =
            this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputManager?.showSoftInput(this, 0)
}

class SimpleDividerItemDecoration(context: Context, @DrawableRes dividerRes: Int) : RecyclerView.ItemDecoration() {

    private val mDivider: Drawable = ContextCompat.getDrawable(context, dividerRes)!!

    override fun onDrawOver(c: Canvas, parent: RecyclerView) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top: Int = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            mDivider.draw(c)
        }
    }


}

fun makeSelector(color: Int): StateListDrawable? {
    val res = StateListDrawable()
    res.setExitFadeDuration(400)
   // res.alpha = 45
    res.addState(intArrayOf(R.attr.state_pressed), ColorDrawable(color))
    res.addState(intArrayOf(), ColorDrawable(color))
    return res
}
/**
 * формирует дравейбл
 */
fun createShape(colorString: String): Drawable {
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.RECTANGLE
    shape.setTintAuto(Color.parseColor(colorString),
        manipulateColor(Color.parseColor(colorString), 0.6f))
    return shape
}

fun createRoundShape(colorString: String): Drawable {
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.OVAL
    shape.setTintAuto(Color.parseColor(colorString),
        manipulateColor(Color.parseColor(colorString), 0.6f))
    return shape
}

/**
 * формирует разные цветовые заливки в зависимости от состояния объекта
 */
fun GradientDrawable.setTintAuto(
    enabledColor: Int,
    disabledColor: Int
) {

    val colorTint = ColorStateList(
        arrayOf(
            intArrayOf(-R.attr.state_checked),
            intArrayOf(R.attr.state_checked)
        ), intArrayOf(enabledColor, disabledColor)
    )
    this.color = colorTint

}

/**
 * делает цвет темнее
 */

fun manipulateColor(color: Int, factor: Float): Int {
    val a = Color.alpha(color)
    val r = Math.round(Color.red(color) * factor)
    val g = Math.round(Color.green(color) * factor)
    val b = Math.round(Color.blue(color) * factor)
    return Color.argb(
        a,
        Math.min(r, 255),
        Math.min(g, 255),
        Math.min(b, 255)
    )
}

interface SetColorScheme {

    fun setThemeColor(colorString: String)
}