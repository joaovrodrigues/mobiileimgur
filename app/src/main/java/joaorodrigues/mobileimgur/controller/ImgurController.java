package joaorodrigues.mobileimgur.controller;


import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import joaorodrigues.mobileimgur.connection.AlbumCallback;
import joaorodrigues.mobileimgur.connection.ApiManager;
import joaorodrigues.mobileimgur.connection.ImageListCallback;
import joaorodrigues.mobileimgur.events.AlbumUpdateEvent;
import joaorodrigues.mobileimgur.events.DatasetUpdateEvent;
import joaorodrigues.mobileimgur.events.GalleryUpdateEvent;
import joaorodrigues.mobileimgur.model.Album;
import joaorodrigues.mobileimgur.model.Image;
import joaorodrigues.mobileimgur.model.ImageList;

/**
 * Imgur data controller.
 *
 * This class is in charge of receiving and parsing the api received data
 * and calling the api when appropriate.
 *
 */
public class ImgurController {

    private HashMap<String, Image> mImageMap;
    private List<Image> mCurrentImages;
    private List<String> mAlbumgetList;

    private Bus mBus;
    private ApiManager mApiManager;

    private String mSection;
    private String mSort;
    private String mWindow;
    private boolean mShowViral;
    private int mPage;


    public ImgurController(Bus bus) {
        this.mImageMap = new HashMap<>();
        this.mCurrentImages = new ArrayList<>();
        this.mBus = bus;
        this.mApiManager = new ApiManager();
        this.mSection = ApiManager.SECTION_HOT;
        this.mSort = ApiManager.SORT_VIRAL;
        this.mWindow = ApiManager.WINDOW_DAY;
        this.mShowViral = true;
        this.mPage = 0;
    }

    public void setSection(String section) {
        this.mSection = section;
        refreshData();
    }

    public void setSort(String sort) {
        this.mSort = sort;
        refreshData();
    }

    public void setWindow(String window) {
        this.mWindow = window;
        refreshData();
    }

    public void setShowViral(boolean showViral) {
        this.mShowViral = showViral;
        refreshData();
    }

    public void setPage(int page) {
        this.mPage = page;
        refreshData();
    }

    public void refreshData() {
        if(mSection.equals(ApiManager.SECTION_TOP)) {
            mApiManager.getApi().listTopImages(mSort, mWindow, Integer.toString(mPage), new ImageListCallback(mBus));
        }else {
            mApiManager.getApi().listImages(mSection, mSort, Integer.toString(mPage), Boolean.toString(mShowViral), new ImageListCallback(mBus));
        }
    }

    /**
     * Updates the underlying dataset of image objects.
     * @param data
     */
    public void updateImageData(ImageList data) {
        mCurrentImages.clear();
        for (Image image : data) {
            if (!image.isAlbum()) {
                mImageMap.put(image.getId(), image);
            }else {
                //keep the old album while the new one is not loaded
                if (mImageMap.containsKey(image.getId())) {
                    List<Image> oldAlbum = mImageMap.get(image.getId()).getAlbum();
                    image.setAlbum(oldAlbum);
                }
                mImageMap.put(image.getId(), image);
            }

            mCurrentImages.add(image);
            mBus.post(getData());
            generateAlbumGetList();
            getNextAlbum();
        }
    }

    public void register() {
        mBus.register(this);
    }

    public void unregister() {
        mBus.unregister(this);
    }

    public List<Image> getCurrentImages() {
        return this.mCurrentImages;
    }

    /**
     * Creates the list of Strings with album ids to get information of.
     */
    public void generateAlbumGetList() {
        if(mAlbumgetList == null)
            mAlbumgetList = new ArrayList<>();

        mAlbumgetList.clear();

        for (Image image : mImageMap.values()) {
            if (image.isAlbum() && image.getAlbum() == null) {
                mAlbumgetList.add(image.getId());
            }
        }
    }

    /**
     * Gets the next album in queue.
     */
    public void getNextAlbum() {
        if (mAlbumgetList.size() > 0) {
            String id = mAlbumgetList.get(0);
            mApiManager.getApi().getAlbum(id, new AlbumCallback(mBus));
            mAlbumgetList.remove(0);
        }
    }

    /**
     *
     * @return the current list of items.
     */
    @Produce
    public DatasetUpdateEvent getData() {
        DatasetUpdateEvent event = new DatasetUpdateEvent();
        event.setData(mCurrentImages);
        return event;
    }

    @Subscribe
    public void receiveImageData(GalleryUpdateEvent event) {
        updateImageData(event.getData());
    }

    @Subscribe
    public void receiveAlbumData(AlbumUpdateEvent event) {
        Album album = event.getData();
        Image image = mImageMap.get(album.getId());
        image.setAlbum(album.getImages());
        getNextAlbum();
        //sends an event to notify the dataset changed
        mBus.post(new DatasetUpdateEvent(mCurrentImages));
    }

}