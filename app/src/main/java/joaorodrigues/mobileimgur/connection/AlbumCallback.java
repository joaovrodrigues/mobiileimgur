package joaorodrigues.mobileimgur.connection;

import android.util.Log;

import com.squareup.otto.Bus;

import joaorodrigues.mobileimgur.events.AlbumUpdateEvent;
import joaorodrigues.mobileimgur.model.Album;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by joao on 15-03-2015.
 */
public class AlbumCallback extends ApiCallback<Album> {

    public AlbumCallback(Bus bus) {
        super(bus);
        register();
    }

    @Override
    public void success(Album album, Response response) {
        AlbumUpdateEvent event = new AlbumUpdateEvent();
        if (album == null) {
            throw new IllegalStateException("album null");
        }
        event.setData(album);
        post(event);
        unregister();
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e("Album get error", error.getMessage());
        unregister();
    }
}
