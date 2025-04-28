package com.semenovdev.addendtrainer.presentation

import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.semenovdev.addendtrainer.R

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.min_quantity),
        count.toString()
    )
}

@BindingAdapter("yourScore")
fun bindYourScore(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.your_score),
        count.toString()
    )
}

@BindingAdapter("requiredPercent")
fun bindRequiredPercent(textView: TextView, percent: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.min_percentage),
        percent.toString()
    )
}

@BindingAdapter("yourPercentage")
fun bindYourPercentage(textView: TextView, percent: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.percentage),
        percent.toString()
    )
}

@BindingAdapter("resultEmoji")
fun bindResultEmoji(imageView: ImageView, isSuccess: Boolean) {
    var imageSource = if (isSuccess) {
        R.drawable.ic_glad
    } else {
        R.drawable.ic_sad
    }
    imageView.setImageResource(imageSource)
}

@BindingAdapter("numberToString")
fun bindNumberToString (textView: TextView, num: Int ){
    textView.text = num.toString()
}

@BindingAdapter("progressBarColor")
fun bindProgressBarColors(progressBar: ProgressBar, isEnough: Boolean) {
    val color = getSuccessColors(progressBar, isEnough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("textSuccessColor")
fun bindTextSuccessColor(textView: TextView, isSuccess: Boolean) {
    val color = getSuccessColors(textView, isSuccess)
    textView.setTextColor(color)
}

fun getSuccessColors (view: View, isSuccess: Boolean): Int {
    val colorRes = if (isSuccess) {
        R.color.green_100
    } else {
        R.color.red
    }
    return view.context.getColor(colorRes)
}