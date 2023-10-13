package com.example.sellars.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {
    private Long id;
    private Long userId;
    private Long authorId;
    private Double rating;
    private String description;
    private LocalDateTime timeOfCreated;
}
