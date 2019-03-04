import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	private final String[] wordList = { "onion", "carrot", "rabbit", "horse", "turtle", "chair", "table", "pencil",
			"tissue", "window", "chimney", "brush", "phone", "laptop", "paper"};
	private final int numberOfWords = 9;

	public MainFrame() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(400, 300));
		addElements(this.getContentPane());
	}

	public void addElements(Container pane) {
		if (!(pane.getLayout() instanceof BorderLayout)) {
			pane.setLayout(new BorderLayout());
		}
		Random rnd = new Random();
		ArrayList<String> tmpList = new ArrayList<>(Arrays.asList(wordList));
		ArrayList<String> randomElems = new ArrayList<>(); 
		for (int i = 0; i < numberOfWords; i++) {
	        int randomIndex = rnd.nextInt(tmpList.size());
	        randomElems.add(tmpList.get(randomIndex));
	        tmpList.remove(randomIndex);
	    }
		JLabel task = new JLabel("Please select the word onion from the list");
		pane.add(task, BorderLayout.NORTH);
		JPanel panel = new ListPanel(randomElems);
		pane.add(panel, BorderLayout.CENTER);
	}
}
