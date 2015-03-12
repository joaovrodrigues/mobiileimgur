package joaorodrigues.mobileimgur.connection;

import android.content.Context;
import android.util.Log;

import java.util.List;


import joaorodrigues.mobileimgur.interfaces.ImgurApiInterface;
import joaorodrigues.mobileimgur.model.Image;
import joaorodrigues.mobileimgur.model.RetrofitResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Created by joao on 12-03-2015.
 */
public class ApiManager {

    Context context;

    public ApiManager(Context context) {
        this.context = context;

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.imgur.com")
                .build();

        ImgurApiInterface service = restAdapter.create(ImgurApiInterface.class);


        Callback<RetrofitResponse> callback = new Callback<RetrofitResponse>() {
            @Override
            public void success(RetrofitResponse retrofitResponse, Response response) {
                List<Image> images = retrofitResponse.getData();
                for (Image image : images) {
                    Log.e(image.getId(), image.getId());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("tag", error.getMessage());
            }
        };

        service.listImages(callback);



    }

    public void somethingMethod() {
        OkClient client = new OkClient();

    }
}
