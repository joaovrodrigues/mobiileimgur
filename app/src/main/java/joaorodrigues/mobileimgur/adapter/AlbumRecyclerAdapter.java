package joaorodrigues.mobileimgur.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import joaorodrigues.mobileimgur.R;
import joaorodrigues.mobileimgur.model.Image;
import joaorodrigues.mobileimgur.util.ImageViewInflater;

/**
 * Recycler view adapter for the album fragment in the viewpager.
 */
public class AlbumRecyclerAdapter extends RecyclerView.Adapter<AlbumRecyclerAdapter.AlbumViewHolder> {

    private Image mAlbumImage;

    public AlbumRecyclerAdapter(Image albumImage) {
        this.mAlbumImage = albumImage;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.listitem_imagedisplay,
                parent, false);

        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        Image image = mAlbumImage.getAlbum().get(position);

        //small fix to get titles in album.
        if (position == 0) {
            image.setTitle(mAlbumImage.getTitle());
        }
        ImageViewInflater.setViews(holder.getView(), image);

    }

    @Override
    public int getItemCount() {
        return mAlbumImage.getAlbum().size();
    }

    @Override
    public void onViewRecycled(AlbumViewHolder holder) {
        super.onViewRecycled(holder);
        Glide.clear(holder.getImageView());
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private ImageView mImageView;

        public AlbumViewHolder(View view) {
            super(view);
            this.mView = view;
            mImageView = (ImageView) view.findViewById(R.id.iv_image);
        }

        public View getView() {
            return mView;
        }

        public ImageView getImageView() {
            return mImageView;
        }
    }
}
