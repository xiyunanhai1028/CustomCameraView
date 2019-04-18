package com.xince.customcamera.camera;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.xince.customcamera.R;

/**
 * author : dufeihu
 * date   : 2019/4/1810:45
 * desc   :
 */
public class OCRCameraLayout extends FrameLayout {
    //垂直方向
    public static int ORIENTATION_PORTRAIT = 0;
    //水平方向
    public static int ORIENTATION_HORIZONTAL = 1;
    //方向
    private int orientation = ORIENTATION_PORTRAIT;
    private View contentView;
    private View centerView;
    private View leftDownView;
    private View rightUpView;

    private int contentViewId;
    private int centerViewId;
    private int leftDownViewId;
    private int rightUpViewId;

    /**
     * 设置方向
     * @param orientation
     */
    public void setOrientation(int orientation) {
        if (this.orientation == orientation) {
            return;
        }
        this.orientation = orientation;
        requestLayout();
    }

    public OCRCameraLayout(Context context) {
        this(context,null);
    }

    public OCRCameraLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public OCRCameraLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttrs(attrs);
    }

    {
        setWillNotDraw(false);
    }

    private void parseAttrs(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.OCRCameraLayout,
                0, 0);
        try {
            contentViewId = a.getResourceId(R.styleable.OCRCameraLayout_contentView, -1);
            centerViewId = a.getResourceId(R.styleable.OCRCameraLayout_centerView, -1);
            leftDownViewId = a.getResourceId(R.styleable.OCRCameraLayout_leftDownView, -1);
            rightUpViewId = a.getResourceId(R.styleable.OCRCameraLayout_rightUpView, -1);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        contentView = findViewById(contentViewId);
        if (centerViewId != -1) {
            centerView = findViewById(centerViewId);
        }
        leftDownView = findViewById(leftDownViewId);
        rightUpView = findViewById(rightUpViewId);
    }

    private Rect backgroundRect = new Rect();
    private Paint paint = new Paint();

    {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.argb(83, 0, 0, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int width = getWidth();
        int height = getHeight();
        int left;
        int top;

        MarginLayoutParams leftDownViewLayoutParams = (MarginLayoutParams) leftDownView.getLayoutParams();
        MarginLayoutParams rightUpViewLayoutParams = (MarginLayoutParams) rightUpView.getLayoutParams();
        if (r < b) {
            int contentHeight = width * 4 / 3;
            int heightLeft = height - contentHeight;
            contentView.layout(l, t, r, contentHeight);

            backgroundRect.left = 0;
            backgroundRect.top = contentHeight;
            backgroundRect.right = width;
            backgroundRect.bottom = height;

            // layout centerView;
            if (centerView != null) {
                left = (width - centerView.getMeasuredWidth()) / 2;
                top = contentHeight + (heightLeft - centerView.getMeasuredHeight()) / 2;
                centerView
                        .layout(left, top, left + centerView.getMeasuredWidth(), top + centerView.getMeasuredHeight());
            }
            // layout leftDownView

            left = leftDownViewLayoutParams.leftMargin;
            top = contentHeight + (heightLeft - leftDownView.getMeasuredHeight()) / 2;
            leftDownView
                    .layout(left, top, left + leftDownView.getMeasuredWidth(), top + leftDownView.getMeasuredHeight());
            // layout rightUpView
            left = width - rightUpView.getMeasuredWidth() - rightUpViewLayoutParams.rightMargin;
            top = contentHeight + (heightLeft - rightUpView.getMeasuredHeight()) / 2;
            rightUpView.layout(left, top, left + rightUpView.getMeasuredWidth(), top + rightUpView.getMeasuredHeight());
        } else {
            int contentWidth = height * 4 / 3;
            int widthLeft = width - contentWidth;
            contentView.layout(l, t, contentWidth, height);

            backgroundRect.left = contentWidth;
            backgroundRect.top = 0;
            backgroundRect.right = width;
            backgroundRect.bottom = height;

            // layout centerView
            if (centerView != null) {
                left = contentWidth + (widthLeft - centerView.getMeasuredWidth()) / 2;
                top = (height - centerView.getMeasuredHeight()) / 2;
                centerView
                        .layout(left, top, left + centerView.getMeasuredWidth(), top + centerView.getMeasuredHeight());
            }
            // layout leftDownView
            left = contentWidth + (widthLeft - leftDownView.getMeasuredWidth()) / 2;
            top = height - leftDownView.getMeasuredHeight() - leftDownViewLayoutParams.bottomMargin;
            leftDownView
                    .layout(left, top, left + leftDownView.getMeasuredWidth(), top + leftDownView.getMeasuredHeight());
            // layout rightUpView
            left = contentWidth + (widthLeft - rightUpView.getMeasuredWidth()) / 2;

            top = rightUpViewLayoutParams.topMargin;
            rightUpView.layout(left, top, left + rightUpView.getMeasuredWidth(), top + rightUpView.getMeasuredHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(backgroundRect, paint);
    }
}

