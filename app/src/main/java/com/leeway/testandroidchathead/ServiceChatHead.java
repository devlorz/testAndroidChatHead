package com.leeway.testandroidchathead;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by Lee Lorz on 11/13/2016.
 */

public class ServiceChatHead extends Service {

    private WindowManager windowsManager;
    private ImageView chatImage;
    WindowManager.LayoutParams params;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        windowsManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        chatImage = new ImageView(this);
        chatImage.setImageResource(R.drawable.android_head);

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;


        chatImage.setOnTouchListener(listener);
        windowsManager.addView(chatImage, params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatImage != null) windowsManager.removeView(chatImage);
    }

    View.OnTouchListener listener = new View.OnTouchListener() {

        private int initialX;
        private int initialY;
        private float initialTouchX;
        private float initialTouchY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN :
                    initialX = params.x;
                    initialY = params.y;
                    initialTouchX = event.getRawX();
                    initialTouchY = event.getRawY();
                    return true;
                case MotionEvent.ACTION_UP:
                    return true;
                case MotionEvent.ACTION_MOVE:
                    params.x = initialX + (int) (event.getRawX() - initialTouchX);
                    params.y = initialY + (int) (event.getRawY() - initialTouchY);
                    windowsManager.updateViewLayout(chatImage,params);
                    return true;
            }
            return false;
        }
    };
}
