package com.lexnx.exercises.music.db.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Document
@Data
public class ArtistEntity {
    //TODO: set uniqueconstaint
    @Id
    private UUID artistId;
    private String artistName;
    @JsonIgnore
    private Set<AlbumEntity> albums = new HashSet<>();
}
