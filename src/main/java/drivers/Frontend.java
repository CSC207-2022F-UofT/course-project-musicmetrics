package drivers;

import javax.swing.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import use_cases.MusicData;


/*
Java Swing drivers.Frontend, simply run the file to use.

Check Java Swing documentation for more info.
 */
public class Frontend extends JFrame{
    JFrame f;
    final JTextField welcome = new JTextField();

    Frontend() {
        JButton b = new JButton("Get genres");
        welcome.setText("MusicMetrics");

        b.setBounds(20, 100, 300, 40);
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MusicData.getGenres();
            }
        });
        add(b);add(welcome);
        setSize(400,500);
        setLayout(null);
        setVisible(true);
    }


    public static void main(String[] args) throws FileNotFoundException {
        MusicData.setData();
        new Frontend();
    }
}
