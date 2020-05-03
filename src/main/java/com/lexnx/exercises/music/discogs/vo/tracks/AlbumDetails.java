
package com.lexnx.exercises.music.discogs.vo.tracks;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "styles",
    "genres",
    "num_for_sale",
    "title",
    "most_recent_release",
    "main_release",
    "main_release_url",
    "year",
    "uri",
    "versions_url",
    "tracklist",
    "most_recent_release_url",
    "artists",
    "images",
    "resource_url",
    "lowest_price",
    "id",
    "data_quality"
})
//TODO: make this object immutable
@Data
public class AlbumDetails {

    @JsonProperty("styles")
    private List<String> styles = null;
    @JsonProperty("genres")
    private List<String> genres = null;
    @JsonProperty("num_for_sale")
    private Integer numForSale;
    @JsonProperty("title")
    private String title;
    @JsonProperty("most_recent_release")
    private Integer mostRecentRelease;
    @JsonProperty("main_release")
    private Integer mainRelease;
    @JsonProperty("main_release_url")
    private String mainReleaseUrl;
    @JsonProperty("year")
    private Integer year;
    @JsonProperty("uri")
    private String uri;
    @JsonProperty("versions_url")
    private String versionsUrl;
    @JsonProperty("tracklist")
    private List<Tracklist> tracklist = null;
    @JsonProperty("most_recent_release_url")
    private String mostRecentReleaseUrl;
    @JsonProperty("artists")
    private List<Artist> artists = null;
    @JsonProperty("images")
    private List<Object> images = null;
    @JsonProperty("resource_url")
    private String resourceUrl;
    @JsonProperty("lowest_price")
    private Double lowestPrice;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("data_quality")
    private String dataQuality;


}
