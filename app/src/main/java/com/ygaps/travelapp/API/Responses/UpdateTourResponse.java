package com.ygaps.travelapp.API.Responses;

public class UpdateTourResponse {
        private String hostId;
        private int status;
        private String name;
        private long minCost;
        private long maxCost;
        private long startDate;
        private long endDate;
        private long adults;
        private long childs;
        private String id;
        private boolean isPrivate;
        private String avatar;

        public UpdateTourResponse(String hostId, int status,String name, long minCost, long maxCost, long startDate, long endDate, long adults, long childs, String id, boolean isPrivate, String avatar) {
            this.id = id;
            this.name = name;
            this.startDate = startDate;
            this.endDate = endDate;
            this.adults = adults;
            this.childs = childs;
            this.minCost = minCost;
            this.maxCost = maxCost;
            this.isPrivate = isPrivate;
            this.avatar = avatar;
            this.status = status;
        }

        public String getHostId() {
            return hostId;
        }
        public void setHostId(String hostId) {
            this.hostId = hostId;
        }

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public long getStartDate() {
            return startDate;
        }
        public void setStartDate(long startDate) {
            this.startDate = startDate;
        }

        public long getEndDate() {
            return endDate;
        }
        public void setEndDate(long endDate) {
            this.endDate = endDate;
        }

        public long getAdults() {
            return adults;
        }
        public void setAdults(long adults) {
            this.adults = adults;
        }

        public long getChilds() {
            return childs;
        }
        public void setChilds(long childs) {
            this.childs = childs;
        }

        public long getMinCost() {
            return minCost;
        }
        public void setMinCost(long minCost) {
            this.minCost = minCost;
        }

        public long getMaxCost() {
            return maxCost;
        }
        public void setMaxCost(long maxCost) {
            this.maxCost = maxCost;
        }

        public boolean getPrivate() {
            return isPrivate;
        }
        public void setPrivate(boolean isPrivate) {
            this.isPrivate = isPrivate;
        }

        public String getAvatar() {
            return avatar;
        }
        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getStatus() {
            return status;
        }
        public void setStatus(int status) {
            this.status = status;
        }
}
