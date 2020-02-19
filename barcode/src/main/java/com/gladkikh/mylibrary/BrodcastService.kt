package com.gladkikh.mylibrary

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.subjects.PublishSubject


class BrodcastService : Service() {

    companion object {
        val INTENT_KEY_KEY_BARCODE = "key_barcode"
        val INTENT_KEY_ACTION = "key_action"

    }

    var keyBarcode: String? = null
    var action :String? = null

    val binder = MyBinder()

    val dataPublishSubject = PublishSubject.create<String?>()



    private val mBarcodeDataBroadcastReceiver = BarcodeDataBroadcastReceiver(mResiveBarcode = object :
        BarcodeDataBroadcastReceiver.ReciveBarcode {
        override fun getKeyBarcode(): String {
            return keyBarcode?:""
        }

        override fun callBack(barcode: String?) {
            dataPublishSubject.onNext(barcode ?: "")

        }
    })




    override fun onBind(intent: Intent): IBinder {

        return binder
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        //val intentFilter = IntentFilter("DATA_SCAN")
        action = intent.getStringExtra(INTENT_KEY_ACTION)
        keyBarcode = intent.getStringExtra(INTENT_KEY_KEY_BARCODE)
        val intentFilter = IntentFilter(action)
        registerReceiver(mBarcodeDataBroadcastReceiver, intentFilter)


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Do not forget to unregister the receiver!!!
        this.unregisterReceiver(mBarcodeDataBroadcastReceiver)
    }


    inner class MyBinder() : Binder() {
        val servises = this@BrodcastService
    }
}
