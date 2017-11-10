package com.pinkal.gallery.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.pinkal.gallery.R
import com.pinkal.gallery.model.Images
import com.pinkal.gallery.utils.IMAGE
import com.pinkal.gallery.views.ImageView.imagezoom.ImageViewTouch


/**
 * Created by Pinkal on 14/7/17.
 */

class FullImageViewPagerAdapter(var mContext: Context, var imagesArrayList: ArrayList<Images>,
                                var touchViewPager: TouchViewPager, var menuOption: MenuOption,
                                var onShowHideToolbar: OnShowHideToolbar) :
        PagerAdapter() {

    internal var mLayoutInflater: LayoutInflater =
            mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return imagesArrayList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = mLayoutInflater.inflate(R.layout.full_image_item, container, false)

        val imageView = itemView.findViewById(R.id.imgFullImage) as ImageViewTouch
        val imgFullVideo = itemView.findViewById(R.id.imgFullVideo) as ImageView
        val imgFullPlayLogo = itemView.findViewById(R.id.imgFullPlayLogo) as ImageView

        imageView.setOnTouchListener { view, motionEvent ->
            if (imageView.scale > 1f) {
                touchViewPager.DisallowInterceptTouchEvent(true)
            } else {
                touchViewPager.DisallowInterceptTouchEvent(false)
            }
            return@setOnTouchListener false
        }

        if (imagesArrayList[position].isImageOrVideo == IMAGE) {

            imgFullPlayLogo.visibility = View.GONE
            imgFullVideo.visibility = View.GONE
            imageView.visibility = View.VISIBLE
            menuOption.hideMenuOption(true)

            Glide.with(mContext)
                    .load(Uri.parse("file://" + imagesArrayList[position].filePath))
                    .fitCenter()
                    .into(imageView)

            imageView.setSingleTapListener({
                onShowHideToolbar.showHideToolbar()
            })

        } else {

            menuOption.hideMenuOption(false)
            imgFullPlayLogo.visibility = View.VISIBLE
            imgFullVideo.visibility = View.VISIBLE
            imageView.visibility = View.GONE

            Glide.with(mContext)
                    .load(R.mipmap.ic_play_logo)
                    .fitCenter()
                    .into(imgFullPlayLogo)

            Glide.with(mContext)
                    .load(Uri.parse("file://" + imagesArrayList[position].filePath))
                    .fitCenter()
                    .into(imgFullVideo)

            imgFullVideo.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(Uri.parse("file://" + imagesArrayList[position].filePath), "video/*")
                startActivity(mContext, intent, null)
            }

        }
        container.addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

    interface TouchViewPager {
        fun DisallowInterceptTouchEvent(disallow: Boolean)
    }

    interface MenuOption {
        fun hideMenuOption(isImageOrVideo: Boolean)
    }

    interface OnShowHideToolbar {
        fun showHideToolbar()
    }

}
