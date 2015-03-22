package joaorodrigues.mobileimgur.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import joaorodrigues.mobileimgur.R;
import joaorodrigues.mobileimgur.model.Image;
import joaorodrigues.mobileimgur.util.ImageViewInflater;

/**
 * Single Image Fragment.
 *
 * Currently does not register in the event bus, so if there
 * are changes to the underlying dataset, the viewcount/points/etc
 * will not be changed in the view.
 */
public class ImageFragment extends ImgurAbstractFragment {

    private Image mImage;

    public static ImageFragment newInstance(Image image) {

        ImageFragment fragment = new ImageFragment();
        fragment.setRetainInstance(true);
        fragment.setImage(image);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return ImageViewInflater.inflateAndSetViews(inflater, container, mImage);
    }

    @Override
    public void destroy() {
        Log.e("destroy", "destroy called");
        final ImageView imageView = (ImageView) getView().findViewById(R.id.iv_image);
        Glide.clear(imageView);
    }

    public void setImage(Image image) {
        this.mImage = image;
    }
}
