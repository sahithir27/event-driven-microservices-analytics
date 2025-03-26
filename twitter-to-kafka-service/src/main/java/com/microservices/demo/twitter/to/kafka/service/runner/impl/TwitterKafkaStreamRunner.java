package com.microservices.demo.twitter.to.kafka.service.runner.impl;

import com.microservices.demo.twitter.to.kafka.service.config.TwitterToKafkaServiceConfigData;
import com.microservices.demo.twitter.to.kafka.service.listener.TwitterKafkaStatusListener;
import com.microservices.demo.twitter.to.kafka.service.runner.StreamRunner;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import javax.annotation.PreDestroy;
import java.util.Arrays;

@Component
@ConditionalOnExpression("not ${twitter-to-kafka-service.enable-v2-tweets} && ${twitter-to-kafka-service.enable-mock-tweets}")
public class TwitterKafkaStreamRunner implements StreamRunner {
    private static final Logger LOG= org.slf4j.LoggerFactory.getLogger(TwitterKafkaStreamRunner.class);
    private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;
    private final TwitterKafkaStatusListener twitterKafkaStatusListener;
    private TwitterStream twitterStream;
    public TwitterKafkaStreamRunner(TwitterToKafkaServiceConfigData config, TwitterKafkaStatusListener listener){
        this.twitterToKafkaServiceConfigData=config;
        this.twitterKafkaStatusListener=listener;

    }
    @Override
    public void start() throws TwitterException {
        twitterStream=new TwitterStreamFactory().getInstance();
        twitterStream.addListener(twitterKafkaStatusListener);
        addFilter();
    }

    @PreDestroy
    public void shutdown(){
        if(twitterStream!=null){
            LOG.info("Closing twitter stream");
            twitterStream.shutdown();
        }
    }

    private void addFilter() {
        String[] keywords=twitterToKafkaServiceConfigData.getTwitterKeywords().toArray(new String[0]);
        FilterQuery filterQuery=new FilterQuery(keywords);
        twitterStream.filter(filterQuery);
        //twitterStream.sample();
        LOG.info("Started filtering Twitter Stream data for keywords: {}", Arrays.toString(keywords));
    }
}
