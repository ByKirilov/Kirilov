package com.bykirilov.kirilovdeveloperslife.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Post(

        @SerializedName("description")
        val description: String,

        @SerializedName("gifURL")
        val gifURL: String,
) : Serializable
