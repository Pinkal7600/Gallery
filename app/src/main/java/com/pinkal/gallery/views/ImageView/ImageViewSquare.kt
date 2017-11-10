package com.pinkal.gallery.views.ImageView

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Created by Pinkal on 13/7/17.
 */

class ImageViewSquare : ImageView {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec) // This is the key that will make the height equivalent to its width
    }
}
