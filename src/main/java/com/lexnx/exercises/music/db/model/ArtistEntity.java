package com.lexnx.exercises.music.db.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class ArtistEntity {
    //TODO: set uniqueconstaint
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID artistId;
    @NotEmpty
    private String artistName;
    @OneToMany
    private Set<AlbumEntity> albums = new HashSet<>();
}
