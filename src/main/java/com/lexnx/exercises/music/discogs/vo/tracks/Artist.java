
package com.lexnx.exercises.music.discogs.vo.tracks;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "join",
    "name",
    "anv",
    "tracks",
    "thumbnail_url",
    "role",
    "resource_url",
    "id"
})
//TODO: make this object immutable
@Data
public class Artist {

    @JsonProperty("join")
    private String join;
    @JsonProperty("name")
    private String name;
    @JsonProperty("anv")
    private String anv;
    @JsonProperty("tracks")
    private String tracks;
    @JsonProperty("thumbnail_url")
    private String thumbnailUrl;
    @JsonProperty("role")
    private String role;
    @JsonProperty("resource_url")
    private String resourceUrl;
    @JsonProperty("id")
    private Integer id;


}
