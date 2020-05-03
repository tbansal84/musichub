package com.lexnx.exercises.music.discogs.vo.albums;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "per_page",
        "items",
        "page",
        "urls",
        "pages"
})
//TODO: make this object immutable
@Data
public class Pagination {

    @JsonProperty("per_page")
    private Integer perPage;
    @JsonProperty("items")
    private Integer items;
    @JsonProperty("page")
    private Integer page;
    @JsonProperty("urls")
    private Urls urls;
    @JsonProperty("pages")
    private Integer pages;

}