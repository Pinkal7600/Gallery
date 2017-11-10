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
import com.pinkal.gallery.utils.VIDEO
import kotlinx.android.synthetic.main.fragment_video.view.*
import java.util.*

/**
 * Created by Pinkal on 13/7/17.
 */

class VideoFragment : Fragment() {

    var albumsFolderList: ArrayList<AlbumsFolder> = ArrayList<AlbumsFolder>()

    lateinit var mAdapter: AlbumsFolderAdapter

    lateinit var recyclerViewVideo: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_video, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewVideo = view.recyclerViewVideo
    }

    override fun onResume() {
        super.onResume()
        getGalleryFolderNames()
    }

    fun getGalleryFolderNames() {

        val videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val videoProjection = arrayOf("DISTINCT " + MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.BUCKET_ID)



        for (i in videoProjection) {
            Log.e("Albums Video", i + "")
        }

        val orderBy = MediaStore.Images.Media.DATE_MODIFIED + " ASC" //ASC DESC

        val videoCursor = activity.contentResolver.query(videoUri, videoProjection, null, null, orderBy)

        albumsFolderList.clear()

        if (videoCursor != null) {
            while (videoCursor.moveToNext()) {

                val folderName = videoCursor.getString(0)
                val folderPath = videoCursor.getString(1)

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

                val folderModel = AlbumsFolder(folderName, folderPath, VIDEO, totalVideos.toString(), videoFileList)

                albumsFolderList.add(folderModel)

            }

        }

        mAdapter = AlbumsFolderAdapter(activity, albumsFolderList)
        val mLayoutManager = GridLayoutManager(activity, 2)
        recyclerViewVideo.layoutManager = mLayoutManager
        recyclerViewVideo.itemAnimator = DefaultItemAnimator()
        recyclerViewVideo.adapter = mAdapter


    }


}