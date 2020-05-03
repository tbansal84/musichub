
package com.lexnx.exercises.music.discogs.vo.tracks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "position",
    "title"
})
//TODO: make this object immutable
@Data
public class Tracklist {

    @JsonProperty("position")
    private String position;
    @JsonProperty("title")
    private String title;


}
