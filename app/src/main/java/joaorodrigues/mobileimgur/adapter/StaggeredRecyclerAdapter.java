package joaorodrigues.mobileimgur.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;

import java.util.List;

import joaorodrigues.mobileimgur.R;
import joaorodrigues.mobileimgur.model.Image;

/**
 * Staggered grid adapter.
 *
 * Currently not finished.
 *
 * It still takes a scale variable, but it won"t be functional for now, since the
 * Layout manager does most of the work.
 *
 *
 */
public class StaggeredRecyclerAdapter extends AbstractRecyclerAdapter<StaggeredRecyclerAdapter.ImageViewHolder> {

    private static final double IMAGE_WIDTH = 300;

    private List<Image> mImageList;

    public StaggeredRecyclerAdapter(List<Image> data) {
        this.mImageList = data;
    }

    @Override
    public void changeData(List<Image> imageList) {
        this.mImageList = imageList;
    }

    @Override
    public void setScale(double scale) {
            }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_imagestaggered, parent, false);

       return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder,final int position) {
        final Context context = holder.getImageView().getContext();
        Image image = mImageList.get(position);
        int width;
        int height;

        if (image.isAlbum()) {
            width = image.getCoverWidth();
            height = image.getCoverHeigth();

            if(image.getAlbum()!= null)
                //cover image is the first image in the album
                image = image.getAlbum().get(0);
            else {
                Glide.with(context).load(image.getLink())
                        .override((int)((double)width*getImageCropRatio(width)), (int)((double)height*getImageCropRatio(width)))
                        .into(holder.getImageView());
                return;
            }
        }else{
            width = image.getHeight();
            height = image.getWidth();
        }

        holder.getImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClicked(position);
            }
        });

        Glide.with(context)
                .load(image.getLink())
                .override((int)((double)width*getImageCropRatio(width)), (int)((double)height*getImageCropRatio(width)))
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
        return IMAGE_WIDTH /(double) width;
    }

    private int getCroppedHeight(int width, int height) {
        return (int)(getImageCropRatio(width)*height);
    }


    /**
     * ImageView holder for the staggered grid recycler view
     */
    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;

        public ImageViewHolder(View view) {
            super(view);
            this.mImageView = (ImageView) view;
        }

        public ImageView getImageView() {
            return this.mImageView;
        }

    }
}
