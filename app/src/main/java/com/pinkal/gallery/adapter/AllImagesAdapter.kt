package com.pinkal.gallery.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.pinkal.gallery.R
import com.pinkal.gallery.activity.FullScreenImageActivity
import com.pinkal.gallery.model.Images
import com.pinkal.gallery.utils.CURRENT_IMAGE
import com.pinkal.gallery.utils.IMAGE
import com.pinkal.gallery.utils.IMAGES_LIST
import com.pinkal.gallery.views.ImageView.ImageViewSquare
import kotlinx.android.synthetic.main.images_item.view.*


/**
 * Created by Pinkal on 14/7/17.
 */
class AllImagesAdapter(val mContext: Context, val imagesList: ArrayList<Images>) :
        RecyclerView.Adapter<AllImagesAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (imagesList[position].filePath != null) {
            if (imagesList[position].isImageOrVideo == IMAGE) {

                holder.imgThumbnilPlayLogo.visibility = View.GONE

                Glide.with(mContext)
                        .load(Uri.parse("file://" + imagesList[position].filePath))
                        .centerCrop()
                        .into(holder.imgImages)

            } else {

                holder.imgThumbnilPlayLogo.visibility = View.VISIBLE

                Glide.with(mContext)
                        .load(Uri.parse("file://" + imagesList[position].filePath))
                        .centerCrop()
                        .into(holder.imgImages)

                Glide.with(mContext)
                        .load(R.mipmap.ic_play_logo)
                        .centerCrop()
                        .into(holder.imgThumbnilPlayLogo)
            }

        }

        Log.e("Adapter >> ", "" + imagesList[position].filePath)

        holder.imgImages.setOnClickListener({
            val intent = Intent(mContext, FullScreenImageActivity::class.java)
            val list: ArrayList<Images> = imagesList

            intent.putExtra(IMAGES_LIST, list)
            intent.putExtra(CURRENT_IMAGE, position)
            mContext.startActivity(intent)
        })

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {

        val itemView = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.images_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgImages: ImageViewSquare = itemView.imgImages
        val imgThumbnilPlayLogo: ImageView = itemView.imgThumbnilPlayLogo
    }
}
