package joaorodrigues.mobileimgur;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.List;

import joaorodrigues.mobileimgur.adapter.AbstractAdapter;
import joaorodrigues.mobileimgur.adapter.GridRecyclerAdapter;
import joaorodrigues.mobileimgur.adapter.StaggeredRecyclerAdapter;
import joaorodrigues.mobileimgur.controller.ImgurController;
import joaorodrigues.mobileimgur.events.DatasetUpdateEvent;
import joaorodrigues.mobileimgur.model.Image;
import joaorodrigues.mobileimgur.views.DropdownWindow;
import joaorodrigues.mobileimgur.views.RecyclerViewOnScrollListener;
import joaorodrigues.mobileimgur.views.StableRecyclerView;
import retrofit.RetrofitError;


public class MainActivity extends BaseActivity
    implements DropdownWindow.OnProgressChangedListener,
    DropdownWindow.OnApiChangedListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private ImgurController mImgurController;
    private StableRecyclerView mGridView;
    private AbstractAdapter mAdapter;
    private DropdownWindow mDropdownWindow;
    private double mScale;
    private int mLayoutType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        this.mScale = 1;
        this.mLayoutType = StableRecyclerView.GRID_LAYOUT;
        mGridView = (StableRecyclerView) findViewById(R.id.rv_images);
        mGridView.setLayoutManager(mLayoutType);
        mGridView.setOnScrollListener(new RecyclerViewOnScrollListener(this));
        mGridView.setHasFixedSize(false);
        mGridView.setScale(mScale);

        mImgurController = (ImgurController) getLastCustomNonConfigurationInstance();
        if (mImgurController == null) {
            mImgurController = new ImgurController(getBus());
            mImgurController.register();
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mImgurController;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImgurController.unregister();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getBus().register(this);
        mImgurController.refreshData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getBus().unregister(this);

        if(mDropdownWindow != null)
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
                if(mDropdownWindow == null) {
                    mDropdownWindow = new DropdownWindow(this,
                            findViewById(item.getItemId()), (int) (mScale * 100d));
                    mDropdownWindow.setOnProgressChangedListener(this);
                    mDropdownWindow.setWindow(mImgurController.getWindow());
                    mDropdownWindow.setSort(mImgurController.getSort());
                    mDropdownWindow.setSection(mImgurController.getSection());
                    return true;
                }
                Log.e("here", "about to dismiss it");
                if(mDropdownWindow.isShowing()){
                    mDropdownWindow.dismiss();
                }else {
                    mDropdownWindow.showAsDropDown(findViewById(item.getItemId()));
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onImageListReceived(DatasetUpdateEvent event) {
        if (event.getData() != null) {
            if (mAdapter == null) {
                mAdapter = getAdapter(event.getData());
                mAdapter.setScale(mScale);
                mGridView.setAdapter(mAdapter);
            } else {
                mAdapter.changeData(event.getData());
            }
        }
    }

    @Subscribe
    public void onRetrofitError(RetrofitError error) {
        Toast.makeText(this, "Error Loading images!\n" +
                error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressChanged(int progress) {
        Log.e("seekbar", progress + "");
        this.mScale = progress > 19 ? progress/100d : mScale;
        mGridView.setScale(mScale);
        if (mAdapter != null) {
            mAdapter.setScale(mScale);
        }
    }

    @Override
    public void onSectionChangedListener(String section) {
        mImgurController.setSection(section);
    }

    @Override
    public void onSortChangedListener(String sort) {
        mImgurController.setSort(sort);
    }

    @Override
    public void onWindowChangedListener(String window) {
        mImgurController.setWindow(window);
    }

    @Override
    public void onViralChangedListener(boolean showViral) {
        mImgurController.setShowViral(showViral);
    }

    private AbstractAdapter getAdapter(List<Image> data) {
        Log.e("getting adapter", "adapter");
        if (mLayoutType == StableRecyclerView.GRID_LAYOUT) {
            return new GridRecyclerAdapter(data);
        } else {
            return new StaggeredRecyclerAdapter(data);
        }
    }
}
