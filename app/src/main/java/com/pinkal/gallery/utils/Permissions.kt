package com.pinkal.gallery.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import org.jetbrains.anko.toast

/**
 * Created by pinkal on 18/7/17.
 */

class Permissions {

    //Check for location permission
    fun isLocationPermissionGranted(mActivity: Activity): Boolean {

        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            mActivity.toast("Please allow location permission.")

            ActivityCompat.requestPermissions(mActivity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_FINE_LOCATION_PERMISSION)


            return false

        } else {
            return true
        }
    }

    //Check for read storage permission
    fun isReadStoragePermissionGranted(mActivity: Activity): Boolean {

        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

//            mActivity.toast("Please allow storage permission.")

            ActivityCompat.requestPermissions(mActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_READ_STORAGE_PERMISSION)
            return false

        } else {
            return true
        }
    }

    //Check for write storage permission
    fun isWriteStoragePermissionGranted(mActivity: Activity): Boolean {

        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            mActivity.toast("Please allow storage permission.")

            ActivityCompat.requestPermissions(mActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_WRITE_STORAGE_PERMISSION)


            return false

        } else {
            return true
        }
    }

    //Check for read contacts permission
    fun isReadContactsPermissionGranted(mActivity: Activity): Boolean {

        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            mActivity.toast("Please allow contact permission.")

            ActivityCompat.requestPermissions(mActivity,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    REQUEST_READ_CONTACTS_PERMISSION)
            return false

        } else {
            return true
        }
    }

    //Check for write contacts permission
    fun isWriteContactsPermissionGranted(mActivity: Activity): Boolean {

        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            mActivity.toast("Please allow contact permission.")

            ActivityCompat.requestPermissions(mActivity,
                    arrayOf(Manifest.permission.WRITE_CONTACTS),
                    REQUEST_WRITE_CONTACTS_PERMISSION)
            return false

        } else {
            return true
        }
    }

    //Check for camera permission
    fun isCameraPermissionGranted(mActivity: Activity): Boolean {

        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            mActivity.toast("Please allow camera permission.")

            ActivityCompat.requestPermissions(mActivity,
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CAMERA_PERMISSION)
            return false

        } else {
            return true
        }
    }

    //Check for read phone state permission
    fun isReadPhoneStatePermissionGranted(mActivity: Activity): Boolean {

        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            mActivity.toast("Please allow read phone state permission.")

            ActivityCompat.requestPermissions(mActivity,
                    arrayOf(Manifest.permission.READ_PHONE_STATE),
                    REQUEST_READ_PHONE_STATE_PERMISSION)
            return false

        } else {
            return true
        }
    }

    //Check for call phone permission
    fun isCallPhonePermissionGranted(mActivity: Activity): Boolean {

        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            mActivity.toast("Please allow call permission.")

            ActivityCompat.requestPermissions(mActivity,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    REQUEST_CALL_PHONE_PERMISSION)
            return false

        } else {
            return true
        }
    }

    //Check for read sms permission
    fun isReadSmsPermissionGranted(mActivity: Activity): Boolean {

        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {

            mActivity.toast("Please allow sms permission.")

            ActivityCompat.requestPermissions(mActivity,
                    arrayOf(Manifest.permission.READ_SMS),
                    REQUEST_READ_SMS_PERMISSION)
            return false

        } else {
            return true
        }
    }

    //Check for send sms permission
    fun isSendSmsPermissionGranted(mActivity: Activity): Boolean {

        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            mActivity.toast("Please allow sms permission.")

            ActivityCompat.requestPermissions(mActivity,
                    arrayOf(Manifest.permission.SEND_SMS),
                    REQUEST_SEND_SMS_PERMISSION)
            return false

        } else {
            return true
        }
    }

    //Check for receive sms permission
    fun isReceiveSmsPermissionGranted(mActivity: Activity): Boolean {

        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {

            mActivity.toast("Please allow sms permission.")

            ActivityCompat.requestPermissions(mActivity,
                    arrayOf(Manifest.permission.RECEIVE_SMS),
                    REQUEST_RECEIVE_SMS_PERMISSION)
            return false

        } else {
            return true
        }
    }

    //Check for write settings permission
    fun isWriteSettingsPermissionGranted(mActivity: Activity): Boolean {

        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED) {

            mActivity.toast("Please allow write settings permission.")

            ActivityCompat.requestPermissions(mActivity,
                    arrayOf(Manifest.permission.WRITE_SETTINGS),
                    REQUEST_WRITE_SETTINGS)
            return false

        } else {
            return true
        }
    }

    //Check for record audio permission
    fun isRecordAudioPermissionGranted(mActivity: Activity): Boolean {

        if (ContextCompat.checkSelfPermission(mActivity,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            mActivity.toast("Please allow record audio permission.")

            ActivityCompat.requestPermissions(mActivity,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    REQUEST_RECORD_AUDIO)
            return false

        } else {
            return true
        }
    }

    companion object {

        private var mPermissions: Permissions? = null

        val REQUEST_FINE_LOCATION_PERMISSION = 1
        val REQUEST_READ_STORAGE_PERMISSION = 2
        val REQUEST_WRITE_STORAGE_PERMISSION = 3
        val REQUEST_READ_CONTACTS_PERMISSION = 4
        val REQUEST_WRITE_CONTACTS_PERMISSION = 5
        val REQUEST_CAMERA_PERMISSION = 6
        val REQUEST_READ_PHONE_STATE_PERMISSION = 7
        val REQUEST_CALL_PHONE_PERMISSION = 8
        val REQUEST_READ_SMS_PERMISSION = 9
        val REQUEST_SEND_SMS_PERMISSION = 10
        val REQUEST_RECEIVE_SMS_PERMISSION = 11
        val REQUEST_WRITE_SETTINGS = 12
        val REQUEST_RECORD_AUDIO = 13

        //Get Single Instance of CommonUtils Class
        val instance: Permissions
            get() {
                if (mPermissions == null) {
                    mPermissions = Permissions()
                }
                return mPermissions as Permissions
            }
    }

}


