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
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            in.readLine(); // getting rid of the first line...
            String line;
            while ((line = in.readLine()) != null) {
                String[] song = line.split(";");
                int score = Integer.parseInt(song[2]);
                 insert(song[0], song[1], score); // calls insert() on the new song
            }
        } catch (IOException e) {
            System.err.println("Error: Unable to read file '" + filename + "'. Check if the file exists");
        }
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
        Song newSong = new Song(title, artist, score);
        SongNode newSNode = new SongNode(newSong);
        if (this.headByScore == null && this.headByTitle == null) {
            this.headByScore = newSNode;
            this.headByTitle = newSNode;
            return;
        }
        String newSongTitle = newSong.getTitle();

        SongNode currScore = this.headByScore; // pointer keeping track of nodes ordered by SCORE
        int currScoreVal = currScore.getSong().getScore(); // SCORE value of pointer

        SongNode currTitle = this.headByTitle; // pointer keeping track of nodes ordered alphabetically by Title
        String currTitleVal = currTitle.getSong().getTitle(); // Title value of pointer

        // adding by Score
        // while the next node for currScore is non-null AND while the score of newSong is less than the score of the
        // next node...
        while (currScore.getNextByScore() != null && (newSong.getScore() < currScore.getNextByScore().getSong().getScore())) {
            currScore = currScore.getNextByScore();
        }

        while (currScore.getNextByScore() != null && (newSong.getScore() == currScore.getNextByScore().getSong().getScore())) {
            if (newSongTitle.compareTo(currScore.getNextByScore().getSong().getTitle()) > 0 && currScore.getNextByScore().getSong().getScore() == newSong.getScore()) {
                currScore = currScore.getNextByScore();
                currScoreVal = currScore.getSong().getScore();
            } else {
                break;
            }
        }

        if (currScore == this.headByScore && newSNode.getSong().getScore() >= this.headByScore.getSong().getScore()) {
            newSNode.setNextByScore(currScore);
            this.headByScore = newSNode;
        } else if (currScore.getNextByScore() == null && (newSongTitle.compareTo(currScore.getSong().getTitle()) > 0)) {
            currScore.setNextByScore(newSNode);
        } else {
            newSNode.setNextByScore(currScore.getNextByScore());
            currScore.setNextByScore(newSNode);
        }

        // adding by Title in Alphabetical Order
        while (currTitle.getNextByTitle() != null && newSongTitle.compareTo(currTitle.getNextByTitle().getSong().getTitle()) > 0) {
            currTitle = currTitle.getNextByTitle();
        }

        if (currTitle == this.headByTitle && newSongTitle.compareTo(currTitle.getSong().getTitle()) < 0) {
            newSNode.setNextByTitle(currTitle);
            this.headByTitle = newSNode;
        } else {
            newSNode.setNextByTitle(currTitle.getNextByTitle());
            currTitle.setNextByTitle(newSNode);
        }
    }

    /**
     * Checks if there's a song with given title/artist in the list.
     * @param title title
     * @param artist artist
     * @return true if the song is in the list, false otherwise
     */
    public boolean containsSong(String title, String artist) {
        // FILL IN CODE:
        SongNode curr = this.headByTitle;

        while (curr != null) {
            if (title.compareTo(curr.getSong().getTitle()) == 0 && artist.compareTo(curr.getSong().getArtist()) == 0) {
                return true;
            }
            curr = curr.getNextByTitle();
        }

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
        boolean scoreDone = false;
        boolean titleDone = false;

        if (this.headByTitle == null) {
            return null;
        }

        if (title.compareTo(this.headByScore.getSong().getTitle()) == 0 && title.compareTo(this.headByTitle.getSong().getTitle()) == 0) {
            Song song = this.headByScore.getSong();
            this.headByScore = this.headByScore.getNextByScore();
            this.headByTitle = this.headByTitle.getNextByTitle();
            return song;
        }

        if (title.compareTo(this.headByScore.getSong().getTitle()) == 0) {
            this.headByScore = this.headByScore.getNextByScore();
            scoreDone = true;
        }

        if (title.compareTo(this.headByTitle.getSong().getTitle()) == 0) {
            this.headByTitle = this.headByTitle.getNextByTitle();
            titleDone = true;
        }

        SongNode curr = this.headByTitle;
        SongNode curr2 = this.headByScore;

        while (curr.getNextByTitle() != null || curr2.getNextByScore() != null) {
            if (title.compareTo(curr.getNextByTitle().getSong().getTitle()) == 0 && artist.compareTo(curr.getNextByTitle().getSong().getArtist()) == 0) {
                titleDone = true;
            }
            if (title.compareTo(curr2.getNextByScore().getSong().getTitle()) == 0 && artist.compareTo(curr2.getNextByScore().getSong().getArtist()) == 0) {
                scoreDone = true;
            }
            if (!titleDone) {
                curr = curr.getNextByTitle();
            }
            if (!scoreDone) {
                curr2 = curr2.getNextByScore();
            }
            if (titleDone && scoreDone) break;
        }


        if (curr.getNextByTitle() != null && curr2.getNextByScore() != null) {
            SongNode temp = curr.getNextByTitle();
            if (titleDone) {
                curr.setNextByTitle(curr.getNextByTitle().getNextByTitle());
            }
            if (scoreDone) {
                curr2.setNextByScore(curr2.getNextByScore().getNextByScore());
            }
            return temp.getSong();
        } else {
            return null;
        }
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
        if (min < 1) return result;
        if (this.headByTitle == null) return result;

        SongNode curr = this.headByScore;

        while (curr != null) {
            int currScore = curr.getSong().getScore();
            String currTitle = curr.getSong().getTitle();
            String currArtist = curr.getSong().getArtist();
            if (currScore >= min && currScore <= max) {
                result.insert(currTitle, currArtist, currScore);
            }
            curr = curr.getNextByScore();
        }
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
        if (k < 1) {
            return result;
        }

        if (k == 1) {
            String title = this.headByScore.getSong().getTitle();
            String artist = this.headByScore.getSong().getArtist();
            int score = this.headByScore.getSong().getScore();
            result.insert(title, artist, score);
            return result;
        }

        SongNode curr = this.headByScore;
        int j = 0;

        while (curr != null && j < k) {
            String title = curr.getSong().getTitle();
            String artist = curr.getSong().getArtist();
            int score = curr.getSong().getScore();
            result.insert(title, artist, score);
            curr = curr.getNextByScore();
            j++;
        }

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
        SongNode fast = this.headByScore;
        SongNode slow = this.headByScore;

        for (int i = 0; i < k; i++) {
            if (fast == null) {
                break;
            }
            fast = fast.getNextByScore();
        }

        while (fast != null) {
            fast = fast.getNextByScore();
            slow = slow.getNextByScore();
        }

        while (slow != null) {
            String title = slow.getSong().getTitle();
            String artist = slow.getSong().getArtist();
            int score = slow.getSong().getScore();
            result.insert(title, artist, score);
            slow = slow.getNextByScore();
        }

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
            return current != null;
        }

        @Override
        public Song next() {
            // FILL IN CODE
            if (!hasNext()) {
                throw new NoSuchElementException("Song List Empty");
            }
            Song song = current.getSong();
            current = current.getNextByTitle();
            return song;
        }
    }

    // Iterator by score
    class IteratorByScore implements Iterator<Song> {
        private SongNode current = headByScore;

        @Override
        public boolean hasNext() {
            // FILL IN CODE
            return current != null;
        }

        @Override
        public Song next() {
            // FILL IN CODE
            if (!hasNext()) {
                throw new NoSuchElementException("Song List Empty");
            }
            Song song = current.getSong();
            current = current.getNextByScore();
            return song;
        }
    }
}
