package joaorodrigues.mobileimgur.interfaces;


import joaorodrigues.mobileimgur.connection.ApiCallback;
import joaorodrigues.mobileimgur.connection.ImageListCallback;
import joaorodrigues.mobileimgur.model.Album;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Retrofit imgur interface
 */
public interface ApiImgur {

    @GET("/3/gallery/{section}/{sort}/{page}.json")
    void listImages(@Path("section") String section,
                    @Path("sort") String sort,
                    @Path("page") String page,
                    @Query("showViral") String showViral,
                    ImageListCallback callback);

    @GET("/3/gallery/top/{sort}/{window}/{page}.json")
    void listTopImages(@Path("sort") String sort,
                       @Path("window") String window,
                       @Path("page") String page,
                       ImageListCallback callback);

    @GET("/3/album/{id}")
    void getAlbum(@Path("id") String albumId, ApiCallback<Album> callback);
}
