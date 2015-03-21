package joaorodrigues.mobileimgur.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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

    public static View inflate(LayoutInflater inflater, ViewGroup container, Image image) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        return inflate(view, image);
    }

    public static View inflate(final View view, final Image image) {

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
            /*try {
                final URL url = new URL(image.getLink());

                new AsyncTask<Void, Void, GifDrawable>(){
                    @Override
                    protected GifDrawable doInBackground(Void... params) {
                        try {
                            InputStream inputStream = (InputStream) url.getContent();
                            GifDrawable drawable = new GifDrawable(new BufferedInputStream(inputStream, inputStream.available()));
                            return drawable;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(GifDrawable gifDrawable) {
                        if(gifDrawable != null) {
                                imageView.setImageDrawable(gifDrawable);
                        }else {
                            Log.e("asdfas", "gif drawable null");
                            Picasso.with(view.getContext())
                                    .load(image.getLink())
                                    .resize(getScaledWidth(image.getWidth(), image.getHeight()),
                                            getScaledHeight(image.getWidth(), image.getHeight()))
                                    .into(imageView);
                        }
                    }
                }.execute();

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        GifDrawable drawable = (GifDrawable) ((GifImageView) v).getDrawable();

                        if (drawable != null &&
                                drawable instanceof GifDrawable) {
                            ((GifDrawable) drawable).start();
                        } else {
                            Log.e("drawable null ", "or not gif drawable");
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();

            }*/

            /**
             * Ended up using Glide for gifs, will probably transfer every image
             * handling to it in the future, seeing how its more capable than
             * picasso for this particular project.
             */

            Glide.with(view.getContext())
                    .load(image.getLink())
                    .override(getScaledWidth(image.getWidth(), image.getHeight()),
                            getScaledHeight(image.getWidth(), image.getHeight()))
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

    private static int getScaledHeight(int width, int height) {
        return (int) (height * getResizeRatio(width, height));
    }

    private static int getScaledWidth(int width, int height) {
        return (int) (width * getResizeRatio(width, height));
    }

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
