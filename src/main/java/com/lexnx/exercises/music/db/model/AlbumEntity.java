package com.lexnx.exercises.music.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
@Data
public class AlbumEntity {
    //TODO: set uniqueconstaint
    @Id
    private UUID albumId;

    private String title;
    @JsonIgnore
    private ArtistEntity artist;

    private String genre;

    private Long releaseYear;


}
