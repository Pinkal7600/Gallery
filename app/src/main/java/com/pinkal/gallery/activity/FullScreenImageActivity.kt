package com.pinkal.gallery.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.pinkal.gallery.R
import com.pinkal.gallery.adapter.FullImageViewPagerAdapter
import com.pinkal.gallery.model.Images
import com.pinkal.gallery.utils.CURRENT_IMAGE
import com.pinkal.gallery.utils.IMAGES_LIST
import kotlinx.android.synthetic.main.activity_full_screen_image.*


/**
 * Created by Pinkal on 14/7/17.
 */
class FullScreenImageActivity : AppCompatActivity(), FullImageViewPagerAdapter.TouchViewPager, FullImageViewPagerAdapter.MenuOption, FullImageViewPagerAdapter.OnShowHideToolbar {

    val mActivity = this@FullScreenImageActivity

    lateinit var decorView: View
    var isToolbarVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_full_screen_image)

        initialize()

    }


    private fun initialize() {

        appbarFullScreen.setExpanded(true, true)
        appbarFullScreen.setBackgroundColor(resources.getColor(R.color.blackTransprant))
        toolbarFullScreen.setBackgroundColor(resources.getColor(R.color.blackTransprant))

        setSupportActionBar(toolbarFullScreen)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        decorView = (this as Activity).window.decorView

        val intentData = intent.extras

        val imagesList: ArrayList<Images> = intent.getSerializableExtra(IMAGES_LIST) as ArrayList<Images>

        val adapter = FullImageViewPagerAdapter(mActivity, imagesList, this, this, this)
        viewpagerImage.adapter = adapter
        viewpagerImage.currentItem = intentData.getInt(CURRENT_IMAGE)
        viewpagerImage.offscreenPageLimit = 5

    }

    override fun showHideToolbar() {
        if (isToolbarVisible) {
            appbarFullScreen.animate()
                    .translationYBy(0F)
                    .translationY((-appbarFullScreen.height).toFloat())
                    .duration = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

            isToolbarVisible = false
            hideSystemUI()
        } else {
            appbarFullScreen.animate()
                    .translationYBy((-appbarFullScreen.height).toFloat())
                    .translationY(0F)
                    .duration = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
            isToolbarVisible = true
            showSystemUI()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun DisallowInterceptTouchEvent(disallow: Boolean) {
        viewpagerImage.requestDisallowInterceptTouchEvent(disallow)
    }

    override fun hideMenuOption(isImageOrVideo: Boolean) {
//        if (isImageOrVideo) { // image
//
//            for (i in 0..menu.size() - 1) {
//                if (menu.getItem(i).itemId == R.id.action_Rotate_Right) menu.getItem(i).isVisible = true
//                if (menu.getItem(i).itemId == R.id.action_Rotate_left) menu.getItem(i).isVisible = true
//                if (menu.getItem(i).itemId == R.id.action_edit) menu.getItem(i).isVisible = true
//            }
//
//        } else {  // video
//
//            for (i in 0..menu.size() - 1) {
//                if (menu.getItem(i).itemId == R.id.action_Rotate_Right) menu.getItem(i).isVisible = false
//                if (menu.getItem(i).itemId == R.id.action_Rotate_left) menu.getItem(i).isVisible = false
//                if (menu.getItem(i).itemId == R.id.action_edit) menu.getItem(i).isVisible = false
//            }
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_full_screen, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_edit -> {
            }
            R.id.action_delete -> {
            }
            R.id.action_Rotate_Right -> {
            }
            R.id.action_Rotate_left -> {
            }
            R.id.action_details -> {
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun hideSystemUI() {
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_IMMERSIVE
    }

    private fun showSystemUI() {
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
}
