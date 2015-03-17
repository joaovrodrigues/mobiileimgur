package joaorodrigues.mobileimgur.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import joaorodrigues.mobileimgur.R;

/**
 * Dropdown window for the main activity.
 *
 * Enables scaling of the UI and sets the IMGUR api.
 */
public class DropdownWindow extends PopupWindow
        implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener{

    private SeekBar mSeekBar;
    private int mProgress;
    private OnProgressChangedListener mListener;

    /**
     * @param context context;
     * @param anchor anchor view;
     * @param progress progress in percentage.
     */
    public DropdownWindow(Context context, View anchor, int progress) {
        super(context);
        //for somereason popupwindow, when the content view isnt added through the constructor
        //decides to add its own background drawable as a layer. Generally speaking, this is one
        //bad class.
        setBackgroundDrawable(null);

        //get display metrics for width and height
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        setWidth(size.x);
        setHeight(size.y/5);
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_popup, null));

        this.mProgress = progress;



        /*setWidth(width);
        setHeight(height/5);
        setFocusable(false);*/
        showAsDropDown(anchor, -200, 0);
        //update(width, height);

        Button close = (Button) getContentView().findViewById(R.id.btn_dismiss);
        close.setOnClickListener(this);
        mSeekBar = (SeekBar) getContentView().findViewById(R.id.sb_scale);
        mSeekBar.setProgress(mProgress);
        mSeekBar.setOnSeekBarChangeListener(this);

    }

    public void setOnProgressChangedListener(OnProgressChangedListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dismiss:
                this.dismiss();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mListener != null) {
            mListener.onProgressChanged(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public interface OnProgressChangedListener{
        public void onProgressChanged(int progress);
    }


}
