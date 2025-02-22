package songlist;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A custom linked list class that stores song nodes.
 * Each node has a reference to the "next by title" node and the "next by score" node.
 * Allows to iterate over the list by title (in increasing alphabetical order)
 * or by score (in decreasing order of the score).
 */
public class SongList {
    private SongNode headByScore; // Head of the list if we want to iterate in the decreasing order of scores.
    private SongNode headByTitle; // Head of the list if we want to iterate in the increasing alphabetical order of the titles.


    /**
     * Read a given csv file and insert songs to the SongList.
     * @param filename name of csv file with songs; the file stores each song as following:
     * Title;Artist;Score
     *
     */
    public void loadSongs(String filename) {
        // FILL IN CODE: must use a BufferedReader, not Scanner
        // to read songs from the file
        // Call insert method for every song.

    }

    /** Insert a song node with the given song into this linked list,
     * preserving the correct order, and updating both references (the ones connecting nodes according to the title, and the ones connecting nodes according to the score).
     * Before and after the insertion, the nodes should be ordered by title using nextByTitle references
     * and by score, using nextbyScore references.
     * @param title title
     * @param artist artist
     * @param score score (rating)
     */
    public void insert(String title, String artist, int score) {
        // Feel free to write helper methods like insertByTitle, insertByScore
        // FILL IN CODE:

    }

    /**
     * Checks if there's a song with given title/artist in the list.
     * @param title title
     * @param artist artist
     * @return true if the song is in the list, false otherwise
     */
    public boolean containsSong(String title, String artist) {
        // FILL IN CODE:

        return false;
    }

    /**
     * Find and remove the SongNode containing the song with the given title and artist.
     * @param title title of the song
     * @param artist artist performing the song
     * @return removed Song if found it, otherwise null.
     */
    public Song remove(String title, String artist) {
        // FILL IN CODE:
        // Feel free to add helper methods

        return null; // change
    }


    /** Return a SongList where each song's score falls in [min, max] range.
     * Songs should be sorted in decreasing order of the score.
     *
     * @param min smallest allowed score
     * @param max largest allowed score
     * @return SongList that contains songs whose score is >= min, <= max.
     */
    public SongList findSongsWithinScoreRange(int min, int max) {
        SongList result = new SongList();
        // FILL IN CODE:

        return result;
    }

    /**
     * Return a new SongList containing the top k highest-scoring songs.
     * If k >= the total number of songs, all songs are returned.
     * @param k number of highest scoring songs to return
     * @return song list containing the top k highest-scoring songs
     */
    public SongList findBestKSongs(int k) {
        SongList result = new SongList();
        // FILL IN CODE:

        return result;
    }


    /**
     * Return a new SongList containing the k lowest-scoring songs.
     * Must use the slow/fast pointer approach to find the start of the last k nodes.
     * Not allowed to count nodes or keep track of the size of the list.
     * If k >= total size, return the entire list.
     * @param k number of lowest scoring songs to return.
     * @return song list with k lowest-scoring songs
     */
    public SongList findWorstKSongs(int k) {
        SongList result = new SongList();
        // FILL IN CODE:

        return result;
    }

    /**
     * An iterator for iterating "by title"
     * @return iterator by title
     */
    public Iterator<Song> iteratorByTitle() {
        return new IteratorByTitle();
    }

    /** An iterator for iterating "by score"
     *
     * @return iterator by score
     */
    public Iterator<Song> iteratorByScore() {
        return new IteratorByScore();
    }

    // Iterator by Title
    class IteratorByTitle implements Iterator<Song> {
        private SongNode current = headByTitle;

        @Override
        public boolean hasNext() {
            // FILL IN CODE
            return false;
        }

        @Override
        public Song next() {
            // FILL IN CODE

            return null;
        }
    }

    // Iterator by score
    class IteratorByScore implements Iterator<Song> {
        private SongNode current = headByScore;

        @Override
        public boolean hasNext() {
            // FILL IN CODE

            return false;
        }

        @Override
        public Song next() {
            // FILL IN CODE

            return null; // change
        }
    }
}
