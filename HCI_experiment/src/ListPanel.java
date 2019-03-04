import java.awt.GridLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ListPanel extends JPanel {
	public ListPanel(List<String> words) {
		super();
		this.setLayout(new GridLayout(0, 1));
		for(String word : words) {
			JButton button = new JButton(word);
			add(button);
		}
	}

}
