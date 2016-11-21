package com.mauri.android.flickrexample.network.deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mauri.android.flickrexample.app.Constants;
import com.mauri.android.flickrexample.models.Owner;
import com.mauri.android.flickrexample.models.Photo;
import com.mauri.android.flickrexample.network.responses.GetPhotoInfoResponse;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by mauri on 20/11/16.
 */

public class PhotoJsonDeserializer implements JsonDeserializer<GetPhotoInfoResponse> {
    @Override
    public GetPhotoInfoResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject obj = json.getAsJsonObject();
        Photo photo = new Photo();
        JsonObject jsonPhoto = obj.getAsJsonObject("photo");
//      Owner gets deserialized in full flow, as it is properly serialized from BE and modelled here.
        photo.setFull_owner(((Owner) context.deserialize(jsonPhoto.getAsJsonObject("owner"), Owner.class)));
        photo.setTitle(jsonPhoto.getAsJsonObject("title").get("_content").getAsString());
        photo.setDescription(jsonPhoto.getAsJsonObject("description").get("_content").getAsString());
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.FLICKR_DATE_FORMAT);
        try {
            photo.setPhoto_date(sdf.parse(jsonPhoto.getAsJsonObject("dates").get("taken").getAsString()));
        } catch (ParseException ignored) {
        }

        GetPhotoInfoResponse response = new GetPhotoInfoResponse();
        response.setPhoto(photo);
        response.setStat(obj.get("stat").getAsString());
        return response;
    }
}
