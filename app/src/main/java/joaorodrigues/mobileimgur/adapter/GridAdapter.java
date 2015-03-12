package joaorodrigues.mobileimgur.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import joaorodrigues.mobileimgur.R;
import joaorodrigues.mobileimgur.model.Image;

/**
 * Created by joao on 12-03-2015.
 */
public class GridAdapter extends BaseAdapter {

    Context context;
    List<Image> images;

    public GridAdapter(Context context, List<Image>images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Image getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view;
        if (convertView == null) {
            view = (ImageView) LayoutInflater.from(context).inflate(R.layout.listitem_image, parent, false);
        }else{
            view = (ImageView) convertView;
        }
        Image image = getItem(position);

        Log.e("image link", image.getLink());


        Picasso.with(context)
                .load(image.getLink())
                .resize(500, 500)
                .centerCrop()
                .into(view);


        return view;
    }
}
