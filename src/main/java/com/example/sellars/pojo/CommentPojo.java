package com.example.sellars.pojo;

import com.example.sellars.models.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Data
public class CommentPojo {
    Comment comment;
    List<MultipartFile> files;

    public CommentPojo(@JsonProperty("comment") Comment comment, @JsonProperty("files") List<MultipartFile> files) {
        this.comment = comment;
        this.files = files;
    }
}
