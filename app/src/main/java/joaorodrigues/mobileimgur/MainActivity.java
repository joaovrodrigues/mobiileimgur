package joaorodrigues.mobileimgur;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import java.util.List;

import joaorodrigues.mobileimgur.R;
import joaorodrigues.mobileimgur.adapter.GridAdapter;
import joaorodrigues.mobileimgur.connection.ApiManager;
import joaorodrigues.mobileimgur.interfaces.ImgurApiInterface;
import joaorodrigues.mobileimgur.model.Image;
import joaorodrigues.mobileimgur.model.RetrofitResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final GridView gridView = (GridView) findViewById(R.id.gv_images);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.imgur.com")
                .build();

        ImgurApiInterface service = restAdapter.create(ImgurApiInterface.class);



        Callback<RetrofitResponse> callback = new Callback<RetrofitResponse>() {
            @Override
            public void success(RetrofitResponse retrofitResponse, Response response) {
                List<Image> images = retrofitResponse.getData();
                GridAdapter adapter = new GridAdapter(MainActivity.this, images);
                gridView.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("tag", error.getMessage());
            }
        };

        service.listImages(callback);

    }
}
