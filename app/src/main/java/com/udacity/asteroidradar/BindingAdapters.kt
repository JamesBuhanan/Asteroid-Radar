package com.udacity.asteroidradar

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.main.AsteroidAdapter
import com.udacity.asteroidradar.main.AsteroidApiStatus

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Picasso.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("statusDescription")
fun ImageView.statusDescription(potentiallyHazardous: Boolean) {
    contentDescription = if (potentiallyHazardous) {
        resources.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        resources.getString(R.string.not_hazardous_asteroid_image)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, asteroids: List<Asteroid>?) {
    asteroids?.let {
        val adapter = recyclerView.adapter as AsteroidAdapter
        adapter.submitList(asteroids)
    }
}

@BindingAdapter("loading")
fun ProgressBar.loading(asteroidApiStatus: AsteroidApiStatus?) {
    visibility = when(asteroidApiStatus) {
        AsteroidApiStatus.LOADING -> VISIBLE
        AsteroidApiStatus.ERROR -> GONE
        AsteroidApiStatus.DONE -> GONE
        null -> GONE
    }
}