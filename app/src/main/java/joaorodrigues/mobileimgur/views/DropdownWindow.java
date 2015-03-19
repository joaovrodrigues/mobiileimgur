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
    private OnProgressChangedListener mListener;
    private DropdownViewHolder mViewHolder;

    private String mSort;
    private String mSection;
    private String mWindow;
    private boolean mShowViral;
    private int mProgress;

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
        setHeight(size.y/4);

        //we dont need a parent as we are manually setting the params.
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_popup, null));

        this.mProgress = progress;
        showAsDropDown(anchor, 0, 0);

        Button close = (Button) getContentView().findViewById(R.id.btn_dismiss);
        close.setOnClickListener(this);
        mSeekBar = (SeekBar) getContentView().findViewById(R.id.sb_scale);
        mSeekBar.setProgress(mProgress);
        mSeekBar.setOnSeekBarChangeListener(this);

        this.mViewHolder = new DropdownViewHolder();
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

    public String getSort() {
        return mSort;
    }

    public void setSort(String sort) {
        mSort = sort;
        this.mViewHolder.mSortButton.setText(mSort);
    }

    public String getSection() {
        return mSection;
    }

    public void setSection(String section) {
        mSection = section;
        this.mViewHolder.mSectionButton.setText(mSection);
    }

    public String getWindow() {
        return mWindow;

    }

    public void setWindow(String window) {
        mWindow = window;
        this.mViewHolder.mWindowButton.setText(mWindow);
    }

    public boolean isShowViral() {
        return mShowViral;
    }

    public void setShowViral(boolean showViral) {
        mShowViral = showViral;
    }

    public interface OnProgressChangedListener{
        public void onProgressChanged(int progress);
    }

    public interface OnApiChangedListener{
        public void onSectionChangedListener(String section);

        public void onSortChangedListener(String sort);

        public void onWindowChangedListener(String window);

        public void onViralChangedListener(boolean showViral);
    }

    public class DropdownViewHolder{

        private Button mSortButton;
        private Button mSectionButton;
        private Button mWindowButton;

        DropdownViewHolder() {
            mSortButton = (Button) getContentView().findViewById(R.id.btn_sort);
            mSectionButton = (Button) getContentView().findViewById(R.id.btn_section);
            mWindowButton = (Button) getContentView().findViewById(R.id.btn_window);
        }

        protected void setClickListener(View.OnClickListener listener) {
            this.mWindowButton.setOnClickListener(listener);
            this.mSectionButton.setOnClickListener(listener);
            this.mSortButton.setOnClickListener(listener);
        }

    }
}
