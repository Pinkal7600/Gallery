package com.pinkal.gallery.fragment

import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pinkal.gallery.R
import com.pinkal.gallery.adapter.AllImagesAdapter
import com.pinkal.gallery.model.Images
import com.pinkal.gallery.utils.IMAGE
import kotlinx.android.synthetic.main.fragment_camera_roll.view.*
import java.util.*


/**
 * Created by Pinkal on 13/7/17.
 */

class CameraRollFragment : Fragment() {

    var imagesList: ArrayList<Images> = ArrayList<Images>()
    lateinit var recyclerViewCameraRoll: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_camera_roll, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize(view)
    }

    private fun initialize(view: View) {
        recyclerViewCameraRoll = view.recyclerViewCameraRoll

    }

    override fun onResume() {
        super.onResume()
        featchList()
    }

    private fun featchList() {
        val CAMERA_IMAGE_BUCKET_NAME = Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera"
        val CAMERA_IMAGE_BUCKET_ID = getBucketId(CAMERA_IMAGE_BUCKET_NAME)

        val selection = MediaStore.Images.Media.BUCKET_ID + " = ?"
        val selectionArgs = arrayOf(CAMERA_IMAGE_BUCKET_ID)
        val orderBy = MediaStore.Images.Media.DATE_TAKEN + " DESC"

        val cursor = context.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                selection,
                selectionArgs,
                orderBy)

        imagesList.clear()

        if (cursor.moveToFirst()) {
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            do {
                val path = cursor.getString(dataColumn)
                val images = Images()
                images.filePath = path
                images.isImageOrVideo = IMAGE
                imagesList.add(images)
            } while (cursor.moveToNext())
        }
        cursor.close()

        val mAdapter = AllImagesAdapter(activity, imagesList)

        val mLayoutManager = GridLayoutManager(activity, 2)
        recyclerViewCameraRoll.layoutManager = mLayoutManager
        recyclerViewCameraRoll.itemAnimator = DefaultItemAnimator()
        recyclerViewCameraRoll.adapter = mAdapter
    }

    /**
     * Matches code in MediaProvider.computeBucketValues. Should be a common
     * function.
     */
    fun getBucketId(path: String): String {
        return path.toLowerCase().hashCode().toString()
    }
}
