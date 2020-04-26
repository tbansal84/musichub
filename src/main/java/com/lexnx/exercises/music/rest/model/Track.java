package com.lexnx.exercises.music.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Track {

    private String position;
    private String title;

}