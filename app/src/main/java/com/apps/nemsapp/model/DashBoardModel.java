package com.apps.nemsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashBoardModel {
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

        @SerializedName("status_id")
        @Expose
        private Integer statusId;
        @SerializedName("status_description")
        @Expose
        private String statusDescription;
        @SerializedName("status_icon")
        @Expose
        private String statusIcon;
        @SerializedName("status_count")
        @Expose
        private Integer statusCount;

        public String getStatusColorCode() {
            return statusColorCode;
        }

        public void setStatusColorCode(String statusColorCode) {
            this.statusColorCode = statusColorCode;
        }

        @SerializedName("status_color_code")
        @Expose
        private String statusColorCode;


        public Integer getStatusId() {
            return statusId;
        }

        public void setStatusId(Integer statusId) {
            this.statusId = statusId;
        }

        public String getStatusDescription() {
            return statusDescription;
        }

        public void setStatusDescription(String statusDescription) {
            this.statusDescription = statusDescription;
        }

        public String getStatusIcon() {
            return statusIcon;
        }

        public void setStatusIcon(String statusIcon) {
            this.statusIcon = statusIcon;
        }

        public Integer getStatusCount() {
            return statusCount;
        }

        public void setStatusCount(Integer statusCount) {
            this.statusCount = statusCount;
        }

    }
}
