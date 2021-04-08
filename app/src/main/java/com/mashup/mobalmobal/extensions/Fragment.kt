package com.mashup.mobalmobal.extensions

import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mashup.base.extensions.show
import com.mashup.mobalmobal.R
import com.mashup.mobalmobal.databinding.LayoutBottomSheetDonateBinding

fun Fragment.showChargeBottomSheet(
    title: String,
    onPriceClick: ((Int) -> Boolean)? = null,
    onDirectClick: (() -> Boolean)? = null
) {
    val binding = LayoutBottomSheetDonateBinding.inflate(layoutInflater)

    BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme).show {
        setContentView(binding.root)
        binding.bottomSheetTitle.text = title
        binding.bottomSheetCharge1000.setOnClickListener {
            if (onPriceClick?.invoke(1000) == true) {
                dismiss()
            }
        }
        binding.bottomSheetCharge2000.setOnClickListener {
            if (onPriceClick?.invoke(2000) == true) {
                dismiss()
            }
        }
        binding.bottomSheetCharge5000.setOnClickListener {
            if (onPriceClick?.invoke(5000) == true) {
                dismiss()
            }
        }
        binding.bottomSheetCharge10000.setOnClickListener {
            if (onPriceClick?.invoke(10000) == true) {
                dismiss()
            }
        }
        binding.bottomSheetCharge50000.setOnClickListener {
            if (onPriceClick?.invoke(50000) == true) {
                dismiss()
            }
        }
        binding.bottomSheetCharge100000.setOnClickListener {
            if (onPriceClick?.invoke(100000) == true) {
                dismiss()
            }
        }
        binding.bottomSheetChargeWriting.setOnClickListener {
            if (onDirectClick?.invoke() == true) {
                dismiss()
            }
        }
    }
}

