package com.ygaps.travelapp.API.Requests;

public class NotificationListRequest {
    private String tourId;
    private int pageIndex;
    private String pageSize;

    public NotificationListRequest(String tourId, int pageIndex, String pageSize) {
        this.tourId = tourId;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public String getTourId() {
        return tourId;
    }
    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public int getPageIndex() {
        return pageIndex;
    }
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPageSize() {
        return pageSize;
    }
    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

}
