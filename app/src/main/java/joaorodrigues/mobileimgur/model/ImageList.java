package joaorodrigues.mobileimgur.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * ImageList class to deserialize the image array of the
 * imgur api gallery object
 */
public class ImageList extends ArrayList<Image> {
    private ImageList(int capacity) {
        super(capacity);
    }

    public static class Deserializer implements JsonDeserializer<ImageList> {

        @Override
        public ImageList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonArray data = json.getAsJsonObject().getAsJsonArray("data");
            ImageList imageList = new ImageList(data.size());
            for (JsonElement element : data) {
                imageList.add((Image) context.deserialize(element, Image.class));
            }
            return imageList;
        }
    }
}
