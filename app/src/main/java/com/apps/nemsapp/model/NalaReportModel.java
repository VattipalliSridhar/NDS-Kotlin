package com.apps.nemsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NalaReportModel {

    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }


    public class Detail {

        @SerializedName("NallaLocation")
        @Expose
        private String nallaLocation;
        @SerializedName("DateTime")
        @Expose
        private String dateTime;
        @SerializedName("today_total_qty")
        @Expose
        private String todayTotalQty;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("image")
        @Expose
        private String image;

        public String getNallaLocation() {
            return nallaLocation;
        }

        public void setNallaLocation(String nallaLocation) {
            this.nallaLocation = nallaLocation;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getTodayTotalQty() {
            return todayTotalQty;
        }

        public void setTodayTotalQty(String todayTotalQty) {
            this.todayTotalQty = todayTotalQty;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }
}
