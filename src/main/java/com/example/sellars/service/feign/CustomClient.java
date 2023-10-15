package com.example.sellars.service.feign;

import com.example.sellars.model.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@FeignClient(value = "CustomClient", url = "http://localhost:8080/comments")
public interface CustomClient {

    @RequestMapping(method = RequestMethod.GET, value = "/user/{id}/sort", consumes = APPLICATION_JSON_VALUE)
    List<Comment> getSortComment(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                 @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                 @RequestParam(value = "sort", defaultValue = "1") Integer sortField,
                                 @RequestParam(value = "pos", defaultValue = "2") Integer sortPos,
                                 @PathVariable Long id);

    @RequestMapping(method = GET, value = "/user/{id}", consumes = APPLICATION_JSON_VALUE)
    List<Comment> getAllComments(@PathVariable Long id);

    @RequestMapping(method = GET, value = "/my/{id}", consumes = APPLICATION_JSON_VALUE)
    List<Comment> getMyComments(@PathVariable Long id);


    @RequestMapping(method = POST, value = "/user/{id}/save/{authorId}", produces = "application/json")
    HttpStatus saveComment(@PathVariable("id") Long id,
                           @PathVariable("authorId") Long authorId,
                           @RequestBody Comment comment);


    @RequestMapping(method = PUT, value = "/user/{id}/update/{authorId}", produces = "application/json")
    HttpStatus updateComment(@PathVariable("id") Long id,
                             @PathVariable("authorId") Long authorId,
                             @RequestBody Comment comment);


    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{id}/delete/{authorId}", produces = "application/json")
    HttpStatus deleteById(@PathVariable("id") Long id,
                          @PathVariable("authorId") Long authorId);

}
