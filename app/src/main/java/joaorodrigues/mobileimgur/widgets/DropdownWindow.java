package joaorodrigues.mobileimgur.widgets;

import android.animation.ObjectAnimator;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import joaorodrigues.mobileimgur.R;

/**
 * Dropdown window for the main activity.
 *
 * Enables scaling of the UI and sets the IMGUR api.
 */
public class DropdownWindow
        implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener{


    private SeekBar mSeekBar;
    private OnProgressChangedListener mProgressListener;
    private OnApiChangedListener mApiChangedListener;
    private DropdownViewHolder mViewHolder;
    private View mView;

    private String mSort;
    private String mSection;
    private String mWindow;
    private boolean mShowViral;
    private int mProgress;

    private PopupMenu.OnMenuItemClickListener mSectionListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            setSection(item.getTitle().toString().toLowerCase());
            if (mApiChangedListener != null) {
                mApiChangedListener.onSectionChanged(item.getTitle().toString().toLowerCase());
            }
            return true;
        }
    };

    private PopupMenu.OnMenuItemClickListener mSortListener = new PopupMenu.OnMenuItemClickListener(){
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            setSort(item.getTitle().toString().toLowerCase());
            if (mApiChangedListener != null) {
                mApiChangedListener.onSortChanged(item.getTitle().toString().toLowerCase());
            }
            return true;
        }
    };

    private PopupMenu.OnMenuItemClickListener mWindowListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            setWindow(item.getTitle().toString().toLowerCase());
            if (mApiChangedListener != null) {
                mApiChangedListener.onWindowChanged(item.getTitle().toString().toLowerCase());
            }
            return true;
        }
    };

    /**
     * @param progress progress in percentage.
     */
    public DropdownWindow(RelativeLayout view, int progress) {
        this.mView = view;
        this.mProgress = progress;

        Button close = (Button) mView.findViewById(R.id.btn_dismiss);
        close.setOnClickListener(this);


        mSeekBar = (SeekBar) mView.findViewById(R.id.sb_scale);
        mSeekBar.setProgress(mProgress);
        mSeekBar.setOnSeekBarChangeListener(this);

        this.mViewHolder = new DropdownViewHolder();
        this.mViewHolder.setClickListener(this);
    }


    public void show() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mView, "y", mView.getY(), 0);
        animator.setDuration(800);
        animator.start();
    }

    public void dismiss() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mView, "y", mView.getY(), mView.getHeight()*-1);
        animator.setDuration(800);
        animator.start();
    }

    public boolean isShowing() {
        return mView.getY() >= 0;
    }

    public void setOnProgressChangedListener(OnProgressChangedListener listener) {
        this.mProgressListener = listener;
    }

    public void setOnApiChangedListener(OnApiChangedListener listener) {
        this.mApiChangedListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dismiss:
                dismiss();
                break;
            case R.id.btn_section:
                String[] section = mView.getContext().getResources().getStringArray(R.array.section);
                PopupSelectorMenu sectionMenu = new PopupSelectorMenu(mView.getContext(),
                        mViewHolder.mSectionButton,
                        section);
                sectionMenu.setOnMenuItemClickListener(mSectionListener);
                sectionMenu.show();
                break;
            case R.id.btn_sort:
                String[] sort = mView.getContext().getResources().getStringArray(R.array.sort);
                PopupSelectorMenu sortMenu = new PopupSelectorMenu(mView.getContext(),
                        mViewHolder.mSectionButton,
                        sort);
                sortMenu.setOnMenuItemClickListener(mSortListener);
                sortMenu.show();
                break;
            case R.id.btn_window:
                String[] window = mView.getContext().getResources().getStringArray(R.array.window);
                PopupSelectorMenu windowMenu = new PopupSelectorMenu(mView.getContext(),
                        mViewHolder.mSectionButton,
                        window);
                windowMenu.setOnMenuItemClickListener(mWindowListener);
                windowMenu.show();
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mProgressListener != null) {
            mProgressListener.onProgressChanged(progress);
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

    public String getSection() {
        return mSection;
    }

    public String getWindow() {
        return mWindow;
    }

    public void setSort(String sort) {
        mSort = sort;
        this.mViewHolder.mSortButton.setText(mSort.toUpperCase());
    }

    public void setSection(String section) {
        mSection = section;
        this.mViewHolder.mSectionButton.setText(mSection.toUpperCase());
    }

    public void setWindow(String window) {
        mWindow = window;
        this.mViewHolder.mWindowButton.setText(mWindow.toUpperCase());
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
        public void onSectionChanged(String section);

        public void onSortChanged(String sort);

        public void onWindowChanged(String window);

        public void onViralChanged(boolean showViral);
    }

    private class DropdownViewHolder{

        private Button mSortButton;
        private Button mSectionButton;
        private Button mWindowButton;

        DropdownViewHolder() {
            mSortButton = (Button) mView.findViewById(R.id.btn_sort);
            mSectionButton = (Button) mView.findViewById(R.id.btn_section);
            mWindowButton = (Button) mView.findViewById(R.id.btn_window);
        }

        protected void setClickListener(View.OnClickListener listener) {
            this.mWindowButton.setOnClickListener(listener);
            this.mSectionButton.setOnClickListener(listener);
            this.mSortButton.setOnClickListener(listener);
        }

    }
}
