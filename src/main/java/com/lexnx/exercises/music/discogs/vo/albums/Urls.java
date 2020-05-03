package com.lexnx.exercises.music.discogs.vo.albums;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "last",
        "next"
})
//TODO: make this object immutable
@Data
public class Urls {

    @JsonProperty("last")
    private String last;
    @JsonProperty("next")
    private String next;

}