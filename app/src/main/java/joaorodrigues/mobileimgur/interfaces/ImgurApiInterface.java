package joaorodrigues.mobileimgur.interfaces;

import java.util.List;
import java.util.concurrent.Callable;


import joaorodrigues.mobileimgur.model.Image;
import joaorodrigues.mobileimgur.model.RetrofitResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * Retrofit imgur interface
 */
public interface ImgurApiInterface {

    @Headers("Authorization: Client-ID b165ec613fd601a")
    @GET("/3/gallery/hot/viral/0.json?showViral=true")
    void listImages(Callback<RetrofitResponse> callback);

}
