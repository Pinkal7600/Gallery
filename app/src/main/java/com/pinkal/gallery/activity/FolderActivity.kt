package com.pinkal.gallery.activity

import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.view.Window
import android.view.WindowManager
import com.pinkal.gallery.R
import com.pinkal.gallery.adapter.AllImagesAdapter
import com.pinkal.gallery.model.Images
import com.pinkal.gallery.utils.*
import kotlinx.android.synthetic.main.activity_folder.*
import java.util.*

/**
 * Created by Pinkal on 17/7/17.
 */
class FolderActivity : AppCompatActivity() {

    var imagesList: ArrayList<Images> = ArrayList<Images>()

    val mActivity = this@FolderActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_folder)

        val bundle: Bundle = intent.extras
        val folderName = bundle.getString(FOLDER_NAME)
        val folderPath = bundle.getString(FOLDER_PATH)
        val fileType = bundle.getString(FOLDER_IMAGE_OR_VIDEO)

        initialize(folderName, folderPath, fileType)
    }

    private fun initialize(folderName: String, folderPath: String, fileType: String) {

        setSupportActionBar(toolbarFolder)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        supportActionBar!!.title = folderName

        if (fileType == IMAGE) {
            getFolderImages(folderPath, folderName)
        } else {
            getFolderVideos(folderPath, folderName)
        }
    }

    private fun getFolderImages(folderPath: String, folderName: String) {

        imagesList.clear()

        val orderBy = MediaStore.Images.Media.DATE_TAKEN + " DESC"

        val cursor = this.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Images.Media.BUCKET_ID + " like ? AND " + MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " like ?",
                arrayOf("%$folderPath%", "%$folderName%"), orderBy)

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

        val mAdapter = AllImagesAdapter(mActivity, imagesList)

        val mLayoutManager = GridLayoutManager(mActivity, 2)
        recyclerViewFolder.layoutManager = mLayoutManager
        recyclerViewFolder.itemAnimator = DefaultItemAnimator()
        recyclerViewFolder.adapter = mAdapter
    }

    private fun getFolderVideos(folderPath: String, folderName: String) {
        imagesList.clear()

        val orderBy = MediaStore.Video.Media.DATE_TAKEN + " DESC"

        val cursor = this.contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null,
                MediaStore.Video.Media.BUCKET_ID + " like ? AND " + MediaStore.Video.Media.BUCKET_DISPLAY_NAME + " like ?",
                arrayOf("%$folderPath%", "%$folderName%"), orderBy)

        if (cursor.moveToFirst()) {
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            do {
                val path = cursor.getString(dataColumn)
                val images = Images()
                images.filePath = path
                images.isImageOrVideo = VIDEO
                imagesList.add(images)
            } while (cursor.moveToNext())
        }
        cursor.close()

        val mAdapter = AllImagesAdapter(mActivity, imagesList)

        val mLayoutManager = GridLayoutManager(mActivity, 2)
        recyclerViewFolder.layoutManager = mLayoutManager
        recyclerViewFolder.itemAnimator = DefaultItemAnimator()
        recyclerViewFolder.adapter = mAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}