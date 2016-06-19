package com.cofradias.android.model.object;

import java.io.Serializable;

/**
 * Created by alaria on 27/04/2016.
 */
public class ImagenGaleria implements Serializable {

    private String caption, id_cofradia, image, thumbnail;


    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getId_cofradia() {
        return id_cofradia;
    }

    public void setId_cofradia(String id_cofradia) {
        this.id_cofradia = id_cofradia;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


    public ImagenGaleria() {
    }

}
