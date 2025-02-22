import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import songlist.Song;
import songlist.SongList;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class SongListTest {
    private SongList listA;
    private SongList listB;
    private SongList list;

    @BeforeEach
    public void setUp() {
        listA = new SongList();
        listB = new SongList();
        list = new SongList();
    }

    @Test
    public void testInsertAndIterationByTitle() {
        listA.insert("Love Story", "Taylor Swift", 5);
        listA.insert("Bad Habits", "Ed Sheeran", 3);
        listA.insert("Hello", "Adele", 4);
        Iterator<Song> titleIt = listA.iteratorByTitle();
        // Expect songs sorted by title: "Bad Habits", "Hello", "Love Story"
        assertTrue(titleIt.hasNext(), "Iterator should have elements");
        Song t1 = titleIt.next();
        assertEquals("Bad Habits", t1.getTitle());

        Song t2 = titleIt.next();
        assertEquals("Hello", t2.getTitle());

        Song t3 = titleIt.next();
        assertEquals("Love Story", t3.getTitle());

        assertFalse(titleIt.hasNext(), "Iterator should be exhausted");
    }


    @Test
    public void testInsertAndIterationByScore() {
        listA.insert("Love Story", "Taylor Swift", 5);
        listA.insert("Bad Habits", "Ed Sheeran", 3);
        listA.insert("Hello", "Adele", 4);

        Iterator<Song> scoreIt = listA.iteratorByScore();
        // Should be sorted by score in decreasing order: 5, 4, 3
        assertTrue(scoreIt.hasNext(), "Iterator should have elements");
        Song first = scoreIt.next();
        assertEquals("Love Story", first.getTitle());
        assertEquals("Taylor Swift", first.getArtist());
        assertEquals(5, first.getScore());

        Song second = scoreIt.next();
        assertEquals("Hello", second.getTitle());
        assertEquals("Adele", second.getArtist());
        assertEquals(4, second.getScore());

        Song third = scoreIt.next();
        assertEquals("Bad Habits", third.getTitle());
        assertEquals("Ed Sheeran", third.getArtist());
        assertEquals(3, third.getScore());

        assertFalse(scoreIt.hasNext(), "Iterator should be exhausted");

    }

    @Test
    public void testContainsSong() {
        listA.insert("Song A", "Artist A", 2);
        listA.insert("Song B", "Artist B", 4);

        assertTrue(listA.containsSong("Song A", "Artist A"));
        assertTrue(listA.containsSong("Song B", "Artist B"));
        assertFalse(listA.containsSong("Song C", "Artist C"));
    }

    @Test
    public void testFindSongsWithinScoreRange() {
        listA.insert("Low Score", "Artist", 1);
        listA.insert("Mid Score", "Artist", 3);
        listA.insert("High Score", "Artist", 5);

        SongList filtered = listA.findSongsWithinScoreRange(2, 5);
        // Expect filtered to contain nodes with scores 3 and 5

        Iterator<Song> it = filtered.iteratorByScore();
        assertTrue(it.hasNext(), "Filtered list should have songs");
        Song first = it.next();
        assertEquals("High Score", first.getTitle());
        assertEquals(5, first.getScore());

        Song second = it.next();
        assertEquals("Mid Score", second.getTitle());
        assertEquals(3, second.getScore());

        assertFalse(it.hasNext(), "No more songs in filtered list");
    }

    @Test
    public void testLoadSongsNonExistentFile() {
        listA.loadSongs("nonExistentFile.csv"); // Should handle IOException, not crash
        assertTrue(true, "No crash on non-existent file");
    }

    @Test
    public void testLoadSongsFromCsv() {
        String csvPath = "src/main/resources/songs.csv";
        list.loadSongs(csvPath);
        // Checks only a few songs, not all (so passing this test does not mean your code is correct)
        assertTrue(list.containsSong("Birds of a Feather", "Billie Eilish"),
                "Should contain 'Birds of a Feather' by Billie Eilish");
        assertTrue(list.containsSong("Just the Way You Are", "Bruno Mars"),
                "Should contain 'Just the Way You Are' by Bruno Mars");
        assertTrue(list.containsSong("Hotel California", "Eagles"),
                "Should contain 'Hotel California' by Eagles");

        assertFalse(list.containsSong("Random Title", "Unknown Artist"),
                "Should not contain arbitrary missing songs");
    }

    @Test
    public void testRemoveHead() {
        list.insert("TitleA", "ArtistA", 5);
        list.insert("TitleB", "ArtistB", 4);
        list.insert("TitleC", "ArtistC", 3);
        Song removed = list.remove("TitleA", "ArtistA");
        assertNotNull(removed, "Removed song should not be null");
        assertEquals("TitleA", removed.getTitle(), "Should remove TitleA");
        assertEquals("ArtistA", removed.getArtist(), "Should remove ArtistA");
        assertEquals(5, removed.getScore(), "Score should be 5");
        assertFalse(list.containsSong("TitleA", "ArtistA"), "TitleA, ArtistA should be removed");
    }

    @Test
    public void testRemoveMiddle() {
        list.insert("Alpha", "A", 5);
        list.insert("Beta", "B", 4);
        list.insert("Gamma", "G", 3);

        Song removed = list.remove("Beta", "B");
        assertNotNull(removed, "Removed node should not be null");
        assertEquals("Beta", removed.getTitle());
        assertEquals("B", removed.getArtist());
        assertFalse(list.containsSong("Beta", "B"), "Beta should be removed");
    }

    @Test
    public void testRemoveNonExistent() {
        list.insert("One", "Artist1", 5);
        list.insert("Two", "Artist2", 4);

        Song removed = list.remove("Three", "Artist3");
        assertNull(removed, "Removing a non-existent song should return null");
        assertTrue(list.containsSong("One", "Artist1"), "Song One should still be present");
        assertTrue(list.containsSong("Two", "Artist2"), "Song Two should still be present");
    }

    @Test
    public void testRemoveOnlyNode() {
        list.insert("A", "A", 5);

        Song removed = list.remove("A", "A");
        assertNotNull(removed, "Removed node should not be null");
        assertFalse(list.containsSong("A", "A"), "No nodes");
    }

    @Test
    public void testFindBestK() {
        list.insert("SongA", "ArtistA", 5);
        list.insert("SongB", "ArtistB", 4);
        list.insert("SongC", "ArtistC", 4);
        list.insert("SongD", "ArtistD", 3);

        // findBestKSongs(2): top songs are SongA(5), SongB(4)
        SongList best2 = list.findBestKSongs(2);
        Iterator<Song> it = best2.iteratorByScore();
        assertTrue(it.hasNext());
        Song first = it.next();
        assertEquals("SongA", first.getTitle());
        assertEquals(5, first.getScore());

        assertTrue(it.hasNext());
        Song second = it.next();
        assertEquals("SongB", second.getTitle());
        assertEquals(4, second.getScore());

        assertFalse(it.hasNext(), "Should only have 2 songs in best2");

        // findBestKSongs(10) - we have less, return all we have, 4
        SongList best10 = list.findBestKSongs(10);
        Iterator<Song> it10 = best10.iteratorByScore();
        assertEquals("SongA", it10.next().getTitle());
        assertEquals("SongB", it10.next().getTitle());
        assertEquals("SongC", it10.next().getTitle());
        assertEquals("SongD", it10.next().getTitle());
        assertFalse(it10.hasNext());
    }

    @Test
    public void testFindWorstK() {
        // Insert songs: Scores 5,4,4,3
        // Ordering by score: SongX(5)-> SongY(4)-> SongZ(4)-> SongW(3)
        list.insert("SongW", "ArtistW", 3);
        list.insert("SongZ", "ArtistZ", 4);
        list.insert("SongY", "ArtistY", 4);
        list.insert("SongX", "ArtistX", 5);
        Iterator<Song> iteratorByScore = list.iteratorByScore();
        while (iteratorByScore.hasNext()) {
            System.out.println(iteratorByScore.next());
        }
        System.out.println();

        // findWorstKSongs(1) - lowest score: SongW(3)
        SongList worst1 = list.findWorstKSongs(1);
        Iterator<Song> it = worst1.iteratorByScore();

        assertTrue(it.hasNext());
        Song only = it.next();
        assertEquals("SongW", only.getTitle());
        assertEquals(3, only.getScore());
        assertFalse(it.hasNext());

        // findWorstKSongs(2): SongZ(4), SongW(3)
        SongList worst2 = list.findWorstKSongs(2);
        Iterator<Song> it2 = worst2.iteratorByScore();
        assertTrue(it2.hasNext());
        Song first = it2.next(); // the 4 (SongZ)
        Song second = it2.next(); // the 3
        assertFalse(it2.hasNext());

        assertTrue(first.getScore() == 4, "Expect 4");
        assertTrue(second.getScore() == 3, "Expect 3");

        // findWorstKSongs(10): return all songs we have (4)
        SongList worst10 = list.findWorstKSongs(10);
        Iterator<Song> it10 = worst10.iteratorByScore();
        // Should contain all 4 in descending order: 5,4,4,3
        assertEquals(5, it10.next().getScore());
        assertEquals(4, it10.next().getScore());
        assertEquals(4, it10.next().getScore());
        assertEquals(3, it10.next().getScore());
        assertFalse(it10.hasNext());
    }

    @Test
    public void testFindBestAndWorstEdgeCases() {
        SongList emptyBest = list.findBestKSongs(3);
        assertFalse(emptyBest.iteratorByScore().hasNext(), "Empty list => no best songs");

        SongList emptyWorst = list.findWorstKSongs(2);
        assertFalse(emptyWorst.iteratorByScore().hasNext(), "Empty list => no worst songs");

        list.insert("Solo", "OneArtist", 5);
        SongList best1 = list.findBestKSongs(1);
        assertTrue(best1.containsSong("Solo", "OneArtist"));

        SongList worst1 = list.findWorstKSongs(1);
        assertTrue(worst1.containsSong("Solo", "OneArtist"));

        SongList none = list.findBestKSongs(0);
        assertFalse(none.iteratorByScore().hasNext(), "Zero k => empty result");
        SongList neg = list.findWorstKSongs(-5);
        assertFalse(neg.iteratorByScore().hasNext(), "Negative k => empty result");
    }
}