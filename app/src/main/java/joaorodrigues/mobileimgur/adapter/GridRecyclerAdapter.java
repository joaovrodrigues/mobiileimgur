package joaorodrigues.mobileimgur.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import joaorodrigues.mobileimgur.R;
import joaorodrigues.mobileimgur.model.Image;

/**
 * Recycler View Adapter
 */
public class GridRecyclerAdapter extends AbstractRecyclerAdapter<GridRecyclerAdapter.CardViewHolder> {

    private List<Image> mImageList;
    private OnGridItemClickListener mListener;

    private double mScale;

    public GridRecyclerAdapter(List<Image> imageList) {
        this.mImageList = imageList;
    }

    @Override
    public void setScale(double scale) {
        this.mScale = scale;
    }

    @Override
    public void changeData(List<Image> imageList) {
        this.mImageList = imageList;
        notifyDataSetChanged();
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listitem_cardview, viewGroup, false);

        return new CardViewHolder(view, mScale, viewGroup);
    }

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, final int i) {

        Image image = mImageList.get(i);
        final Context context = cardViewHolder.getImageView().getContext();

        cardViewHolder.getTextView().setText(image.getTitle());


        if (image.isAlbum()) {
            if (image.getAlbum() != null)
                image = image.getAlbum().get(0);
            else
                return;
        }

        Glide.with(context).load(image.getLink())
                .override(300, 300)
                .centerCrop()
                .into(cardViewHolder.getImageView());

        cardViewHolder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onGridItemClicked(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public void setOnItemClickListener(OnGridItemClickListener listener) {
        this.mListener = listener;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {


        private ImageView mImageView;
        private TextView mTextView;
        private CardView mCardView;
        private ViewGroup mParent;
        private double mScale;

        /**
         * Sets and redimensions the views for the scale. If the scale is bigger then so should the
         * views be.
         * if scale == 1 the view
         *
         * @param scale
         */
        public CardViewHolder(View view, double scale, ViewGroup parent) {
            super(view);
            this.mCardView = (CardView) view.findViewById(R.id.cv_list_item);
            this.mImageView = (ImageView) view.findViewById(R.id.iv_card);
            this.mTextView = (TextView) view.findViewById(R.id.tv_title);
            this.mScale = scale;
            this.mParent = parent;
            scaleViews();
        }

        public ImageView getImageView() {
            return this.mImageView;
        }

        public TextView getTextView() {
            return this.mTextView;
        }

        private void scaleViews() {
            ViewGroup.LayoutParams params = mCardView.getLayoutParams();
            params.height = getScaledHeigth();
            params.width = getScaledWidth();
            mCardView.setLayoutParams(params);

            params = mImageView.getLayoutParams();
            params.height = getScaledWidth();
            params.width = getScaledWidth();
            mImageView.setLayoutParams(params);

            //TextView visibility when the card gets too small
            if (mScale < 0.45 && mTextView.getVisibility() == View.VISIBLE) {
                mTextView.setVisibility(View.GONE);
            } else if (mScale > 0.45 && mTextView.getVisibility() == View.GONE) {
                mTextView.setVisibility(View.VISIBLE);
            }

        }

        /**
         * Since all the layout scrolling will be vertical, the width will be the
         * main limiting factor.
         *
         * @return
         */
        private int getScaledWidth() {
            final int parentWidth = mParent.getWidth();
            final int scaledWidth = (int) (parentWidth * mScale);
            return scaledWidth;
        }

        /**
         * Height is approx. 1/3rd bigger than the width in the design goal,
         * if the textview is visible.
         *
         * @return
         */
        private int getScaledHeigth() {
            return mTextView.getVisibility() == View.VISIBLE ? (int) ((double) getScaledWidth() * 1.33333) : getScaledWidth();
        }
    }

    public interface OnGridItemClickListener {

        public void onGridItemClicked(int position);

    }
}
