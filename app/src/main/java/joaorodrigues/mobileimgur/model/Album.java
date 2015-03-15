package joaorodrigues.mobileimgur.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joao on 15-03-2015.
 */
public class Album {
    private String id;
    private List<Image> images;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public static class Deserializer implements JsonDeserializer<Album> {

        @Override
        public Album deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject data = json.getAsJsonObject().getAsJsonObject("data");
            Album album = new Album();
            album.setId(data.get("id").getAsString());
            JsonArray array = data.getAsJsonArray("images");
            List<Image> images = new ArrayList<>(array.size());

            for (JsonElement element : array) {
                images.add((Image) context.deserialize(element, Image.class));
            }

            album.setImages(images);

            return album;
        }
    }
}
