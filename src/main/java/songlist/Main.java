package songlist;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        SongList playList = new SongList();
        playList.loadSongs("src/main/resources/songs.csv");
        playList.remove("Yellow", "Coldplay");
        System.out.println();
        playList.remove("Sorry", "Justin Bieber");
        System.out.println();
        playList.remove("Thinking Out Loud", "Ed Sheeran");
        System.out.println();
        // Call other methods in SongList, including using two iterators to
        // traverse songs by title (using IteratorByTitle) and by score (using IteratorByScore).
        // FILL IN CODE

    }
}
