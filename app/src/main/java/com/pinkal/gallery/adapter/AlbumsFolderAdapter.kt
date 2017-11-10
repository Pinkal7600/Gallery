package com.pinkal.gallery.adapter

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.pinkal.gallery.R
import com.pinkal.gallery.activity.FolderActivity
import com.pinkal.gallery.model.AlbumsFolder
import com.pinkal.gallery.utils.FOLDER_IMAGE_OR_VIDEO
import com.pinkal.gallery.utils.FOLDER_NAME
import com.pinkal.gallery.utils.FOLDER_PATH
import com.pinkal.gallery.utils.IMAGE
import kotlinx.android.synthetic.main.albums_folder_item.view.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast


/**
 * Created by Pinkal Daliya on 13-Jul-17.
 */
class AlbumsFolderAdapter(val mContext: Context, val albumsFolderList: ArrayList<AlbumsFolder>) :
        RecyclerView.Adapter<AlbumsFolderAdapter.ViewHolder>() {

    var isFolderSelected = false

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val albumsFolder = albumsFolderList[position]

        val imagePath = albumsFolder.fileList[0].filePath
        val fileType = albumsFolder.fileType
        val fileSize = albumsFolder.fileSize

        if (imagePath != "") {
            if (fileType == IMAGE) {

                Glide.with(mContext)
                        .load(Uri.parse("file://" + imagePath))
                        .centerCrop()
                        .into(holder.imgAlbumsFolder)
                holder.imgPlayLogo.visibility = View.GONE

            } else {

                Glide.with(mContext)
                        .load(R.mipmap.ic_play_logo)
                        .centerCrop()
                        .into(holder.imgPlayLogo)

                holder.imgPlayLogo.visibility = View.VISIBLE

                Glide.with(mContext)
                        .load(Uri.parse("file://" + imagePath))
                        .centerCrop()
                        .into(holder.imgAlbumsFolder)
            }
        }


        holder.txtAlbumsFolderName.text = albumsFolder.folderName

        val subText = fileType + " " + fileSize
        if (subText != "") {
            holder.txtAlbumsTotal.text = subText
        }

        holder.relativeLayoutAlbums.setOnClickListener({
            if (isFolderSelected) {
                if (!albumsFolder.isSelected) {
                    albumsFolder.isSelected = true
                    holder.rlSelected.visibility = View.VISIBLE
                } else {
                    albumsFolder.isSelected = false
                    holder.rlSelected.visibility = View.GONE
                }
            } else {
                mContext.startActivity<FolderActivity>(FOLDER_NAME to albumsFolder.folderName,
                        FOLDER_PATH to albumsFolder.folderPath,
                        FOLDER_IMAGE_OR_VIDEO to albumsFolder.fileType)
            }
        })


        holder.relativeLayoutAlbums.setOnLongClickListener(View.OnLongClickListener {

            if (!albumsFolder.isSelected) {
                albumsFolder.isSelected = true
                holder.rlSelected.visibility = View.VISIBLE
                isFolderSelected = true
            } else {
                albumsFolder.isSelected = false
                holder.rlSelected.visibility = View.GONE
            }

            mContext.toast("long")
            return@OnLongClickListener true
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.albums_folder_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return albumsFolderList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imgAlbumsFolder: ImageView = itemView.imgAlbumsFolder
        val imgPlayLogo: ImageView = itemView.imgFolderPlayLogo

        val txtAlbumsFolderName: TextView = itemView.txtAlbumsFolderName
        val txtAlbumsTotal: TextView = itemView.txtAlbumsTotal
        val relativeLayoutAlbums: RelativeLayout = itemView.relativeLayoutAlbums
        val rlSelected: RelativeLayout = itemView.rlSelected

    }

//    private fun getImagePath(folderName: String, imageORVideo: String, folderPath: String): String {
//
//        var path: String? = null
//
//        if (imageORVideo == IMAGE) {
//
//            val OrderBy = MediaStore.Images.Media.DATE_TAKEN + " DESC"
//            val cursor = mContext.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                    null,
//                    MediaStore.Images.Media.BUCKET_ID + " like ? AND " + MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " like ?",
//                    arrayOf("%$folderPath%", "%$folderName%"),
//                    OrderBy)
//
//
//            if (cursor.moveToFirst()) {
//                val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
//                path = cursor.getString(dataColumn)
//                cursor.close()
//                return path
//            }
//
//        } else {
//
//            val OrderBy = MediaStore.Video.Media.DATE_TAKEN + " DESC"
//            val cursor = mContext.contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                    null,
//                    MediaStore.Images.Media.BUCKET_ID + " like ? AND " + MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " like ?",
//                    arrayOf("%$folderPath%", "%$folderName%"),
//                    OrderBy)
//
//
//            if (cursor.moveToFirst()) {
//                val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
//                path = cursor.getString(dataColumn)
//                cursor.close()
//                return path
//            }
//        }
//
//        return ""
//    }
//
//    private fun getAlbumsSubText(folderName: String, imageORVideo: String, folderPath: String): String {
//
//        if (imageORVideo == IMAGE) {
//
//            var totalImage: Int = 0
//
//            val OrderBy = MediaStore.Images.Media.DATE_TAKEN + " DESC"
//            val cursor = mContext.contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                    null,
//                    MediaStore.Images.Media.BUCKET_ID + " like ? AND " + MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " like ?",
//                    arrayOf("%$folderPath%", "%$folderName%"),
//                    OrderBy)
//
//
//            if (cursor.moveToFirst()) {
//                do {
//                    totalImage += 1
//                } while (cursor.moveToNext())
//            }
//
//            if (totalImage != 0) {
//                return "Photos " + totalImage
//            }
//
//        } else {
//
//            var totalVideo: Int = 0
//
//            val OrderBy = MediaStore.Video.Media.DATE_TAKEN + " DESC"
//            val cursor = mContext.contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
//                    null,
//                    MediaStore.Images.Media.BUCKET_ID + " like ? AND " + MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " like ?",
//                    arrayOf("%$folderPath%", "%$folderName%"),
//                    OrderBy)
//
//
//            if (cursor.moveToFirst()) {
//                do {
//                    totalVideo += 1
//                } while (cursor.moveToNext())
//            }
//
//            if (totalVideo != 0) {
//                return "Videos " + totalVideo
//            }
//        }
//
//        return ""
//    }

}
