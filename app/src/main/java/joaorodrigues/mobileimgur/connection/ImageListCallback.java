package joaorodrigues.mobileimgur.connection;

import android.util.Log;

import com.squareup.otto.Bus;

import joaorodrigues.mobileimgur.events.GalleryUpdateEvent;
import joaorodrigues.mobileimgur.model.ImageList;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by joao on 15-03-2015.
 */
public class ImageListCallback extends ApiCallback<ImageList> {


    public ImageListCallback(Bus bus) {
        super(bus);
        register();
    }

    @Override
    public void success(ImageList data, Response response) {
        GalleryUpdateEvent event = new GalleryUpdateEvent();
        event.setData(data);
        post(event);
        unregister();
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e("ImageList callback error", error.getMessage());
        unregister();
    }

}
