package joaorodrigues.mobileimgur.util;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import joaorodrigues.mobileimgur.R;
import joaorodrigues.mobileimgur.widgets.PopupSelectorMenu;
import joaorodrigues.mobileimgur.widgets.StableRecyclerView;

/**
 * Dropdown window for the main activity.
 * <p/>
 * Enables scaling of the UI and sets the IMGUR api.
 * <p/>
 * This class wraps the relative layout the dropdown window is in
 * and sets all the controls and necessary interfaces.
 */
public class DropdownWindow
        implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener {


    private SeekBar mSeekBar;
    private OnProgressChangedListener mProgressListener;
    private OnApiChangedListener mApiChangedListener;
    private DropdownViewHolder mViewHolder;
    private View mView;

    private String mSort;
    private String mSection;
    private String mWindow;
    private int mLayoutType;
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

    private PopupMenu.OnMenuItemClickListener mSortListener = new PopupMenu.OnMenuItemClickListener() {
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
        this.mViewHolder.mShowViralButton.setText("see viral");
    }


    public void show() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mView, "y", mView.getY(), 0);
        animator.setDuration(800);
        animator.start();
    }

    public void dismiss() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mView, "y", mView.getY(), mView.getHeight() * -1);
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
                final String[] section = mView.getContext().getResources().getStringArray(R.array.section);
                final PopupSelectorMenu sectionMenu = new PopupSelectorMenu(mView.getContext(),
                        mViewHolder.mSectionButton,
                        section);
                sectionMenu.setOnMenuItemClickListener(mSectionListener);
                sectionMenu.show();
                break;
            case R.id.btn_sort:
                final String[] sort = mView.getContext().getResources().getStringArray(R.array.sort);
                final PopupSelectorMenu sortMenu = new PopupSelectorMenu(mView.getContext(),
                        mViewHolder.mSectionButton,
                        sort);
                sortMenu.setOnMenuItemClickListener(mSortListener);
                sortMenu.show();
                break;
            case R.id.btn_window:
                final String[] window = mView.getContext().getResources().getStringArray(R.array.window);
                final PopupSelectorMenu windowMenu = new PopupSelectorMenu(mView.getContext(),
                        mViewHolder.mSectionButton,
                        window);
                windowMenu.setOnMenuItemClickListener(mWindowListener);
                windowMenu.show();
                break;
            case R.id.btn_showviral:
                if (mShowViral) {
                    setShowViral(false);
                } else {
                    setShowViral(true);
                }

                if (mApiChangedListener != null) {
                    mApiChangedListener.onViralChanged(mShowViral);
                }
                break;

            case R.id.btn_staggered:
                setLayoutType(StableRecyclerView.STAGGERED_LAYOUT);
                if (mApiChangedListener != null) {
                    mApiChangedListener.onLayoutTypeChanged(mLayoutType);
                }
                break;
            case R.id.btn_linear:
                setLayoutType(StableRecyclerView.GRID_LAYOUT);
                if (mApiChangedListener != null) {
                    mApiChangedListener.onLayoutTypeChanged(mLayoutType);
                }
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
        this.mViewHolder.mWindowButton.setText( mWindow.toUpperCase());
    }

    public void setShowViral(boolean showViral) {
        this.mShowViral = showViral;
        if (mShowViral) {
            this.mViewHolder.mShowViralButton.setTextColor(Color.WHITE);
        } else {
            this.mViewHolder.mShowViralButton.setTextColor(Color.GRAY);
        }
    }

    public void setLayoutType(int type) {
        this.mLayoutType = type;

        if (mLayoutType == StableRecyclerView.GRID_LAYOUT) {

            mViewHolder.mLinearAdapter.setTextColor(Color.GRAY);
            mViewHolder.mLinearAdapter.setClickable(false);
            mViewHolder.mStaggeredAdapter.setTextColor(Color.WHITE);
            mViewHolder.mStaggeredAdapter.setClickable(true);
            mSeekBar.setVisibility(View.VISIBLE);

        }else{

            mViewHolder.mLinearAdapter.setTextColor(Color.WHITE);
            mViewHolder.mLinearAdapter.setClickable(true);
            mViewHolder.mStaggeredAdapter.setTextColor(Color.GRAY);
            mViewHolder.mStaggeredAdapter.setClickable(false);
            mSeekBar.setVisibility(View.GONE);

        }
    }

    public interface OnProgressChangedListener {
        public void onProgressChanged(int progress);
    }

    public interface OnApiChangedListener {
        public void onSectionChanged(String section);

        public void onSortChanged(String sort);

        public void onWindowChanged(String window);

        public void onViralChanged(boolean showViral);

        public void onLayoutTypeChanged(int layoutType);
    }

    private class DropdownViewHolder {

        private Button mSortButton;
        private Button mSectionButton;
        private Button mWindowButton;
        private Button mShowViralButton;
        private Button mStaggeredAdapter;
        private Button mLinearAdapter;

        DropdownViewHolder() {
            this.mSortButton = (Button) mView.findViewById(R.id.btn_sort);
            this.mSectionButton = (Button) mView.findViewById(R.id.btn_section);
            this.mWindowButton = (Button) mView.findViewById(R.id.btn_window);
            this.mShowViralButton = (Button) mView.findViewById(R.id.btn_showviral);
            this.mStaggeredAdapter = (Button) mView.findViewById(R.id.btn_staggered);
            this.mLinearAdapter = (Button) mView.findViewById(R.id.btn_linear);
        }

        protected void setClickListener(View.OnClickListener listener) {
            this.mWindowButton.setOnClickListener(listener);
            this.mSectionButton.setOnClickListener(listener);
            this.mSortButton.setOnClickListener(listener);
            this.mShowViralButton.setOnClickListener(listener);
            this.mStaggeredAdapter.setOnClickListener(listener);
            this.mLinearAdapter.setOnClickListener(listener);
        }

    }
}
