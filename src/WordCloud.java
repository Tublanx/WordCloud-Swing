import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WordCloud extends JFrame {

	JPanel c; // Center Panel
	JLabel titleLbl;
	JLabel[] wordsLbl;

	ArrayList<String> wordList = new ArrayList<>();
	Scanner sc = null;

	static final String FONT_NAME = "맑은 고딕";
	static final int FONT_STYLE = Font.BOLD;
	static final int FONT_SIZE = 11;

	public WordCloud() {
		setTitle("WordCloud"); // set frame title
		setSize(1000, 800); // set frame size (width, height)
		setDefaultCloseOperation(EXIT_ON_CLOSE); // turn off the process when close the frame
		setLocationRelativeTo(null); // set frame location to center

		uiConfig();
		setWordCloud();

		setVisible(true);
	}

	void uiConfig() {
		add(titleLbl = new JLabel("WordCloud", 0), "North");
		add(c = new JPanel(new FlowLayout(1, 5, 5)));
		titleLbl.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
		((JPanel) getContentPane()).setBackground(Color.WHITE);
		c.setOpaque(false);
	}

	void setWordCloud() {
		try {
			sc = new Scanner(new File("datafile/korean_test.txt"));

			while (sc.hasNext()) {
				wordList.add(sc.next());
			}

			sc.close();

			List<String> distinctWordList = wordList.stream().distinct().collect(Collectors.toList());

			wordsLbl = new JLabel[distinctWordList.size()];
			for (int i = 0; i < wordsLbl.length; i++) {
				c.add(wordsLbl[i] = new JLabel(distinctWordList.get(i), 0));
				wordsLbl[i].setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
			}

			wordList.stream().filter(x -> Collections.frequency(wordList, x) > 1).collect(Collectors.toSet())
					.forEach(x -> {
						for (int i = 0; i < wordsLbl.length; i++) {
							if (wordsLbl[i].getText().equals(x)) {
								int freq = Collections.frequency(wordList, x);
								if (freq > 5) {
									wordsLbl[i].setForeground(Color.RED);
								} else if (freq <= 5 && freq >= 3) {
									wordsLbl[i].setForeground(Color.ORANGE);
								} else if (freq < 3 && freq >= 1) {
									wordsLbl[i].setForeground(Color.GREEN);
								}

								wordsLbl[i].setFont(
										new Font(FONT_NAME, FONT_STYLE, FONT_SIZE + (freq < 11 ? (freq * 5) : 55)));
							}
						}
					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new WordCloud();
	}
}
