package joaorodrigues.mobileimgur.views;

/**
 * Created by joao on 15-03-2015.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import joaorodrigues.mobileimgur.R;

public class CardViewHolder extends RecyclerView.ViewHolder {

    public static final int GRID_STATE = 0;
    public static final int STAGGERED_STATE = 1;

    private ImageView mImageView;
    private TextView mTextView;
    private double mScale;
    private int mState;

    public CardViewHolder(View view, int scale, int state) {
        super(view);
        this.mImageView = (ImageView) view.findViewById(R.id.iv_card);
        this.mTextView = (TextView) view.findViewById(R.id.tv_title);
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
    }

    public void setState(int state) {
        this.mState = state;
    }

}