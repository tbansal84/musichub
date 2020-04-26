package com.lexnx.exercises.music.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.UUID;

// the annotation generates private and final fields
@Value
@AllArgsConstructor
public class Album {
    UUID albumId;
    String genre;
    String title;
    Long releaseYear;
    List<Track> tracks;



}
