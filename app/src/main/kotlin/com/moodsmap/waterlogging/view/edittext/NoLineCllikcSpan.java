package com.moodsmap.waterlogging.view.edittext;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

public class NoLineCllikcSpan extends ClickableSpan {

    public NoLineCllikcSpan() {
        super();
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        /**set textColor**/
        ds.setColor(ds.linkColor);
        /**Remove the underline**/
        ds.setUnderlineText(false);     
    }

    @Override
    public void onClick(View widget) {
    }
}