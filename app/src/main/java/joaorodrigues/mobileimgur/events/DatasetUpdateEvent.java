package joaorodrigues.mobileimgur.events;

import java.util.List;

import joaorodrigues.mobileimgur.model.Image;

/**
 * Created by joao on 15-03-2015.
 */
public class DatasetUpdateEvent extends AbstractEvent<List<Image>> {

    private int mPage;

    public DatasetUpdateEvent() {
    }

    public DatasetUpdateEvent(List<Image> data) {
        super(data);
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int page) {
        this.mPage = page;
    }

}
