package com.lexnx.exercises.music.rest.model;

import com.lexnx.exercises.music.db.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.UUID;

// the annotation generates private and final fields
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album {
    UUID albumId;
    Genre genre;
    String title;
    String releaseYear;
}
