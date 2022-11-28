package drivers;
import interface_adapters.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.List;

public class SearchFrame extends JFrame implements ActionListener {

    Container container = getContentPane();
    JTextField searchField = new JTextField();
    DefaultListModel<String> searchModel = new DefaultListModel<>();
    JList<String> searchList = new JList<>(searchModel);
    final String[] SEARCH_TYPES = {"keyword", "artist", "genre"};
    JComboBox<String> searchTypesComboBox = new JComboBox<>(SEARCH_TYPES);
    JButton searchButton = new JButton("SEARCH");
    Searcher searcher = new Searcher();


    SearchFrame() throws FileNotFoundException {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    public void updateSearchSuggestions(String keyword, String searchType) {
        searchModel.clear();
        switch (searchType) {
            case "keyword":
                List<String> actions = searcher.filterKeyword(keyword);
                for (String action : actions) {
                    searchModel.addElement(action);
                }
                break;
            case "artist":
                List<String> artists = searcher.filterArtist(keyword);
                for (String artist : artists) {
                    searchModel.addElement(artist);
                }
                break;
            case "genre":
                List<String> genres = searcher.filterGenre(keyword);
                for (String genre : genres) {
                    searchModel.addElement(genre);
                }
                break;
        }
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        searchField.setBounds(100, 50, 200, 30);
        searchButton.setBounds(315, 50, 100, 30);
        searchList.setBounds(100, 100, 315 , 200);
        searchTypesComboBox.setBounds(10, 50,80,30);

    }

    public void addComponentsToContainer() {
        container.add(searchField);
        container.add(searchList);
        container.add(searchTypesComboBox);
        container.add(searchButton);
    }

    public void addActionEvent() {
        searchButton.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            if (searchField.getText().length() > 0) {
                updateSearchSuggestions(searchField.getText(), searchTypesComboBox
                        .getItemAt(searchTypesComboBox.getSelectedIndex()));
            }
        }
    }
}
