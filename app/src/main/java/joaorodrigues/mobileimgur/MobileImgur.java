package joaorodrigues.mobileimgur;

import android.app.Application;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import joaorodrigues.mobileimgur.connection.ApiManager;
import joaorodrigues.mobileimgur.interfaces.ApiImgur;
import joaorodrigues.mobileimgur.interfaces.ApiProvider;
import joaorodrigues.mobileimgur.interfaces.BusProvider;


public class MobileImgur extends Application implements ApiProvider, BusProvider {
    private static MobileImgur sInstance;

    private ApiManager mApiManager;
    private Bus mBus;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mApiManager = new ApiManager();
        mBus = new Bus(ThreadEnforcer.MAIN);
    }

    public static MobileImgur get() {
        return sInstance;
    }

    @Override
    public Bus getBus() {
        return mBus;
    }

    @Override
    public ApiImgur getApi() {
        return mApiManager.getApi();
    }
}
