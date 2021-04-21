package com.apps.nemsapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NalaListModel {

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
        @SerializedName("Workno")
        @Expose
        private String workno;
        @SerializedName("NallaLocation")
        @Expose
        private String nallaLocation;
        @SerializedName("From")
        @Expose
        private String from;
        @SerializedName("To")
        @Expose
        private String to;
        @SerializedName("NallaId")
        @Expose
        private Integer nallaId;
        @SerializedName("ZoneId")
        @Expose
        private Integer zoneId;
        @SerializedName("CirclId")
        @Expose
        private Integer circlId;
        @SerializedName("WardId")
        @Expose
        private Integer wardId;
        @SerializedName("zone_name")
        @Expose
        private String zoneName;
        @SerializedName("circle_name")
        @Expose
        private String circleName;
        @SerializedName("WardName")
        @Expose
        private String wardName;
        @SerializedName("total_qty")
        @Expose
        private String totalQty;
        @SerializedName("yesterday_total_qty")
        @Expose
        private String yesterdayTotalQty;
        @SerializedName("nalla_progress")
        @Expose
        private String nallaProgress;
        @SerializedName("Status")
        @Expose
        private Integer status;
        @SerializedName("StatusDescription")
        @Expose
        private String statusDescription;
        @SerializedName("imgUrl")
        @Expose
        private String imgUrl;

        @SerializedName("Workid")
        @Expose
        private String Workid;

        public String getWorkno() {
            return workno;
        }

        public void setWorkno(String workno) {
            this.workno = workno;
        }

        public String getNallaLocation() {
            return nallaLocation;
        }

        public void setNallaLocation(String nallaLocation) {
            this.nallaLocation = nallaLocation;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public Integer getNallaId() {
            return nallaId;
        }

        public void setNallaId(Integer nallaId) {
            this.nallaId = nallaId;
        }

        public Integer getZoneId() {
            return zoneId;
        }

        public void setZoneId(Integer zoneId) {
            this.zoneId = zoneId;
        }

        public Integer getCirclId() {
            return circlId;
        }

        public void setCirclId(Integer circlId) {
            this.circlId = circlId;
        }

        public Integer getWardId() {
            return wardId;
        }

        public void setWardId(Integer wardId) {
            this.wardId = wardId;
        }

        public String getZoneName() {
            return zoneName;
        }

        public void setZoneName(String zoneName) {
            this.zoneName = zoneName;
        }

        public String getCircleName() {
            return circleName;
        }

        public void setCircleName(String circleName) {
            this.circleName = circleName;
        }

        public String getWardName() {
            return wardName;
        }

        public void setWardName(String wardName) {
            this.wardName = wardName;
        }

        public String getTotalQty() {
            return totalQty;
        }

        public void setTotalQty(String totalQty) {
            this.totalQty = totalQty;
        }

        public String getYesterdayTotalQty() {
            return yesterdayTotalQty;
        }

        public void setYesterdayTotalQty(String yesterdayTotalQty) {
            this.yesterdayTotalQty = yesterdayTotalQty;
        }

        public String getNallaProgress() {
            return nallaProgress;
        }

        public void setNallaProgress(String nallaProgress) {
            this.nallaProgress = nallaProgress;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getStatusDescription() {
            return statusDescription;
        }

        public void setStatusDescription(String statusDescription) {
            this.statusDescription = statusDescription;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getWorkid() {
            return Workid;
        }

        public void setWorkid(String workid) {
            Workid = workid;
        }
    }

}
