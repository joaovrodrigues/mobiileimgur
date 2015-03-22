package joaorodrigues.mobileimgur.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import joaorodrigues.mobileimgur.R;
import joaorodrigues.mobileimgur.model.Image;

/**
 * Since I'm using the same layout for the album fragment list item and
 * the image fragment content view, I am using this view inflater to save
 * me some time.
 * <p/>
 * Since albums don't have View count this is most likely an inefficient way
 * of dealing with this issue, and we shouldn't be inflating views that are not going
 * to be used.
 * <p/>
 * Will come back to this soon.
 */
public class ImageViewInflater {


    public static View inflateAndSetViews(LayoutInflater inflater, ViewGroup container, Image image) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        return setViews(view, image);
    }

    public static View setViews(final View view, final Image image) {

        TextView title = (TextView) view.findViewById(R.id.tv_title);
        TextView description = (TextView) view.findViewById(R.id.tv_description);
        TextView points = (TextView) view.findViewById(R.id.tv_points);
        TextView views = (TextView) view.findViewById(R.id.tv_views);
        final ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);

        title.setText(image.getTitle());

        if (image.getDescription() != null) {
            description.setText(image.getDescription());
        } else {
            description.setVisibility(View.GONE);
        }

        if (image.getUps() != 0) {
            points.setText(Integer.toString(image.getUps()));
        } else {
            points.setVisibility(View.GONE);
        }

        if (image.getViews() != 0) {
            views.setText(Integer.toString(image.getViews()));
        } else {
            view.setVisibility(View.GONE);
        }

        if (image.getLink().contains(".gif")) {

            Glide.with(view.getContext())
                    .load(image.getLink())
                    .override(getScaledWidth(image.getWidth(), image.getHeight()),
                            getScaledHeight(image.getWidth(), image.getHeight()))
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);

        } else {

            Glide.with(view.getContext())
                    .load(image.getLink())
                    .override(getScaledWidth(image.getWidth(), image.getHeight()),
                            getScaledHeight(image.getWidth(), image.getHeight()))
                    .into(imageView);

        }

        return view;
    }

    /**
     * Scale down the image to a maximum of pixels defined in get
     * Resize pixel ratio
     *
     * @param width the original width of the image
     * @param height the original height of the image
     * @return the scaled height
     */
    private static int getScaledHeight(int width, int height) {
        return (int) (height * getResizeRatio(width, height));
    }


    /**
     * Scale down the image to a maximum of pixels defined in get
     * Resize pixel ratio
     *
     * @param width the original width of the image
     * @param height the original height of the image
     * @return the scaled height
     */
    private static int getScaledWidth(int width, int height) {
        return (int) (width * getResizeRatio(width, height));
    }


    /**
     * Scale down the image to a maximum of pixels defined in get
     * Resize pixel ratio
     *
     * Note: currently the maximumpixel size for an image is
     * hard coded in this method. Subject to change.
     *
     * @return scale ratio
     */
    private static double getResizeRatio(int width, int height) {
        final int maxDimen = 1080;//max pixels.

        if (width > maxDimen && width > height) {
            return (double) maxDimen / (double) width;
        } else if (height > maxDimen && width <= height) {
            return (double) maxDimen / (double) height;
        }

        return 1d;
    }
}
