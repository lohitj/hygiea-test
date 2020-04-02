    package com.capitalone.dashboard.collector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;

import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.PivotalTrackerCollector;
import com.capitalone.dashboard.repository.FeatureRepository;
import com.capitalone.dashboard.repository.PivotalTrackerCollectorRepository;

/**
     * Collects Features from Pivotal Tracker
     */
    public class PivotalTrackerCollectorTask extends CollectorTask<PivotalTrackerCollector> {

        private final FeatureRepository featureRepository;
        private final PivotalTrackerCollectorRepository pivotalTrackerCollectorRepository;

        @Value("${cron}") // Injected from application.properties
        private String cron;

        @Value("${apiToken}") // Injected from application.properties
        private String apiToken;

        @Autowired
        public PivotalTrackerCollectorTask(TaskScheduler taskScheduler,
                                            FeatureRepository featureRepository,
                                            PivotalTrackerCollectorRepository pivotalTrackerCollectorRepository) {
            super(taskScheduler, "Pivotal Tracker");
            this.featureRepository = featureRepository;
            this.pivotalTrackerCollectorRepository = pivotalTrackerCollectorRepository;
        }

        @Override
        public PivotalTrackerCollector getCollector() {

            PivotalTrackerCollector collector = new PivotalTrackerCollector();

            collector.setName("Pivotal Tracker"); // Must be unique to all collectors for a given Dashboard Application instance
            collector.setCollectorType(CollectorType.Feature);
            collector.setEnabled(true);
            collector.setApiToken(apiToken);

            return collector;
        }

        @Override
        public BaseCollectorRepository<PivotalTrackerCollectorRepository> getCollectorRepository() {
            return pivotalTrackerCollectorRepository;
        }

        @Override
        public String getCron() {
            return cron;
        }

        @Override
        public void collect(PivotalTrackerCollector collector) {

            // Collector logic
            PivotalTrackerApi api = new PivotalTrackerApi(collector.getApiToken());

            for (Project project : api.getProjects()) {

                PivotalTrackerCollectorItem collectorItem = getOrCreateCollectorItems(project.getProjectId());

                // Naive implementation
                deleteFeaturesFor(collectorItem);

                addFeaturesFor(collectorItem, project.getStories());
            }
        }

        private PivotalTrackerCollectorItem getOrCreateCollectorItem(long projectId) {
            // ...
        }

        private void deleteFeaturesFor(PivotalTrackerCollectorItem collectorItem) {
            // ...
        }

        private void addFeaturesFor(PivotalTrackerCollectorItem collectorItem, List<Story> stories) {
            // ...
        }
    }