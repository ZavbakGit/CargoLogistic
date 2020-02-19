package com.gladkikh.mylibrary

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Alex on 24.05.2017.
 */

class BarcodeDataBroadcastReceiver(val mResiveBarcode: ReciveBarcode) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val barcode = intent.getStringExtra(mResiveBarcode.getKeyBarcode())
        mResiveBarcode.callBack(barcode)
    }

    interface ReciveBarcode {
        fun callBack(barcode: String?)
        fun getKeyBarcode():String
    }


}
