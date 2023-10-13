package com.example.sellars.service;

import com.example.sellars.feign.CustomClient;
import com.example.sellars.models.Comment;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Qualifier("feign")
@Service
@RequiredArgsConstructor
public class FeignClientImpl {
    private final CustomClient client;

    public List<Comment> getAll(Long userId){
        try {
            return client.getAllComments(userId);
        }catch (FeignException ex){
            return Collections.emptyList();
        }
    }

    public List<Comment> getMyComments(Long authorId){
        try {
            return client.getMyComments(authorId);
        }catch (FeignException ex){
            return Collections.emptyList();
        }
    }


    public List<Comment> getSortProd(Long id, Integer offset, Integer limit, Integer sortField, Integer sortPos){
        return client.getSortComment(offset, limit, sortField, sortPos, id);
    }


    public void save(Long userId, Long authorId, Comment comment){
        client.saveComment(userId, authorId, comment);
    }


    public void updateComment(Long userId, Long authorId, Comment comment){
        client.updateComment(userId, authorId, comment);
    }


    public void deleteComment(Long userId, Long authorId){
        client.deleteById(userId, authorId);
    }


    public double getUserRate(Long id){
        try {
            List<Comment> comments =client.getAllComments(id);
            return (double) Math.round(comments.stream().mapToDouble(Comment::getRating).average().orElse(0) * 10) /10;
        }catch (FeignException e){
            return 0;
        }
    }
}
