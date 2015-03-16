package joaorodrigues.mobileimgur.views;

import android.app.Activity;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by joao on 15-03-2015.
 */
public class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

    private Activity mMainActivity;
    private int mYstart;

    public RecyclerViewOnScrollListener(Activity activity) {
        this.mMainActivity = activity;
        this.mYstart = 0;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        final ActionBar actionBar = mMainActivity.getActionBar();
        mYstart += dy;
        Log.e("variable", mYstart+"");
        /*if(Build.VERSION.SDK_INT > 20)
        if(dy > 0 && mMainActivity.getActionBar().getHideOffset()<actionBar.getHeight()) {
            mMainActivity.getActionBar().setHideOffset(dy);
        }else if (dy < 0 && mMainActivity.getActionBar().getHideOffset() > 0) {
            mMainActivity.getActionBar().setHideOffset(dy);
        }*/
        if (mYstart > 100) {
            actionBar.hide();
            this.mYstart = 0;
        }else if(mYstart < -100){
            actionBar.show();
            this.mYstart = 0;
        }

    }
}
