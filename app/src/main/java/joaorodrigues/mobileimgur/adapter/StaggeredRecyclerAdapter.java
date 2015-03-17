package joaorodrigues.mobileimgur.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import joaorodrigues.mobileimgur.R;
import joaorodrigues.mobileimgur.model.Image;

/**
 * Staggered grid adapter.
 *
 * Currently not finished.
 */
public class StaggeredRecyclerAdapter extends AbstractAdapter<StaggeredRecyclerAdapter.ImageViewHolder> {

    private static final int IMAGE_WIDTH = 300;

    private List<Image> mImageList;
    private double mScale;

    public StaggeredRecyclerAdapter(List<Image> data) {
        this.mImageList = data;
    }

    @Override
    public void changeData(List<Image> imageList) {
        this.mImageList = imageList;
    }

    @Override
    public void setScale(double scale) {
        this.mScale = scale;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_imagestaggered, parent, false);

        ImageViewHolder viewHolder = new ImageViewHolder(view, parent);
        viewHolder.setScale(mScale);
        Image image = mImageList.get(viewType);
        int width;
        int height;

        if (image.isAlbum()) {
            width = image.getCoverWidth();
            height = image.getCoverHeigth();
            viewHolder.setResolutionRatio((double)height / (double)width);

        }else{
            width = image.getHeight();
            height = image.getWidth();
            viewHolder.setResolutionRatio((double)height / (double)width);
        }

        viewHolder.setResolutionRatio((double)height / (double)width);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Image image = mImageList.get(position);
        Context context = holder.getImageView().getContext();
        holder.setScale(mScale);
        int width;
        int height;

        if (image.isAlbum()) {
            width = image.getCoverWidth();
            height = image.getCoverHeigth();

            if(image.getAlbum()!= null)
                image = image.getAlbum().get(0);
            else
                return;
        }else{
            width = image.getHeight();
            height = image.getWidth();
        }

        Picasso picasso = Picasso.with(context);
        picasso.setIndicatorsEnabled(true);
        picasso.load(image.getLink())
                .resize(IMAGE_WIDTH, getCroppedHeight(width, height))
                .centerInside()
                .into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    /**
     * limiting factor == width -> since we will have a vertical scroll
     * we only need to calculate the heigth and leave the width as is.
     * <p/>
     * We're going to keep on using the width at 300px, I feel that's a
     * good middle ground between performance and quality. Will be tweaked
     * if necessary.
     */

    private double getImageCropRatio(int width) {
        return IMAGE_WIDTH / width;
    }

    private int getCroppedHeight(int width, int height) {
        return (int)(getImageCropRatio(width)*height);
    }


    /**
     * ImageView holder for the staggered grid recycler view
     */
    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private ViewGroup mParent;
        private double mResolutionRatio;
        private double mScale;

        public ImageViewHolder(View view, ViewGroup parent) {
            super(view);
            this.mImageView = (ImageView) view;
            this.mParent = parent;
        }

        public void setResolutionRatio(double scaleRatio) {
            this.mResolutionRatio = scaleRatio;
            scaleViews();
        }

        public void setScale(double scale) {
            this.mScale = scale;
        }

        public ImageView getImageView() {
            return this.mImageView;
        }

        private void scaleViews() {
            int height = (int)(getScaledWidth() * mResolutionRatio);
            int width = getScaledWidth();
        }


        /**
         * Since all the layout scrolling will be vertical, the width will be the
         * main limiting factor.
         *
         * @return
         */
        private int getScaledWidth() {
            int parentWidth = mParent.getWidth();
            int scaledWidth = (int) (parentWidth * mScale);
            Log.e("parent and scale", mParent.getWidth() + " " + mScale + " " + scaledWidth);
            return scaledWidth;
        }
    }
}
