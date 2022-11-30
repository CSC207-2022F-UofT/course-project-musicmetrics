
import interface_adapters.Searcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

class SearcherTest {

    @Test
    public void GetRelevantScoreNoRelevancy() {
        double score = Searcher.getRelevantScore("bro", "top 5 genres");
        Assertions.assertEquals(0, score);
    }

    @Test
    public void GetRelevantScoreSameIndexSameLength() {
        double score1 = Searcher.getRelevantScore("top", "top 10 genres");
        double score2 = Searcher.getRelevantScore("top", "top 5 artists");
        Assertions.assertTrue(score1 > score2);
    }

    @Test
    public void GetRelevantScoreSameIndexDifferentLength() {
        double score1 = Searcher.getRelevantScore("top", "top 5 genres");
        double score2 = Searcher.getRelevantScore("top", "top 5 artists");
        Assertions.assertTrue(score1 > score2);
    }

    @Test
    public void GetRelevantScoreDifferentIndexSameLength() {
        double score1 = Searcher.getRelevantScore("top", "top 5 artists of the past week");
        double score2 = Searcher.getRelevantScore("top", "5 top artists of the past week");
        Assertions.assertTrue(score1 > score2);
    }

    @Test
    public void GetRelevantScoreDifferentIndexDifferentLength() {
        double score1 = Searcher.getRelevantScore("top", "top 5 artists of the past week");
        double score2 = Searcher.getRelevantScore("top", "10 top artists of the past week");
        Assertions.assertTrue(score1 > score2);
    }

    @Test
    public void FilterKeywordNoMatch() throws FileNotFoundException {
        Searcher searcher = new Searcher();
        List<String> expected = new ArrayList<>();
        List<String> actual = searcher.filterKeyword("bruh");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void FilterKeywordShortKeyword() throws FileNotFoundException {
        Searcher searcher = new Searcher();
        List<String> expected = new ArrayList<>();
        expected.add("top 5 artists");
        expected.add("5 most trending artists");
        expected.add("top 5 artists of the past week");
        List<String> actual = searcher.filterKeyword("5");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void FilterKeywordLongKeyword() throws FileNotFoundException {
        Searcher searcher = new Searcher();
        List<String> expected = new ArrayList<>();
        expected.add("top 5 artists of the past week");
        expected.add("top 14 artists of the past week");
        expected.add("top 10 artists of the past week");
        List<String> actual = searcher.filterKeyword("of the past week");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void FilterArtistNoMatch() throws FileNotFoundException {
        Searcher searcher = new Searcher();
        List<String> expected = new ArrayList<>();
        List<String> actual = searcher.filterArtist("Jack the Reaper");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void FilterArtistShortKeyword() throws FileNotFoundException {
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
    public void FilterArtistLongKeyword() throws FileNotFoundException {
        Searcher searcher = new Searcher();
        List<String> expected = new ArrayList<>();
        expected.add("Taylor Swift");
        List<String> actual = searcher.filterArtist("Taylor");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void FilterGenreNoMatch() throws FileNotFoundException {
        Searcher searcher = new Searcher();
        List<String> expected = new ArrayList<>();
        List<String> actual = searcher.filterGenre("Pokemon");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void FilterGenreShortKeyword() throws FileNotFoundException {
        Searcher searcher = new Searcher();
        List<String> expected = new ArrayList<>();
        expected.add("R&B");
        expected.add("Latin trap");
        expected.add("Latin urbano");
        expected.add("Hip-Hop/Rap");
        List<String> actual = searcher.filterGenre("r");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void FilterGenreLongKeyword() throws FileNotFoundException {
        Searcher searcher = new Searcher();
        List<String> expected = new ArrayList<>();
        expected.add("Latin trap");
        expected.add("Latin urbano");
        List<String> actual = searcher.filterGenre("Latin");
        Assertions.assertEquals(expected, actual);
    }
}

