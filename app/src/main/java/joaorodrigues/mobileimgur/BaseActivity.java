package joaorodrigues.mobileimgur;

import android.support.v4.app.FragmentActivity;
import com.squareup.otto.Bus;

import joaorodrigues.mobileimgur.interfaces.ApiImgur;
import joaorodrigues.mobileimgur.interfaces.ApiProvider;
import joaorodrigues.mobileimgur.interfaces.BusProvider;

/**
 * Created by joao on 13-03-2015.
 */
public class BaseActivity extends FragmentActivity implements BusProvider, ApiProvider {

    @Override
    public ApiImgur getApi() {
        return ((ApiProvider) getApplicationContext()).getApi();
    }

    @Override
    public Bus getBus() {
        return ((BusProvider) getApplicationContext()).getBus();
    }
}
