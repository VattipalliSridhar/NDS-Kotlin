package com.apps.nemsapp.view.ui.java.view;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class NalaDailyModel implements Parcelable {
    @SerializedName("details")
    @Expose
    private ArrayList<Detail> details = null;
    @SerializedName("status_code")
    @Expose
    private Integer statusCode;
    @SerializedName("status_message")
    @Expose
    private String statusMessage;

    protected NalaDailyModel(Parcel in) {
        if (in.readByte() == 0) {
            statusCode = null;
        } else {
            statusCode = in.readInt();
        }
        statusMessage = in.readString();
    }


    public static final Creator<NalaDailyModel> CREATOR = new Creator<NalaDailyModel>() {
        @Override
        public NalaDailyModel createFromParcel(Parcel in) {
            return new NalaDailyModel(in);
        }

        @Override
        public NalaDailyModel[] newArray(int size) {
            return new NalaDailyModel[size];
        }
    };

    public ArrayList<Detail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<Detail> details) {
        this.details = details;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (statusCode == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(statusCode);
        }
        dest.writeString(statusMessage);
    }


    public static class Detail implements Parcelable{

        @SerializedName("Workno")
        @Expose
        private String workno;
        @SerializedName("Workid")
        @Expose
        private Integer workid;
        @SerializedName("NallaLocation")
        @Expose
        private String nallaLocation;
        @SerializedName("FromTo")
        @Expose
        private ArrayList<FromTo> fromTo = null;
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
        @SerializedName("today_total_qty")
        @Expose
        private String todayTotalQty;
        @SerializedName("total_length_today")
        @Expose
        private String totalLengthToday;
        @SerializedName("total_width_today")
        @Expose
        private String totalWidthToday;
        @SerializedName("total_depth_today")
        @Expose
        private String totalDepthToday;
        @SerializedName("nalla_progress")
        @Expose
        private String nallaProgress;
        @SerializedName("Status")
        @Expose
        private Integer status;
        @SerializedName("StatusDescription")
        @Expose
        private String statusDescription;

        protected Detail(Parcel in) {
            workno = in.readString();
            if (in.readByte() == 0) {
                workid = null;
            } else {
                workid = in.readInt();
            }
            nallaLocation = in.readString();
            if (in.readByte() == 0) {
                zoneId = null;
            } else {
                zoneId = in.readInt();
            }
            if (in.readByte() == 0) {
                circlId = null;
            } else {
                circlId = in.readInt();
            }
            if (in.readByte() == 0) {
                wardId = null;
            } else {
                wardId = in.readInt();
            }
            zoneName = in.readString();
            circleName = in.readString();
            wardName = in.readString();
            totalQty = in.readString();
            todayTotalQty = in.readString();
            totalLengthToday = in.readString();
            totalWidthToday = in.readString();
            totalDepthToday = in.readString();
            nallaProgress = in.readString();
            if (in.readByte() == 0) {
                status = null;
            } else {
                status = in.readInt();
            }
            statusDescription = in.readString();
        }

        public static final Creator<Detail> CREATOR = new Creator<Detail>() {
            @Override
            public Detail createFromParcel(Parcel in) {
                return new Detail(in);
            }

            @Override
            public Detail[] newArray(int size) {
                return new Detail[size];
            }
        };

        public String getWorkno() {
            return workno;
        }

        public void setWorkno(String workno) {
            this.workno = workno;
        }

        public Integer getWorkid() {
            return workid;
        }

        public void setWorkid(Integer workid) {
            this.workid = workid;
        }

        public String getNallaLocation() {
            return nallaLocation;
        }

        public void setNallaLocation(String nallaLocation) {
            this.nallaLocation = nallaLocation;
        }

        public ArrayList<FromTo> getFromTo() {
            return fromTo;
        }

        public void setFromTo(ArrayList<FromTo> fromTo) {
            this.fromTo = fromTo;
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

        public String getTodayTotalQty() {
            return todayTotalQty;
        }

        public void setTodayTotalQty(String todayTotalQty) {
            this.todayTotalQty = todayTotalQty;
        }

        public String getTotalLengthToday() {
            return totalLengthToday;
        }

        public void setTotalLengthToday(String totalLengthToday) {
            this.totalLengthToday = totalLengthToday;
        }

        public String getTotalWidthToday() {
            return totalWidthToday;
        }

        public void setTotalWidthToday(String totalWidthToday) {
            this.totalWidthToday = totalWidthToday;
        }

        public String getTotalDepthToday() {
            return totalDepthToday;
        }

        public void setTotalDepthToday(String totalDepthToday) {
            this.totalDepthToday = totalDepthToday;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(workno);
            if (workid == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(workid);
            }
            dest.writeString(nallaLocation);
            if (zoneId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(zoneId);
            }
            if (circlId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(circlId);
            }
            if (wardId == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(wardId);
            }
            dest.writeString(zoneName);
            dest.writeString(circleName);
            dest.writeString(wardName);
            dest.writeString(totalQty);
            dest.writeString(todayTotalQty);
            dest.writeString(totalLengthToday);
            dest.writeString(totalWidthToday);
            dest.writeString(totalDepthToday);
            dest.writeString(nallaProgress);
            if (status == null) {
                dest.writeByte((byte) 0);
            } else {
                dest.writeByte((byte) 1);
                dest.writeInt(status);
            }
            dest.writeString(statusDescription);
        }

        public static class FromTo implements Parcelable{

            @SerializedName("From")
            @Expose
            private String from;
            @SerializedName("To")
            @Expose
            private String to;

            protected FromTo(Parcel in) {
                from = in.readString();
                to = in.readString();
            }

            public static final Creator<FromTo> CREATOR = new Creator<FromTo>() {
                @Override
                public FromTo createFromParcel(Parcel in) {
                    return new FromTo(in);
                }

                @Override
                public FromTo[] newArray(int size) {
                    return new FromTo[size];
                }
            };

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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(from);
                dest.writeString(to);
            }
        }

    }

}
