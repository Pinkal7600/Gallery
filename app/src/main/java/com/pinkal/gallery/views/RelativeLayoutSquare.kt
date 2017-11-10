package com.pinkal.gallery.views

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

/**
 * Created by Pinkal on 19/7/17.
 */

class RelativeLayoutSquare : RelativeLayout {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec) // This is the key that will make the height equivalent to its width
    }
}
