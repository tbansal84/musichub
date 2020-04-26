package com.lexnx.exercises.music.db.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Entity
public class AlbumEntity {
    //TODO: set uniqueconstaint
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID albumId;

    @NotEmpty
    private String title;

    @ManyToOne
    @NotNull
    private ArtistEntity artist;

    @NotEmpty
    private String genre;

    @NotNull
    private Long releaseYear;


}
