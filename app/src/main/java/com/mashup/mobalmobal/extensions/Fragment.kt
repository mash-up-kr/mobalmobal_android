package com.mashup.mobalmobal.extensions

import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mashup.base.extensions.show
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.LayoutBottomSheetDonateBinding

fun Fragment.showChargeBottomSheet(
    title: String,
    onPriceClick: (Int) -> Unit,
    onDirectClick: () -> Unit
) {
    val binding = LayoutBottomSheetDonateBinding.inflate(layoutInflater)

    BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme).show {
        setContentView(binding.root)
        binding.bottomSheetTitle.text = title
        binding.bottomSheetCharge1000.setOnClickListener {
            onPriceClick(1000)
            dismiss()
        }
        binding.bottomSheetCharge2000.setOnClickListener {
            onPriceClick(2000)
            dismiss()
        }
        binding.bottomSheetCharge5000.setOnClickListener {
            onPriceClick(5000)
            dismiss()
        }
        binding.bottomSheetCharge10000.setOnClickListener {
            onPriceClick(10000)
            dismiss()
        }
        binding.bottomSheetCharge50000.setOnClickListener {
            onPriceClick(50000)
            dismiss()
        }
        binding.bottomSheetCharge100000.setOnClickListener {
            onPriceClick(100000)
            dismiss()
        }
        binding.bottomSheetChargeWriting.setOnClickListener {
            onDirectClick()
            dismiss()
        }
    }
}

