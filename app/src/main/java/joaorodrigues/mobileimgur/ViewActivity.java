package joaorodrigues.mobileimgur;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import joaorodrigues.mobileimgur.adapter.ViewPagerAdapter;
import joaorodrigues.mobileimgur.events.DatasetUpdateEvent;
import joaorodrigues.mobileimgur.events.PageRequestEvent;
import joaorodrigues.mobileimgur.model.Image;

/**
 * Image display activity
 */
public class ViewActivity extends BaseActivity
    implements ViewPager.OnPageChangeListener{

    private List<Image> mImageList;
    private ViewPagerAdapter mAdapter;
    private ViewPager mViewPager;

    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view);

        if (getIntent() != null) {
            mPosition = getIntent().getIntExtra("position", -1);
        }

        if (mPosition == -1) {
            throw new IllegalStateException("position has to be defined");
        }

        mImageList = (List<Image>) getLastCustomNonConfigurationInstance();

        mViewPager = (ViewPager) findViewById(R.id.vp_content);
        mViewPager.setOnPageChangeListener(this);

        if (mImageList != null) {
            mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mImageList);
            mViewPager.setAdapter(mAdapter);
            if (savedInstanceState != null) {
                mViewPager.setCurrentItem(savedInstanceState.getInt("position"));
            }
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mImageList;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mViewPager != null)
            outState.putInt("position", mViewPager.getCurrentItem());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBus().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getBus().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        final Intent intent = new Intent();
        intent.putExtra("position", mViewPager.getCurrentItem());
        setResult(RESULT_OK, intent);

        super.onBackPressed();
    }

    @Subscribe
    public void onDatasetChanged(DatasetUpdateEvent event) {
        this.mImageList = event.getData();
        if (mAdapter == null) {
            mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mImageList);
            mViewPager.setAdapter(mAdapter);
            mViewPager.setCurrentItem(mPosition);
        }else{
            mAdapter.changeData(mImageList);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            int position = mViewPager.getCurrentItem();
            mViewPager.setCurrentItem(position-1);
            return true;
        }else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            int position = mViewPager.getCurrentItem();
            mViewPager.setCurrentItem(position + 1);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPageSelected(int position) {
        if (mImageList.size() > 0 && mImageList.size() - 1 == position) {
            Toast.makeText(this, R.string.last_pic_page, Toast.LENGTH_LONG).show();
            getBus().post(new PageRequestEvent());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) { }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
}
