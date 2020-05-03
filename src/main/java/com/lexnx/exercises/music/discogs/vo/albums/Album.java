package com.lexnx.exercises.music.discogs.vo.albums;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "thumb",
        "title",
        "uri",
        "master_url",
        "cover_image",
        "resource_url",
        "master_id",
        "type",
        "id"
})
//TODO: make this object immutable
@Data
public class Album {

    @JsonProperty("thumb")
    private String thumb;
    @JsonProperty("title")
    private String title;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("master_url")
    private Object masterUrl;
    @JsonProperty("cover_image")
    private String coverImage;
    @JsonProperty("resource_url")
    private String resourceUrl;
    @JsonProperty("master_id")
    private Object masterId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("id")
    private Integer id;

}