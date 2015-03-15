package joaorodrigues.mobileimgur.events;

import java.util.List;

import joaorodrigues.mobileimgur.model.Image;

/**
 * Created by joao on 15-03-2015.
 */
public class DatasetUpdateEvent extends AbstractEvent<List<Image>> {

    public DatasetUpdateEvent() {

    }

    public DatasetUpdateEvent(List<Image> data) {
        super(data);
    }

}
