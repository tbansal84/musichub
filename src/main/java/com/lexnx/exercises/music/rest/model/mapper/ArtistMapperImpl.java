package com.lexnx.exercises.music.rest.model.mapper;

import com.lexnx.exercises.music.db.model.ArtistEntity;
import com.lexnx.exercises.music.rest.model.Artist;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;

@Component
public class ArtistMapperImpl implements ArtistMapper {

    @Override
    public ArtistEntity modelToEntity(Artist source) {
        if ( source == null ) {
            return null;
        }

        ArtistEntity artistEntity = new ArtistEntity();

        artistEntity.setArtistId( source.getArtistId() );
        artistEntity.setArtistname( source.getArtistName() );

        return artistEntity;
    }

    @Override
    public Artist entityToModel(ArtistEntity destination) {
        if ( destination == null ) {
            return null;
        }

        Artist artist = new Artist();

        artist.setArtistId( destination.getArtistId() );
        artist.setArtistName( destination.getArtistname() );

        return artist;
    }
}
