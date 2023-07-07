package com.example.trackmysleepquality

import com.example.trackmysleepquality.databse.SleepNight

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import java.text.SimpleDateFormat

/**
 * These functions create a formatted string that can be set in a TextView.
 */

/**
 * Returns a string representing the numeric quality rating.
 */
fun convertNumericQualityToString(quality: Int, resources: Resources): String {
    var qualityString = resources.getString(R.string.three_ok)
    when (quality) {
        -1 -> qualityString = "--"
        0 -> qualityString = resources.getString(R.string.zero_very_bad)
        1 -> qualityString = resources.getString(R.string.one_poor)
        2 -> qualityString = resources.getString(R.string.two_soso)
        4 -> qualityString = resources.getString(R.string.four_pretty_good)
        5 -> qualityString = resources.getString(R.string.five_excellent)
    }
    return qualityString
}

@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm")
        .format(systemTime).toString()
}


fun formatNights(nights: List<SleepNight>, resources: Resources): Spanned {
    val sb = StringBuilder()
    sb.apply {
        append(resources.getString(R.string.title))
        nights.forEach {
            append("<br>")
            append(resources.getString(R.string.start_time))
            append("\t${it.startTimeMilli?.let { it1 -> convertLongToDateString(it1) }}<br>")
            if (it.endTimeMilli != it.startTimeMilli) {
                append(resources.getString(R.string.end_time))
                append("\t${it.endTimeMilli?.let { it1 -> convertLongToDateString(it1) }}<br>")
                append(resources.getString(R.string.quality))
                append("\t${it.sleepQuality?.let { it1 -> convertNumericQualityToString(it1, resources) }}<br>")
                append(resources.getString(R.string.hours_slept))
                // Hours
                append("\t ${it.endTimeMilli!!.minus(it.startTimeMilli!!) / 1000 / 60 / 60}:")
                // Minutes
                append("${it.endTimeMilli!!.minus(it.startTimeMilli) / 1000 / 60}:")
                // Seconds
                append("${it.endTimeMilli!!.minus(it.startTimeMilli) / 1000}<br><br>")
            }
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}