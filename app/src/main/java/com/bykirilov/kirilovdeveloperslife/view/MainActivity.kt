package com.bykirilov.kirilovdeveloperslife.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bykirilov.kirilovdeveloperslife.R
import com.bykirilov.kirilovdeveloperslife.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment = PostFragment.newInstance("post_fragment")
            transaction
                .replace(
                    R.id.fragment_container,
                    fragment,
                    "post_fragment_tag"
                )
                .commit()
        }
    }
}