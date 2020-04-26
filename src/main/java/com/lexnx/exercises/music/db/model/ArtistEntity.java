package com.lexnx.exercises.music.db.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
