
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

class SearcherTest {

    @Test
    public void GetRelevantScoreDifferentLength() {
        double score1 = Searcher.getRelevantScore("top", "top 5 genres");
        double score2 = Searcher.getRelevantScore("top", "top 5 artists");
        Assertions.assertTrue(score1 > score2);
    }

    @Test
    public void GetRelevantScoreDifferentIndexDifferentLength() {
        double score1 = Searcher.getRelevantScore("top", "top 5 genres");
        double score2 = Searcher.getRelevantScore("top", "5 top artists");
        Assertions.assertTrue(score1 > score2);
    }

    @Test
    public void GetRelevantScoreDifferentIndexDifferentLength2() {
        double score1 = Searcher.getRelevantScore("t", "tea party lets go");
        double score2 = Searcher.getRelevantScore("t", "bts");
        Assertions.assertTrue(score1 > score2);
    }

    @Test
    public void GetRelevantScoreDifferentIndexDifferentLength3() {
        double score1 = Searcher.getRelevantScore("te", "tea party lets go");
        double score2 = Searcher.getRelevantScore("te", "I ate pizza");
        double score3 = Searcher.getRelevantScore("te", "Everyone, attention!");
        Assertions.assertTrue(score1 > score2);
        Assertions.assertTrue(score2 > score3);
    }

    @Test
    public void FilterKeywordCommonKeyword() throws FileNotFoundException {
        Searcher searcher = new Searcher();
        List<String> expected = new ArrayList<>();
        expected.add("top 5 genres");
        expected.add("top 20 genres");
        expected.add("top 10 genres");
        expected.add("top 5 artists");
        expected.add("top 10 artists");
        expected.add("top 20 artists");
        expected.add("top 5 genres of the past week");
        expected.add("top 5 artists of the past week");
        expected.add("top 10 genres of the past week");
        expected.add("top 20 genres of the past week");
        List<String> actual = searcher.filterKeyword("top");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void FilterKeywordPartialKeyword() throws FileNotFoundException {
        Searcher searcher = new Searcher();
        List<String> expected = new ArrayList<>();
        expected.add("top 5 artists of the past week");
        expected.add("top 10 artists of the past week");
        expected.add("top 20 artists of the past week");
        List<String> actual = searcher.filterKeyword("artists of");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void FilterArtistCommonKeyword() throws FileNotFoundException {
        Searcher searcher = new Searcher();
        List<String> expected = new ArrayList<>();
        expected.add("The Weeknd");
        expected.add("Taylor Swift");
        expected.add("BTS");
        expected.add("Post Malone");
        expected.add("XXXTENTACION");
        expected.add("Justin Bieber");
        List<String> actual = searcher.filterArtist("t");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void FilterArtistPartialKeyword() throws FileNotFoundException {
        Searcher searcher = new Searcher();
        List<String> expected = new ArrayList<>();
        expected.add("Eminem");
        expected.add("Imagine Dragons");
        expected.add("Post Malone");
        List<String> actual = searcher.filterArtist("ne");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void FilterGenreCommonKeyword() throws FileNotFoundException {
        Searcher searcher = new Searcher();
        List<String> expected = new ArrayList<>();
        expected.add("Latin trap");
        expected.add("Latin urbano");
        List<String> actual = searcher.filterGenre("Latin");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void FilterGenrePartialKeyword() throws FileNotFoundException {
        Searcher searcher = new Searcher();
        List<String> expected = new ArrayList<>();
        expected.add("Latin trap");
        expected.add("Hip-Hop/Rap");
        List<String> actual = searcher.filterGenre("rap");
        Assertions.assertEquals(expected, actual);
    }
}

