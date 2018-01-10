
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import controller.TetrisGameController;
import view.HoldBlockView;
import view.MainFrame;
import view.NextBlockView;
import view.StatusBar;
import view.TetrisView;

public class TetrisApplication {

	private JFrame frame;

	/**
	 * 
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TetrisApplication window = new TetrisApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TetrisApplication() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new MainFrame();
		TetrisGameController controller = new TetrisGameController(frame);
		
		JPanel statusBar = new StatusBar(controller);
		frame.getContentPane().add(statusBar);
		
		JPanel tetrisView = new TetrisView(controller);
		frame.getContentPane().add(tetrisView);
		
		JPanel nextBlocksView = new NextBlockView(controller);
		frame.getContentPane().add(nextBlocksView);
		
		JPanel holdingBlockView = new HoldBlockView(controller);
		frame.getContentPane().add(holdingBlockView);
	}
}
