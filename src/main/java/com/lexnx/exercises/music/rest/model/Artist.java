package com.lexnx.exercises.music.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.UUID;

// the annotation generates private and final fields
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
    UUID artistId;
    String artistName;
}
