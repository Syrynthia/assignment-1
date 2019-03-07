import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	private final String[] wordList = { "onion", "carrot", "rabbit", "horse", "turtle", "chair", "table", "pencil",
			"tissue", "window", "chimney", "brush", "phone", "laptop", "paper" };
	private List<Integer> indexes;
	private final int numberOfWords = 9;
	private JPanel listPanel;
	private JLabel task;

	public MainFrame() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(500, 400));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		indexes = new ArrayList<>();
		for (int i = 0; i < numberOfWords; i++) {
			indexes.add(i);
		}
		Collections.shuffle(indexes);
		addElements(this.getContentPane());
	}

	public void addElements(Container pane) {
		if (!(pane.getLayout() instanceof BorderLayout)) {
			pane.setLayout(new BorderLayout());
		}
		Random rnd = new Random();
		List<String> randomElems = getRandomElems(rnd);
		String searched = randomElems.get(indexes.remove(0));
		task = new JLabel("Please select the word " + searched + " from the list");
		pane.add(task, BorderLayout.NORTH);
		addList(searched, randomElems, pane);
	}

	private List<String> getRandomElems(Random rnd) {
		ArrayList<String> tmpList = new ArrayList<>(Arrays.asList(wordList));
		ArrayList<String> randomElems = new ArrayList<>();
		for (int i = 0; i < numberOfWords; i++) {
			int randomIndex = rnd.nextInt(tmpList.size());
			randomElems.add(tmpList.get(randomIndex));
			tmpList.remove(randomIndex);
		}
		return randomElems;
	}

	private void addList(String searched, List<String> words, Container pane) {
		if (listPanel != null) {
			JPanel oldPanel = listPanel;
			pane.remove(oldPanel);
		}
		listPanel = new JPanel();
		listPanel.setLayout(new GridLayout(0, 1));
		for (String word : words) {
			JButton button = new JButton(word);
			if (word.equals(searched)) {
				button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (indexes.isEmpty()) {
							setVisible(false);
							dispose();
							return;
						}
						Random rnd = new Random();
						List<String> randomElems = getRandomElems(rnd);
						String searched = randomElems.get(indexes.remove(0));
						JLabel oldTask = task;
						pane.remove(oldTask);
						task = new JLabel("Please select the word " + searched + " from the list");
						pane.add(task, BorderLayout.NORTH);
						addList(searched, randomElems, pane);

					}
				});
			}
			listPanel.add(button);
		}
		pane.add(listPanel, BorderLayout.CENTER);
		pane.revalidate();
		pane.repaint();
	}
}
