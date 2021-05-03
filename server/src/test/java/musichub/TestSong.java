package test.java.musichub;
import musichub.business.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


class TestSong {
    Song song = new Song("testTitle", "testArtist",60,"testsong.wav","jazz");
  //  Song songfail = new Song("testTitle", "testArtist",60,"testsong.wav","electro");
    
    @Test
    void testGenre() {
        assertEquals(song.getGenre(),"jazz");
    }

    @Test
    void testtoString() {
        assertEquals(song.toString(),"Title = testTitle, Artist = testArtist, Length = 60, Content = testsong.wav, Genre = jazz\n");
    }
    /*@Test
    void testFalseGenre() {
        assertEquals(songfail.getGenre(),"electro");
    }*/

}