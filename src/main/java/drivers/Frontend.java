package drivers;

import interface_adapters.Searcher;
import use_cases.MusicData;

import javax.swing.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.List;


/*
Java Swing Frontend, simply run the file to use.

Check Java Swing documentation for more info.
 */
public class Frontend extends JFrame{
    JFrame f;
    final JTextField welcome = new JTextField();

    Frontend() throws FileNotFoundException {
        JTextField tf = new JTextField();
        Searcher searcher = new Searcher();
        JTextArea area = new JTextArea();
        JButton b = new JButton("Get genres");
        welcome.setText("MusicMetrics");
        welcome.setBounds(20, 550, 300, 40);
        b.setBounds(20, 500, 300, 40);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                welcome.setText(MusicData.getGenres().toString());
            }
        });
        area.setEditable(false);
        tf.setBounds(15, 50, 200, 40);

        JButton search = new JButton("Search");
        area.setBounds(20, 100, 300 , 200);
        search.setBounds(220, 50, 100, 40);
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String keyword = tf.getText();
                StringBuilder output = new StringBuilder();
                List<String> actions = searcher.filterKeyword(keyword);
                for (String action : actions) {
                    output.append(action).append("\n");
                }
                area.setText(output.toString());
            }
        });
        setLayout(null);
        setVisible(true);
        setSize(500, 800);


        add(tf);add(area);add(search);add(b);add(welcome);

    }


    public static void main(String[] args) throws FileNotFoundException {
        MusicData.setData();
        new Frontend();
    }
}
