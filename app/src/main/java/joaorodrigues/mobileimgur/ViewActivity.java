package joaorodrigues.mobileimgur;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import joaorodrigues.mobileimgur.adapter.ViewPagerAdapter;
import joaorodrigues.mobileimgur.events.DatasetUpdateEvent;
import joaorodrigues.mobileimgur.model.Image;

/**
 * Image display activity
 */
public class ViewActivity extends BaseActivity {

    private Fragment mFragment;
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

        mViewPager = (ViewPager) findViewById(R.id.vp_content);
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

        return false;
    }
}
