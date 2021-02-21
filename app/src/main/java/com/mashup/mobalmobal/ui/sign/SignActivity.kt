package com.mashup.mobalmobal.ui.sign

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mashup.mobalmobal.databinding.ActivitySignBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}