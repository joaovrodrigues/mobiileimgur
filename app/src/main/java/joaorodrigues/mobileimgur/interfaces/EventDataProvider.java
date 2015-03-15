package joaorodrigues.mobileimgur.interfaces;

/**
 * Created by joao on 15-03-2015.
 */
public interface EventDataProvider<T> {
    public T getData();

    public void setData(T data);
}
