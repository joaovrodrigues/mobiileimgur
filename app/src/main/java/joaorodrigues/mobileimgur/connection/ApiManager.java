package joaorodrigues.mobileimgur.connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;

import joaorodrigues.mobileimgur.MobileImgur;
import joaorodrigues.mobileimgur.R;
import joaorodrigues.mobileimgur.controller.ImgurController;
import joaorodrigues.mobileimgur.interfaces.ApiImgur;
import joaorodrigues.mobileimgur.interfaces.ApiProvider;
import joaorodrigues.mobileimgur.interfaces.BusProvider;
import joaorodrigues.mobileimgur.model.Album;
import joaorodrigues.mobileimgur.model.ImageList;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Api Manager for Mobile Imgur
 */
public class ApiManager implements ApiProvider {


    public static final String SECTION_HOT = "hot";
    public static final String SECTION_TOP = "top";
    public static final String SECTION_USER = "user";

    public static final String SORT_VIRAL = "viral";
    public static final String SORT_TOP = "top";
    public static final String SORT_TIME = "time";
    public static final String SORT_RISING = "rising";

    public static final String WINDOW_DAY = "day";
    public static final String WINDOW_WEEK = "week";
    public static final String WINDOW_MONTH = "month";
    public static final String WINDOW_YEAR = "year";

    private ApiImgur mApi;

    public ApiManager() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addHeader("Authorization", "Client-ID " + MobileImgur.get().getString(R.string.client_id));
            }
        };

        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(ImageList.class, new ImageList.Deserializer())
                .registerTypeAdapter(Album.class, new Album.Deserializer())
                .create();

        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(requestInterceptor)
                .setEndpoint("https://api.imgur.com")
                .build();

        mApi = restAdapter.create(ApiImgur.class);
    }

    public ApiImgur getApi() {
        return mApi;
    }
}
