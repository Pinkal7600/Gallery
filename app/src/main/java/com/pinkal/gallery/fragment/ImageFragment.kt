package com.pinkal.gallery.fragment

import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pinkal.gallery.R
import com.pinkal.gallery.adapter.AlbumsFolderAdapter
import com.pinkal.gallery.model.AlbumsFolder
import com.pinkal.gallery.model.FileList
import com.pinkal.gallery.utils.IMAGE
import kotlinx.android.synthetic.main.fragment_images.view.*
import java.util.*

/**
 * Created by Pinkal on 13/7/17.
 */

class ImageFragment : Fragment() {

    var albumsFolderList: ArrayList<AlbumsFolder> = ArrayList<AlbumsFolder>()

    lateinit var mAdapter: AlbumsFolderAdapter

    lateinit var recyclerViewImages: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_images, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewImages = view.recyclerViewImages
    }

    override fun onResume() {
        super.onResume()
        getGalleryFolderNames()
    }
    
    fun getGalleryFolderNames() {

        val imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val imageProjection = arrayOf("DISTINCT " + MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_ID)


        for (i in imageProjection) {
            Log.e("Albums Image", i + "")
        }

        val orderBy = MediaStore.Images.Media.DATE_MODIFIED + " ASC" //ASC DESC

        val imageCursor = activity.contentResolver.query(imageUri, imageProjection, null, null, orderBy)

        albumsFolderList.clear()

        if (imageCursor != null) {
            while (imageCursor.moveToNext()) {

                val folderName = imageCursor.getString(0)
                val folderPath = imageCursor.getString(1)

                val imagePathOrderBy = MediaStore.Images.Media.DATE_TAKEN + " DESC"
                val imagePathCursor = activity.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        null,
                        MediaStore.Images.Media.BUCKET_ID + " like ? AND " + MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " like ?",
                        arrayOf("%$folderPath%", "%$folderName%"),
                        imagePathOrderBy)

                var imageFilePath = ""
                var totalImages = 0
                var imageFileList: ArrayList<FileList> = ArrayList()

                if (imagePathCursor.moveToFirst()) {
                    do {
                        val dataColumn = imagePathCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                        imageFilePath = imagePathCursor.getString(dataColumn)

                        totalImages += 1

                        val fileList = FileList(imageFilePath)

                        imageFileList.add(fileList)

                    } while (imagePathCursor.moveToNext())
                }
                imagePathCursor.close()

                val folderModel = AlbumsFolder(folderName, folderPath, IMAGE, totalImages.toString(), imageFileList)

                albumsFolderList.add(folderModel)

            }

        }

        mAdapter = AlbumsFolderAdapter(activity, albumsFolderList)
        val mLayoutManager = GridLayoutManager(activity, 2)
        recyclerViewImages.layoutManager = mLayoutManager
        recyclerViewImages.itemAnimator = DefaultItemAnimator()
        recyclerViewImages.adapter = mAdapter
    }
}