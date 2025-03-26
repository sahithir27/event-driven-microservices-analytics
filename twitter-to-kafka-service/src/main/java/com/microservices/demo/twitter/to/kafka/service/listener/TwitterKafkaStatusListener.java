package com.microservices.demo.twitter.to.kafka.service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Component
public class TwitterKafkaStatusListener extends StatusAdapter {
    private static final Logger LOG= LoggerFactory.getLogger(TwitterKafkaStatusListener.class);
    @Override
    public void onStatus(Status status){
        System.out.println(">> Tweet: " + status.getText());
        LOG.info("Twitter status with text {}", status.getText());

    }
    @Override
    public void onException(Exception ex) {
        ex.printStackTrace(); // Just in case there's a silent failure
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
        System.out.println("⚠️ Tweets dropped: " + numberOfLimitedStatuses);
    }

}
