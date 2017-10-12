package com.panlei.web.dao;

import com.panlei.web.model.Upvote;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 361pa on 2017/8/12.
 */
@Repository
public interface UpvoteMapper {
    int insert(Upvote upvote);
    List<Upvote> selectUpvoteByTopicID(@Param("topicID") String topicID);
    Upvote selectUpvoteByWebchatIdAndTopicIdAndFloorID(@Param("webchatID") String webchatID, @Param("topicID") String topicID, @Param("floorID") String floorID);
    int deleteUpvoteByWebchatIdAndTopicIdAndFloorID(@Param("webchatID") String webchatID, @Param("topicID") String topicID, @Param("floorID") String floorID);
}
