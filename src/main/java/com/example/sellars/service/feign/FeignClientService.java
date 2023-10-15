package com.example.sellars.service.feign;

import com.example.sellars.exeption.FeignClientException;
import com.example.sellars.model.Comment;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@Slf4j
@Qualifier("FeignClientService")
@RequiredArgsConstructor
public class FeignClientService {
    private final CustomClient client;

    public List<Comment> getAll(Long userId) {
        try {
            return client.getAllComments(userId);
        } catch (FeignException ex) {
            return Collections.emptyList();
        }
    }


    public List<Comment> getMyComments(Long authorId) {
        try {
            return client.getMyComments(authorId);
        } catch (FeignException ex) {
            return Collections.emptyList();
        }
    }


    public List<Comment> getSortComment(Long id, Integer offset,
                                        Integer limit, Integer sortField, Integer sortPos) {
        try {
            return client.getSortComment(offset, limit, sortField, sortPos, id);
        } catch (FeignException ex) {
            return Collections.emptyList();
        }
    }


    public void save(Long userId, Long authorId, Comment comment) {
        HttpStatus entity = client.saveComment(userId, authorId, comment);
        if (entity != HttpStatus.ACCEPTED) {
            throw new FeignClientException("Не удалось сохранить комментарий");
        }
    }


    public void updateComment(Long userId, Long authorId, Comment comment) {
        HttpStatus entity = client.updateComment(userId, authorId, comment);
        if (entity != HttpStatus.ACCEPTED) {
            throw new FeignClientException("Не удалось обновить комментарий");
        }
    }


    public void deleteComment(Long userId, Long authorId) {
        HttpStatus entity = client.deleteById(userId, authorId);
        if (entity != HttpStatus.ACCEPTED) {
            throw new FeignClientException("Не удалось удалить комментарий");
        }
    }


    public double getUserRate(Long id) {
        try {
            List<Comment> comments = client.getAllComments(id);
            return (double) Math.round(comments.stream().mapToDouble(Comment::getRating).average().orElse(0) * 10) / 10;
        } catch (FeignException e) {
            return 0;
        }
    }
}
