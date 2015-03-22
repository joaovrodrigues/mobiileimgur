package joaorodrigues.mobileimgur.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import joaorodrigues.mobileimgur.R;


/**
 * Class that crops a bitmap to create a round imageview.
 *
 */
public class RoundedImageView extends ImageView {

    private double mBorderWidth = -1;
    private int mBorderColor = 0;


    public RoundedImageView(Context context) {
        super(context);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        final TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RoundedImageView,
                0, 0);
        try {
            mBorderWidth = a.getDimension(R.styleable.RoundedImageView_borderWidth, -1);
            mBorderColor = a.getInteger(R.styleable.RoundedImageView_borderColor, 0);
        } finally {
            a.recycle();
        }
    }

    /**
     * Sets a mBorderWidth. Set null for default width
     * @param borderWidth in pixels
     */
    public void setBorderWidth(Double borderWidth) {
        if(borderWidth != null) {
            this.mBorderWidth = borderWidth;
        }else{
            this.mBorderWidth = -1;
        }
        invalidate();
    }

    /**
     * Sets a border color. Set null for default color
     * @param borderColor hex value
     */
    public void setBorderColor(Integer borderColor) {
        if(borderColor != null) {
            this.mBorderColor = borderColor;
        }else{
            this.mBorderColor = 0;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        //fix to work with colors as a background
        Bitmap b = drawableToBitmap(drawable);


        if (b == null) {
            return;
        }

        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        int w = getWidth();
        Bitmap roundBitmap =  getCroppedBitmap(bitmap, w);

        Double border;
        if (mBorderWidth > -1) {
            border = mBorderWidth;
        }else {
            border = 0d;
        }

        Bitmap roundWithBorders = addBorder(roundBitmap, border.intValue());
        Bitmap finalBmp = getCroppedBitmap(roundWithBorders, w);

        canvas.drawBitmap(finalBmp, 0,0, null);
    }

    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {

        Bitmap bitmap;
        if(bmp.getWidth() != radius || bmp.getHeight() != radius)
            bitmap = Bitmap.createScaledBitmap(bmp,radius, radius, false);
        else
            bitmap = bmp;

        Bitmap output = Bitmap.createBitmap(radius-1,
                radius-1, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, radius, radius);

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.WHITE);
        canvas.drawCircle((radius) / 2, (radius) / 2, (radius)/(2+0.05f), paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * Default implementation of this class in the original project.
     * @param bmp
     * @param borderSize
     * @return
     */
    private Bitmap addBorder(Bitmap bmp, int borderSize) {
        return addBorder(bmp, borderSize, mBorderColor != 0 ? mBorderColor : Color.TRANSPARENT);
    }

    private Bitmap addBorder(Bitmap bmp, int borderSize, int color) {
        Bitmap bmpWithBorder = Bitmap.createBitmap(bmp.getWidth() + borderSize * 2, bmp.getHeight() + borderSize * 2, bmp.getConfig());
        Canvas canvas = new Canvas(bmpWithBorder);
        canvas.drawColor(color);
        canvas.drawBitmap(bmp, borderSize, borderSize, null);
        return bmpWithBorder;
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        final int width = !drawable.getBounds().isEmpty() ?
                drawable.getBounds().width() : drawable.getIntrinsicWidth();

        final int height = !drawable.getBounds().isEmpty() ?
                drawable.getBounds().height() : drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
