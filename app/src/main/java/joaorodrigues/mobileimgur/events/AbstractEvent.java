package joaorodrigues.mobileimgur.events;

import joaorodrigues.mobileimgur.interfaces.EventDataProvider;

/**
 * Created by joao on 15-03-2015.
 */
public abstract class AbstractEvent<T>{

    protected T data;

    public AbstractEvent() {

    }

    public AbstractEvent(T data) {
        this.data = data;
    }


    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
