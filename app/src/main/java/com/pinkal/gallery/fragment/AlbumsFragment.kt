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
import com.pinkal.gallery.utils.VIDEO
import kotlinx.android.synthetic.main.fragment_albums.view.*


/**
 * Created by Pinkal on 13/7/17.
 */

class AlbumsFragment : Fragment() {

    var albumsFolderList: ArrayList<AlbumsFolder> = ArrayList<AlbumsFolder>()

    lateinit var mAdapter: AlbumsFolderAdapter
    lateinit var recyclerViewAlbums: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_albums, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize(view)
    }

    private fun initialize(view: View) {
        recyclerViewAlbums = view.recyclerViewAlbums
    }

    override fun onResume() {
        super.onResume()
        getGalleryFolderNames()
    }

    fun getGalleryFolderNames() {

        val imageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val imageProjection = arrayOf("DISTINCT " + MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_ID)

        val videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val videoProjection = arrayOf("DISTINCT " + MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.BUCKET_ID)

        val imageOrderBy = MediaStore.Images.Media.DATE_MODIFIED + " ASC" //ASC DESC
        val videoOrderBy = MediaStore.Video.Media.DATE_MODIFIED + " ASC" //ASC DESC

        val imageCursor = activity.contentResolver.query(imageUri, imageProjection, null, null, imageOrderBy)
        val videoCursor = activity.contentResolver.query(videoUri, videoProjection, null, null, videoOrderBy)

        albumsFolderList.clear()

        if (imageCursor != null) {
            while (imageCursor.moveToNext()) {

                val folderName = imageCursor.getString(0)
                val folderPath = imageCursor.getString(1)

                Log.e("Albums", "String 0 : " + imageCursor.getString(0))
                Log.e("Albums", "String 1 : " + imageCursor.getString(1))


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
        imageCursor.close()

        if (videoCursor != null) {
            while (videoCursor.moveToNext()) {

                val folderName = videoCursor.getString(0)
                val folderPath = videoCursor.getString(1)

                Log.e("Albums", "String 0 : " + videoCursor.getString(0))
                Log.e("Albums", "String 1 : " + videoCursor.getString(1))

                val videoPathOrderBy = MediaStore.Video.Media.DATE_TAKEN + " DESC"
                val videoPathCursor = activity.contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        null,
                        MediaStore.Video.Media.BUCKET_ID + " like ? AND " + MediaStore.Video.Media.BUCKET_DISPLAY_NAME + " like ?",
                        arrayOf("%$folderPath%", "%$folderName%"),
                        videoPathOrderBy)

                var videoFilePath = ""
                var totalVideos = 0
                var videoFileList: ArrayList<FileList> = ArrayList()

                if (videoPathCursor.moveToFirst()) {
                    do {
                        val dataColumn = videoPathCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
                        videoFilePath = videoPathCursor.getString(dataColumn)

                        totalVideos += 1

                        val fileList = FileList(videoFilePath)

                        videoFileList.add(fileList)

                    } while (videoPathCursor.moveToNext())
                }
                videoPathCursor.close()

                val folderModel = AlbumsFolder(folderName, folderPath, VIDEO,
                        totalVideos.toString(), videoFileList)

                albumsFolderList.add(folderModel)

            }

        }
        videoCursor.close()

        mAdapter = AlbumsFolderAdapter(activity, albumsFolderList)
        val mLayoutManager = GridLayoutManager(activity, 2)
        recyclerViewAlbums.layoutManager = mLayoutManager
        recyclerViewAlbums.itemAnimator = DefaultItemAnimator()
        recyclerViewAlbums.adapter = mAdapter


    }

}

//
//        // Get relevant columns for use later.
//        val projection = arrayOf(
//                MediaStore.Files.FileColumns._ID,
//                MediaStore.Files.FileColumns.DATA,
//                MediaStore.Files.FileColumns.DATE_ADDED,
//                MediaStore.Files.FileColumns.MEDIA_TYPE,
//                MediaStore.Files.FileColumns.MIME_TYPE,
//                MediaStore.Files.FileColumns.TITLE)
//
//        // Return only video and image metadata.
//        val selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "=" +
//                MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE + " OR " +
//                MediaStore.Files.FileColumns.MEDIA_TYPE + "=" +
//                MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
//
//        val queryUri = MediaStore.Files.getContentUri("external")
//
//        val cursorLoader = CursorLoader(
//                context,
//                queryUri,
//                projection,
//                selection,
//                null, // Selection args (none).
//                MediaStore.Files.FileColumns.DATE_ADDED + " ASC" // Sort order.
//        )
//
//        val cursor = cursorLoader.loadInBackground()
