package com.capitalone.dashboard.model;

    import com.capitalone.dashboard.model.Collector;

    /**
     * Collect Features from PivotalTracker
     */
    public class PivotalTrackerCollector extends Collector {
        private String apiToken;

        public String getApiToken() {
            return apiToken;
        }

        public void setApiToken(String apiToken) {
            this.apiToken = apiToken;
        }
    }