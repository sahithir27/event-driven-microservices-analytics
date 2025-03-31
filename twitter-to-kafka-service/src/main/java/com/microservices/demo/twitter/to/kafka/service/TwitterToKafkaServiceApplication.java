package com.microservices.demo.twitter.to.kafka.service;

import com.microservices.demo.config.TwitterToKafkaServiceConfigData;
import com.microservices.demo.twitter.to.kafka.service.init.StreamInitializer;
import com.microservices.demo.twitter.to.kafka.service.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;


@SpringBootApplication
@ComponentScan(basePackages = "com.microservices.demo")
public class TwitterToKafkaServiceApplication implements CommandLineRunner {
    private static final Logger LOG= LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);
    //private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;
    private final StreamInitializer streamInitializer;
    private final StreamRunner streamRunner;

    public TwitterToKafkaServiceApplication(StreamInitializer streamInitializer, StreamRunner streamRunner) {
        this.streamInitializer = streamInitializer;
        this.streamRunner = streamRunner;
    }
//    public  TwitterToKafkaServiceApplication(TwitterToKafkaServiceConfigData config, StreamRunner streamRunner){
//        this.twitterToKafkaServiceConfigData=config;
//        this.streamRunner=streamRunner;
//    }

    public static void main(String[] args){
        SpringApplication.run(TwitterToKafkaServiceApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("Application Started");
        //LOG.info(Arrays.toString(twitterToKafkaServiceConfigData.getTwitterKeywords().toArray(new String[] {})));
        //LOG.info("Keywords: {}", twitterToKafkaServiceConfigData.getTwitterKeywords());
        //LOG.info(twitterToKafkaServiceConfigData.getWelcomeMessage());
        streamInitializer.init();
        streamRunner.start();
    }
}
