package com.ygaps.travelapp.API;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ygaps.travelapp.utils.Notification;
import com.ygaps.travelapp.utils.Tour;

import java.util.ArrayList;

public class NotificationListResponse {

    @SerializedName("tours")
    @Expose
    private ArrayList<Notification> Notification;

    public ArrayList<Notification> getNotification() {
        return Notification;
    }
}
