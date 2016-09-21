package com.mingchaogui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.ViewfinderView;

import java.util.List;

/**
 * Created by apple on 16/8/24.
 */
public class MQRCodeViewfinderView extends ViewfinderView {

    public int laserLinePosition = 0;
    public float[] position = new float[]{0f, 0.5f, 1f};
    public int[] laserColors;
    public int cornerColor;
    public LinearGradient linearGradient;

    public MQRCodeViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Resources resources = getResources();

        TypedArray attributes = getContext().obtainStyledAttributes(attrs, com.google.zxing.client.android.R.styleable.zxing_finder);
        cornerColor = attributes.getColor(
                com.google.zxing.client.android.R.styleable.zxing_finder_zxing_viewfinder_laser,
                resources.getColor(com.google.zxing.client.android.R.color.zxing_viewfinder_laser)
        );
        laserColors = new int[]{cornerColor, cornerColor, cornerColor};
        attributes.recycle();
    }

    @Override
    public void onDraw(Canvas canvas) {
        refreshSizes();
        if (framingRect == null || previewFramingRect == null) {
            return;
        }

        Rect frame = framingRect;
        Rect previewFrame = previewFramingRect;

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        //绘制4个角
        paint.setColor(cornerColor);
        canvas.drawRect(frame.left, frame.top, frame.left + 70, frame.top + 10, paint);
        canvas.drawRect(frame.left, frame.top, frame.left + 10, frame.top + 70, paint);

        canvas.drawRect(frame.right - 70, frame.top, frame.right, frame.top + 10, paint);
        canvas.drawRect(frame.right - 10, frame.top, frame.right, frame.top + 70, paint);

        canvas.drawRect(frame.left, frame.bottom - 10, frame.left + 70, frame.bottom, paint);
        canvas.drawRect(frame.left, frame.bottom - 70, frame.left + 10, frame.bottom, paint);

        canvas.drawRect(frame.right - 70, frame.bottom - 10, frame.right, frame.bottom, paint);
        canvas.drawRect(frame.right - 10, frame.bottom - 70, frame.right, frame.bottom, paint);

        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        } else {
            laserLinePosition = laserLinePosition + 5;
            if (laserLinePosition > frame.height()) {
                laserLinePosition = 0;
            }
            linearGradient = new LinearGradient(frame.left + 1, frame.top + laserLinePosition, frame.right - 1, frame.top + 10 + laserLinePosition, laserColors, position, Shader.TileMode.CLAMP);

            paint.setShader(linearGradient);
            //绘制扫描线
            canvas.drawRect(frame.left + 1, frame.top + laserLinePosition, frame.right - 1, frame.top + 10 + laserLinePosition, paint);
            paint.setShader(null);
            float scaleX = frame.width() / (float) previewFrame.width();
            float scaleY = frame.height() / (float) previewFrame.height();

            List<ResultPoint> currentPossible = possibleResultPoints;
            List<ResultPoint> currentLast = lastPossibleResultPoints;
            int frameLeft = frame.left;
            int frameTop = frame.top;
            if (currentPossible.isEmpty()) {
                lastPossibleResultPoints = null;
            } else {
                possibleResultPoints.clear();
                lastPossibleResultPoints = currentPossible;
                paint.setAlpha(CURRENT_POINT_OPACITY);
                paint.setColor(resultPointColor);
                for (ResultPoint point : currentPossible) {
                    canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                            frameTop + (int) (point.getY() * scaleY),
                            POINT_SIZE, paint);
                }
            }
            if (currentLast != null) {
                paint.setAlpha(CURRENT_POINT_OPACITY / 2);
                paint.setColor(resultPointColor);
                float radius = POINT_SIZE / 2.0f;
                for (ResultPoint point : currentLast) {
                    canvas.drawCircle(frameLeft + (int) (point.getX() * scaleX),
                            frameTop + (int) (point.getY() * scaleY),
                            radius, paint);
                }
            }

            postInvalidateDelayed(16,
                    frame.left,
                    frame.top,
                    frame.right,
                    frame.bottom);
        }
    }
}
