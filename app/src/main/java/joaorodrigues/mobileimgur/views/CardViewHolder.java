package joaorodrigues.mobileimgur.views;

/**
 * Created by joao on 15-03-2015.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import joaorodrigues.mobileimgur.R;

public class CardViewHolder extends RecyclerView.ViewHolder {


    private ImageView mImageView;
    private TextView mTextView;
    private CardView mCardView;
    private ViewGroup mParent;
    private double mScale;
    private int mState;

    public CardViewHolder(View view, double scale, int state, ViewGroup parent) {
        super(view);
        this.mCardView = (CardView) view.findViewById(R.id.cv_list_item);
        this.mImageView = (ImageView) view.findViewById(R.id.iv_card);
        this.mTextView = (TextView) view.findViewById(R.id.tv_title);
        this.mScale = scale;
        this.mState = state;
        this.mParent = parent;
        scaleViews();
    }

    public ImageView getImageView() {
        return this.mImageView;
    }

    public TextView getTextView() {
        return this.mTextView;
    }

    /**
     * Sets and redimensions the views for the scale. If the scale is bigger then so should the
     * views be.
     * if scale == 1 the view
     * @param scale
     */
    public void setScale(double scale) {
        this.mScale = scale;
        scaleViews();
    }

    public void setState(int state) {
        this.mState = state;
    }

    private void scaleViews() {
        ViewGroup.LayoutParams params = mCardView.getLayoutParams();
        params.height=getScaledHeigth();
        params.width=getScaledWidth();
        mCardView.setLayoutParams(params);

        params = mImageView.getLayoutParams();
        params.height = getScaledWidth();
        params.width = getScaledWidth();
        mImageView.setLayoutParams(params);
    }

    private int getScaledWidth() {
        int parentWidth = mParent.getWidth();
        int scaledWidth = (int) (parentWidth * mScale);
        return scaledWidth;
    }

    private int getScaledHeigth() {
        return (int)((double) getScaledWidth() * 1.33333);
    }

}