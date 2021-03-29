package com.mashup.mobalmobal.extensions

import android.content.Context
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mashup.mobalmobal.R

fun Fragment.showChargeBottomSheet(
    context: Context,
) {
    BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme).also {
        it.setContentView(R.layout.bottom_sheet_donate)
        it.show()
        it.findViewById<LinearLayout>(R.id.bottom_sheet_charge_1000)?.setOnClickListener {
            // Do something
        }
        it.findViewById<LinearLayout>(R.id.bottom_sheet_charge_2000)?.setOnClickListener {
        }
        it.findViewById<LinearLayout>(R.id.bottom_sheet_charge_5000)?.setOnClickListener {
        }
        it.findViewById<LinearLayout>(R.id.bottom_sheet_charge_10000)?.setOnClickListener {
        }
        it.findViewById<LinearLayout>(R.id.bottom_sheet_charge_50000)?.setOnClickListener {
        }
        it.findViewById<LinearLayout>(R.id.bottom_sheet_charge_100000)?.setOnClickListener {
        }
        it.findViewById<LinearLayout>(R.id.bottom_sheet_charge_writing)?.setOnClickListener {
        }
    }
}