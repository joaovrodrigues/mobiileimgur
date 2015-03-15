package joaorodrigues.mobileimgur.events;

import joaorodrigues.mobileimgur.model.Album;

/**
 * Created by joao on 15-03-2015.
 */
public class AlbumUpdateEvent extends AbstractEvent<Album> {

    public AlbumUpdateEvent() {

    }

    public AlbumUpdateEvent(Album album) {
        super(album);
    }
}
