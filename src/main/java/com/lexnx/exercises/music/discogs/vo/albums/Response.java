package com.lexnx.exercises.music.discogs.vo.albums;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "pagination",
        "results"
})
//TODO: make this object immutable
@Data
public class Response {

    @JsonProperty("pagination")
    private Pagination pagination;
    @JsonProperty("results")
    private List<Album> results = null;


}
