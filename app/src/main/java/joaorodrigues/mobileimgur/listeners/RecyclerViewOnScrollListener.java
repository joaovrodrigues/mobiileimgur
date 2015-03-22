package joaorodrigues.mobileimgur.listeners;

import android.app.Activity;
import android.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import joaorodrigues.mobileimgur.BaseActivity;
import joaorodrigues.mobileimgur.R;
import joaorodrigues.mobileimgur.events.PageRequestEvent;

/**
 * On Scroll listener to hide the action bar in the main activity.
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

        //Check if the user is approachign the end of the dataset.
        //and if he is request more pages
        if (isTheLastMembers(recyclerView)) {
            ((BaseActivity)mMainActivity).getBus().post(new PageRequestEvent());
            Toast.makeText(mMainActivity, R.string.last_pic_page, Toast.LENGTH_LONG).show();
        }
    }

    private boolean isTheLastMembers(RecyclerView recyclerView) {
        final int offset = 10;
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            final View child = recyclerView.getChildAt(i);
            if (recyclerView.getChildPosition(child) >= recyclerView.getAdapter().getItemCount() - offset) {
                return true;
            }
        }

        return false;
    }

}
