import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class InitialFrame extends JFrame {

	public InitialFrame() {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(500, 400));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		addElements(this.getContentPane());
	}

	private void addElements(Container pane) {
		if (!(pane.getLayout() instanceof BorderLayout)) {
			pane.setLayout(new BorderLayout());
		}

		String text = "Hello and thank you for taking part in this experiment. <br> "
				+ " You will be asked to perform 9 tasks in which you will have to <br>"
				+ "find the correct word in the list as quickly as you can.<br>"
				+ "If you are ready to begin the experiment, please click ok at the bottom of this screen.";
		JLabel explanation = new JLabel("<html><div style='text-align: center;'>" + text + "</div></html>");
		pane.add(explanation, BorderLayout.CENTER);
		JButton button = new JButton("Ok");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MainFrame frame = new MainFrame();
				frame.setVisible(true);
				setVisible(false);
				dispose();
				return;
			}
		});
		pane.add(button, BorderLayout.SOUTH);
	}

}
