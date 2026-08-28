package com.blogapp.postservice.kafaka.producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.blogapp.postservice.kafaka.event.LikeEvent;

import java.util.function.Supplier;

/**
 * Supplier = PRODUCER
 */
@Configuration
public class LikeEventProducer {

//    @Bean
//    public Supplier<LikeEvent> likeEventSupplier() {
//        return () -> {
//        	System.out.println("sending");
//        	return null;
//        }; 
//        // Event will be pushed manually via StreamBridge
//    }
}
