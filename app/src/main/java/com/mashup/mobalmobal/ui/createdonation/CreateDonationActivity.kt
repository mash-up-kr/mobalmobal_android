package com.mashup.mobalmobal.ui.createdonation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mashup.mobalmobal.databinding.ActivityCreateDonationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateDonationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateDonationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateDonationBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}