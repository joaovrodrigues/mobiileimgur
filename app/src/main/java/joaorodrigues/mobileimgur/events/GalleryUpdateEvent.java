package joaorodrigues.mobileimgur.events;

import joaorodrigues.mobileimgur.model.ImageList;

/**
 * Created by joao on 15-03-2015.
 */
public class GalleryUpdateEvent extends AbstractEvent<ImageList> {

    public GalleryUpdateEvent() {

    }

    public GalleryUpdateEvent(ImageList list) {
        super(list);
    }

}
