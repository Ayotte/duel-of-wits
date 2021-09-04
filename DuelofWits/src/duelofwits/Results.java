package duelofwits;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.custom.CLabel;

public class Results {

	protected Shell shlResults;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Results window = new Results();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlResults.open();
		shlResults.layout();
		while (!shlResults.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlResults = new Shell();
		shlResults.setSize(557, 404);
		shlResults.setText("Results");
		shlResults.setLayout(new GridLayout(18, false));
		new Label(shlResults, SWT.NONE);
		
		Label lblPlayer = new Label(shlResults, SWT.NONE);
		lblPlayer.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblPlayer.setText("Player 1");
		new Label(shlResults, SWT.NONE);
		new Label(shlResults, SWT.NONE);
		new Label(shlResults, SWT.NONE);
		new Label(shlResults, SWT.NONE);
		new Label(shlResults, SWT.NONE);
		new Label(shlResults, SWT.NONE);
		new Label(shlResults, SWT.NONE);
		new Label(shlResults, SWT.NONE);
		new Label(shlResults, SWT.NONE);
		new Label(shlResults, SWT.NONE);
		new Label(shlResults, SWT.NONE);
		new Label(shlResults, SWT.NONE);
		new Label(shlResults, SWT.NONE);
		new Label(shlResults, SWT.NONE);
		new Label(shlResults, SWT.NONE);
		
		Label lblPlayer_2 = new Label(shlResults, SWT.NONE);
		lblPlayer_2.setText("Player 2");
		lblPlayer_2.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));

	}

}
