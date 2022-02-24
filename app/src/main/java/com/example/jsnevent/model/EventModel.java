package com.example.jsnevent.model;

import android.graphics.drawable.Drawable;
import android.media.Image;

import java.nio.charset.StandardCharsets;

public class EventModel {

private String eventName;
private String date;
private String venue;
private String description;
private Image imagePicker;
private Image imageLocation;

    public Image getImagePicker() {
        return imagePicker;
    }

    public Image setImagePicker(Image imagePicker) {
        this.imagePicker = imagePicker;
        return imagePicker;
    }

    public Image getImageLocation() {
        return imageLocation;
    }

    public Image setImageLocation(Image imageLocation) {
        this.imageLocation = imageLocation;
        return imageLocation;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
