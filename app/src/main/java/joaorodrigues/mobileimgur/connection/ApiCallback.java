package joaorodrigues.mobileimgur.connection;

import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

import joaorodrigues.mobileimgur.events.AbstractEvent;
import joaorodrigues.mobileimgur.interfaces.BusProvider;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by joao on 13-03-2015.
 */
public abstract class ApiCallback<T> implements Callback<T> {
    private final Bus mBus;

    protected ApiCallback(Bus bus) {
        mBus = bus;
    }

    public void register() {
        mBus.register(this);
    }

    public void unregister() {
        mBus.unregister(this);
    }

    protected void post(AbstractEvent<T> event) {
        mBus.post(event);
    }
}
