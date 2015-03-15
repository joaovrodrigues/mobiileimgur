package joaorodrigues.mobileimgur;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import joaorodrigues.mobileimgur.adapter.RecyclerViewAdapter;
import joaorodrigues.mobileimgur.controller.ImgurController;
import joaorodrigues.mobileimgur.events.DatasetUpdateEvent;
import joaorodrigues.mobileimgur.views.StableRecyclerView;
import retrofit.RetrofitError;


public class MainActivity extends BaseActivity
    implements SeekBar.OnSeekBarChangeListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private ImgurController mImgurController;
    private StableRecyclerView mGridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mGridView = (StableRecyclerView) findViewById(R.id.rv_images);
        mGridView.setLayoutManager(StableRecyclerView.GRID_LAYOUT);

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
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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
                PopupWindow popupWindow = new PopupWindow(
                        getLayoutInflater().inflate(R.layout.dialog_popup, null, false),
                        , 100, false);

                View view = findViewById(item.getItemId());
                popupWindow.showAsDropDown(view, -200, 0);

                SeekBar scale = (SeekBar)popupWindow.getContentView().findViewById(R.id.sb_scale);
                scale.setOnSeekBarChangeListener(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onImageListReceived(DatasetUpdateEvent event) {
        if (event.getData() != null) {
            mGridView.setAdapter(new RecyclerViewAdapter(event.getData()));
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
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
