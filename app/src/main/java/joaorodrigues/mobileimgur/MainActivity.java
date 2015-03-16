package joaorodrigues.mobileimgur;

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

import joaorodrigues.mobileimgur.adapter.RecyclerViewAdapter;
import joaorodrigues.mobileimgur.controller.ImgurController;
import joaorodrigues.mobileimgur.events.DatasetUpdateEvent;
import joaorodrigues.mobileimgur.views.RecyclerViewOnScrollListener;
import joaorodrigues.mobileimgur.views.StableRecyclerView;
import retrofit.RetrofitError;


public class MainActivity extends BaseActivity
    implements SeekBar.OnSeekBarChangeListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private ImgurController mImgurController;
    private StableRecyclerView mGridView;
    private RecyclerViewAdapter mAdapter;
    private double mScale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        this.mScale = 1;
        mGridView = (StableRecyclerView) findViewById(R.id.rv_images);
        mGridView.setLayoutManager(StableRecyclerView.GRID_LAYOUT);
        mGridView.setOnScrollListener(new RecyclerViewOnScrollListener(this));
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
                int width = getWindow().getAttributes().width;
                final PopupWindow popupWindow = new PopupWindow(
                        getLayoutInflater().inflate(R.layout.dialog_popup, null, false),
                        width, 200, false);

                View view = findViewById(item.getItemId());
                popupWindow.showAsDropDown(view, -200, 0);

                SeekBar scale = (SeekBar)popupWindow.
                        getContentView().findViewById(R.id.sb_scale);

                scale.setProgress((int)(mScale*100));
                scale.setOnSeekBarChangeListener(this);

                Button button = (Button) popupWindow.
                        getContentView().findViewById(R.id.btn_dismiss);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onImageListReceived(DatasetUpdateEvent event) {
        if (event.getData() != null) {
            mAdapter = new RecyclerViewAdapter(event.getData());
            mAdapter.setScale(mScale);
            mGridView.setAdapter(mAdapter);
        }
    }

    @Subscribe
    public void onRetrofitError(RetrofitError error) {
        Toast.makeText(this, "Error Loading images!\n" +
                error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.e("seekbar", progress + "");
        this.mScale = progress > 19 ? progress/100d : mScale;
        mGridView.setScale(mScale);
        if (mAdapter != null) {
            mAdapter.setScale(mScale);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
