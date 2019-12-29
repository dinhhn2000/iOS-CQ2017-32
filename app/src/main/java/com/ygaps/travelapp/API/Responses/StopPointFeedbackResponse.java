package com.ygaps.travelapp.API.Responses;

import com.ygaps.travelapp.utils.StopPointFeedback;

import java.util.ArrayList;

public class StopPointFeedbackResponse {
    private ArrayList<StopPointFeedback> feedbackList;

    public StopPointFeedbackResponse(ArrayList<StopPointFeedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    @Override
    public String toString() {
        return "StopPointFeedbackResponse{" +
                "feedbackList=" + feedbackList +
                '}';
    }

    public ArrayList<StopPointFeedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(ArrayList<StopPointFeedback> feedbackList) {
        this.feedbackList = feedbackList;
    }
}
