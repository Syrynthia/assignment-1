import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	private int targetIndex = 0; // the index of the word in listPanel
	private long start;
	private Map<String, String> map = new HashMap<String, String>();// the time spend in finding target word in each position
	private List<String> time_task = new ArrayList<String>(); // the time spend in each task
	private boolean saveDataIntoDatabase = false;
	private boolean saveDataIntoFile = true;
	private DataManager manager = new DataManager();
	
	public MainFrame() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(500, 400));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		addInitialWindow(this.getContentPane());
	}
	
	private void addInitialWindow(Container pane) {
		indexes = new ArrayList<>();
		for (int i = 0; i < numberOfWords; i++) {
			indexes.add(i);
		}
		Collections.shuffle(indexes); 
		String text = "Hello and thank you for taking part in this experiment. <br> "
				+ " You will be asked to perform 9 tasks in which you will have to <br>"
				+ "find the correct word in the list as quickly as you can.<br>"
				+ "If you are ready to begin the experiment, please click ok at the bottom of this screen.";
		
		JLabel explanationPanel = new JLabel("<html><div style='text-align: center;'>" + text + "</div></html>");
		JButton startButton = new JButton("Start");
		
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("button is clicked");
				pane.remove(explanationPanel);
				pane.remove(startButton);
				addElements(pane);	
			}
		});
		pane.add(explanationPanel, BorderLayout.CENTER);
		pane.add(startButton, BorderLayout.SOUTH);
		pane.revalidate();
		pane.repaint();
	
	}

	
	public void addElements(Container pane) {
		if (!(pane.getLayout() instanceof BorderLayout)) {
			pane.setLayout(new BorderLayout());
		}
		Random rnd = new Random();
		List<String> randomElems = getRandomElems(rnd); // create a random sublist of the word list predefined
		
		targetIndex = indexes.get(0); // get the index of the target word in list
		start = System.currentTimeMillis(); // start the timer
		
		String searched = randomElems.get(indexes.remove(0)); // use and remove the first element in indexes as the index
															  // use this index get a element from random list as the target word
		task = new JLabel("Please select the word \"" + searched + "\" from the list");
		pane.add(task, BorderLayout.NORTH);
		addList(searched, randomElems, pane);
	}

	private List<String> getRandomElems(Random rnd) {
		ArrayList<String> tmpList = new ArrayList<>(Arrays.asList(wordList)); // copy the word list we predefined
		ArrayList<String> randomElems = new ArrayList<>(); // create a empty array list
		for (int i = 0; i < numberOfWords; i++) {
			int randomIndex = rnd.nextInt(tmpList.size()); // generate a random index in range [, n-1)
			//System.out.println(randomIndex);
			randomElems.add(tmpList.get(randomIndex)); // get the word by index and add it into the 
			tmpList.remove(randomIndex); // remove the word which has already been selected
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
							long totalTime = System.currentTimeMillis() - start;
							//System.out.println(targetIndex + " : " + totalTime);
							map.put(Integer.toString(targetIndex), Long.toString(totalTime)); // store the target word and the time user spend in finding it in map
							time_task.add(Long.toString(totalTime));
							//System.out.println(map);
							try {
								
								manager.connectMySQL();
								//manager.createTable1();
								//manager.createTable2();
								List<String> time_position = new ArrayList<String>(map.values());
								manager.saveData(time_position, saveDataIntoDatabase, saveDataIntoFile, "time_position");
								manager.saveData(time_task, saveDataIntoDatabase, saveDataIntoFile, "time_task");
								//manager.insertData(time_task, "time_position");
								//manager.insertData(time_task, "time_task");
								//manager.deconnectMySQL();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							pane.remove(listPanel);
							pane.remove(task);
							addInitialWindow(pane);
							
							//setVisible(false);
							//dispose();
							return;
						}
						//System.out.print("correct word has been selected");
						long totalTime = System.currentTimeMillis() - start;
						//System.out.println(targetIndex + " : " + totalTime);
						
						map.put(Integer.toString(targetIndex), Long.toString(totalTime)); // store the target word and the time user spend in finding it in a map
						time_task.add(Long.toString(totalTime));
						
						Random rnd = new Random();
						List<String> randomElems = getRandomElems(rnd);
						
						targetIndex = indexes.get(0);
						start = System.currentTimeMillis();

						String searched = randomElems.get(indexes.remove(0));
						JLabel oldTask = task;
						pane.remove(oldTask);
						task = new JLabel("Please select the word \"" + searched + "\" from the list");
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
