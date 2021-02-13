package com.mashup.mobalmobal.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.funin.base.funinbase.extension.showToast
import com.mashup.mobalmobal.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showToast("asdfasdf")
    }
}