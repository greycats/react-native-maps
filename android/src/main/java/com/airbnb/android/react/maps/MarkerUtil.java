package com.airbnb.android.react.maps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;

public class MarkerUtil {

    public static void setMarkerIcon(Context context, String imageUrl, final Marker marker) {
        final View customMarkerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);

        Glide.with(context).
                load(imageUrl)
                .asBitmap()
                .fitCenter()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        Log.e("haha",  "onResourceReady");
                        marker.setIcon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(customMarkerView, bitmap)));
                    }
                });

    }

    private static Bitmap getMarkerBitmapFromView(View view, Bitmap bitmap) {
        ImageView markerImage = (ImageView) view.findViewById(R.id.profile_image);
        markerImage.setImageBitmap(bitmap);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = view.getBackground();
        if (drawable != null) {
            drawable.draw(canvas);
        }
        view.draw(canvas);
        return returnedBitmap;
    }
}
