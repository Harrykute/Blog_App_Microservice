package com.blogapp.likeservice.kafka.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.blogapp.likeservice.dto.LikeCountChangedEvent;
import com.blogapp.likeservice.dto.LikeEvent;
import com.blogapp.likeservice.entity.Like;
import com.blogapp.likeservice.repository.LikeRepository;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Consumer = Kafka Listener
 */
@Configuration
public class LikeEventConsumer {

	@Autowired
	private LikeRepository likeRepo;
	
	@Autowired
	private StreamBridge streamBridge;
//    @Bean
//    public Consumer<LikeEvent> likeEventConsumerMethod() {
//
//        return event -> {
//        	 System.out.println("✅ LIKE SERVICE RECEIVED EVENT:");
//             System.out.println(event);
//             System.out.println(event.getPostId());
//             System.out.println(event.getUserId());
//             System.out.println(event.getAction());
//             System.out.println(event.getTimestamp());
//            // TODO:
//            // 1. Save like in DB
//             
//             
//             
//            // 2. Increase like count
//            // 3. Prevent duplicate likes
//        };
        
        @Bean
        public Consumer<LikeEvent> likeEventConsumerMethod() {

            return event -> {

                Optional<Like> existingLike =
                    likeRepo.findByPostIdAndUserId(
                        event.getPostId(),
                        event.getUserId()
                    );

                if (event.getAction().equalsIgnoreCase("LIKE")) {

                    if (existingLike.isPresent()) {
                        // Already liked → ignore (idempotent)
                        return;
                    }

                    Like like = new Like();
                    like.setPostId(event.getPostId());
                    like.setUserId(event.getUserId());
                    like.setLiked(true);

                    likeRepo.save(like);
                }
                if (event.getAction().equalsIgnoreCase("UNLIKE")) {

                    existingLike.ifPresent(likeRepo::delete);
                }
                long count = likeRepo.countByPostId(event.getPostId());

                LikeCountChangedEvent countEvent =
                        new LikeCountChangedEvent(event.getPostId(), (int) count);

                // publish to Kafka
                streamBridge.send("like-count-out-0", countEvent);
            };
        } 
}

