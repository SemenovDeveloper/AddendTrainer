package com.semenovdev.addendtrainer.presentation

import android.widget.ImageView
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