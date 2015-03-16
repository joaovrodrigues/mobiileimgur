package joaorodrigues.mobileimgur.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import joaorodrigues.mobileimgur.R;
import joaorodrigues.mobileimgur.model.Image;
import joaorodrigues.mobileimgur.views.CardViewHolder;

/**
 * Recycler View Adapter
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<CardViewHolder>{

    private List<Image> mImageList;
    private int mLayoutType;
    private double mScale;

    public RecyclerViewAdapter(List<Image> imageList) {
        this.mImageList = imageList;
    }

    public void setLayoutType(int layoutType) {
        this.mLayoutType = layoutType;
    }

    public void setScale(double scale) {
        this.mScale = scale;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listitem_image, viewGroup, false);

        return  new CardViewHolder(view, mScale, mLayoutType, viewGroup);
    }

    @Override
    public void onBindViewHolder(CardViewHolder cardViewHolder, int i) {

        Image image = mImageList.get(i);
        Context context = cardViewHolder.getImageView().getContext();

        cardViewHolder.getTextView().setText(image.getTitle());


        if (!image.isAlbum()) {

            Log.e("image link", image.getLink());
            Picasso picasso = Picasso.with(context);

            picasso.setIndicatorsEnabled(true);
            picasso.load(image.getLink())
                    .resize(300, 300)
                    .centerCrop()
                    .into(cardViewHolder.getImageView());

        } else if (image.getAlbum() != null) {

            Log.e("image link in album", image.getLink());
            Image albumPreview = image.getAlbum().get(0);
            Picasso picasso = Picasso.with(context);

            picasso.setIndicatorsEnabled(true);
            picasso.load(albumPreview.getLink())
                    .resize(300, 300)
                    .centerCrop()
                    .into(cardViewHolder.getImageView());

        }

    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

}
