package joaorodrigues.mobileimgur;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.List;

import joaorodrigues.mobileimgur.adapter.AbstractRecyclerAdapter;
import joaorodrigues.mobileimgur.adapter.GridRecyclerAdapter;
import joaorodrigues.mobileimgur.adapter.StaggeredRecyclerAdapter;
import joaorodrigues.mobileimgur.controller.ImgurController;
import joaorodrigues.mobileimgur.events.DatasetUpdateEvent;
import joaorodrigues.mobileimgur.listeners.RecyclerViewOnScrollListener;
import joaorodrigues.mobileimgur.model.Image;
import joaorodrigues.mobileimgur.util.DropdownWindow;
import joaorodrigues.mobileimgur.widgets.StableRecyclerView;
import retrofit.RetrofitError;


public class MainActivity extends BaseActivity
        implements DropdownWindow.OnProgressChangedListener,
        DropdownWindow.OnApiChangedListener, GridRecyclerAdapter.OnGridItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ImgurController mImgurController;
    private StableRecyclerView mGridView;
    private AbstractRecyclerAdapter mAdapter;
    private DropdownWindow mDropdownWindow;
    private TextView mTvError;

    private double mScale;
    private int mLayoutType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        this.mScale = 1;
        this.mLayoutType = StableRecyclerView.GRID_LAYOUT;
        this.mTvError = (TextView) findViewById(R.id.tv_error);

        mGridView = (StableRecyclerView) findViewById(R.id.rv_images);
        mGridView.setLayoutManager(mLayoutType);
        mGridView.setOnScrollListener(new RecyclerViewOnScrollListener(this));
        mGridView.setHasFixedSize(false);
        mGridView.setScale(mScale);

        /**
         * Handles configuration change.
         *
         * Since we're subclassing FragmentActivity that has the method
         * onRetainCustomNonConfigurationInstance we can save the adapter
         * and set it back to our recycler view.
         */
        mAdapter = (AbstractRecyclerAdapter) getLastCustomNonConfigurationInstance();

        if (mAdapter != null) {
            mGridView.setAdapter(mAdapter);
            if (savedInstanceState != null) {
                mGridView.scrollToPosition(savedInstanceState.getInt("position"));
            }
        }

        mImgurController = ImgurController.getInstance();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        if (mAdapter != null) {
            return mAdapter;
        }
        return null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mGridView != null)
            outState.putInt("position", mGridView.getFirstVisiblePosition());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBus().register(this);

        mDropdownWindow = new DropdownWindow((RelativeLayout) findViewById(R.id.rl_selectors),
                (int) (mScale * 100d));
        mDropdownWindow.setOnProgressChangedListener(this);
        mDropdownWindow.setOnApiChangedListener(this);

        mImgurController.refreshData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getBus().unregister(this);

        if (mDropdownWindow != null)
            mDropdownWindow.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case (R.id.btn_popupwindow):

                if (mDropdownWindow.isShowing()) {
                    mDropdownWindow.dismiss();
                } else {
                    mDropdownWindow.show();
                    mDropdownWindow.setWindow(mImgurController.getWindow());
                    mDropdownWindow.setSort(mImgurController.getSort());
                    mDropdownWindow.setSection(mImgurController.getSection());
                    mDropdownWindow.setShowViral(mImgurController.isShowViral());
                    mDropdownWindow.setLayoutType(mLayoutType);
                }

                break;

            case R.id.btn_refresh:
                mImgurController.refreshData();
                break;
            case R.id.btn_dialog:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            mGridView.smoothScrollBy(0, 100);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            mGridView.smoothScrollBy(0, -100);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * Receives the result from the view activity
     * and scrolls the gridview to its position.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            final int position = data.getIntExtra("position", 0);

            if (position > 0) {
                mGridView.scrollToPosition(position);
            }

        }

    }

    @Subscribe
    public void onImageListReceived(DatasetUpdateEvent event) {
        if (event.getData() != null) {
            mTvError.setVisibility(View.GONE);
            if (mAdapter == null) {
                mAdapter = getAdapter(event.getData());
                mAdapter.setScale(mScale);
                mGridView.setAdapter(mAdapter);
            } else {
                mAdapter.changeData(event.getData());
            }

            if (event.getPage() > 0) {
                Toast.makeText(this, "Loaded page " + event.getPage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Subscribe
    public void onRetrofitError(RetrofitError error) {
        mTvError.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Error Loading images!\n" +
                error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressChanged(int progress) {
        this.mScale = progress > 19 ? progress / 100d : mScale;
        mGridView.setScale(mScale);
        if (mAdapter != null) {
            mAdapter.setScale(mScale);
        }
    }

    @Override
    public void onSectionChanged(String section) {
        mImgurController.setSection(section);
    }

    @Override
    public void onSortChanged(String sort) {
        mImgurController.setSort(sort);
    }

    @Override
    public void onWindowChanged(String window) {
        mImgurController.setWindow(window);
    }

    @Override
    public void onViralChanged(boolean showViral) {
        mImgurController.setShowViral(showViral);
    }

    /**
     * Sets the layout type between
     *
      * @param layoutType
     */
    @Override
    public void onLayoutTypeChanged(int layoutType) {
        this.mLayoutType = layoutType;
        final int position = mGridView.getFirstVisiblePosition();

        mAdapter = getAdapter(mImgurController.getData().getData());
        mGridView.setLayoutManager(StableRecyclerView.STAGGERED_LAYOUT);
        mGridView.setAdapter(mAdapter);
        mGridView.scrollToPosition(position);
    }

    /**
     * On grid item interface. When the view is closed we want
     * to receive the position in the dataset the user was last
     * so we can scroll the recycler view to the apropriate position
     * So we will use the startActivity for result.
     *
     * @param position
     */
    @Override
    public void onGridItemClicked(int position) {
        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtra("position", position);
        startActivityForResult(intent, 0);
    }

    private AbstractRecyclerAdapter getAdapter(List<Image> data) {
        if (mLayoutType == StableRecyclerView.GRID_LAYOUT) {
            final GridRecyclerAdapter adapter = new GridRecyclerAdapter(data);
            adapter.setOnItemClickListener(this);
            return adapter;
        } else {
            StaggeredRecyclerAdapter adapter = new StaggeredRecyclerAdapter(data);
            adapter.setOnItemClickListener(this);
            adapter.setScale(0.4d);
            return adapter;
        }
    }


}
