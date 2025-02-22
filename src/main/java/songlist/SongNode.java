package songlist;

public class SongNode {
    private Song song;
    private SongNode nextByScore; // Next node in descending score order
    private SongNode nextByTitle; // Next node in ascending title order

    public SongNode(Song song) {
        this.song = song;
    }

    public Song getSong() {
        return song;
    }

    public SongNode getNextByScore() {
        return nextByScore;
    }

    public SongNode getNextByTitle() {
        return nextByTitle;
    }

    public void setNextByScore(SongNode nextByScore) {
        this.nextByScore = nextByScore;
    }

    public void setNextByTitle(SongNode nextByTitle) {
        this.nextByTitle = nextByTitle;
    }
}
