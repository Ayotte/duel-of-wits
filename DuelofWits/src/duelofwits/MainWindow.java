package duelofwits;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class MainWindow {

	protected Shell shlDuelOfWits;
	private Text text_1;
	private Text text_2;
	Player PlayerOne = new Player();
	Player PlayerTwo = new Player();
	private Label p1Results;
	private Label p2Results;
	private Spinner spn_p1boa;
	private Spinner spn_p2boa;
	private Label totalResults;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
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
		shlDuelOfWits.open();
		shlDuelOfWits.layout();
		while (!shlDuelOfWits.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	public void mapContest(String p1Choice,String p2Choice) {
		
	}
	
	private void launchDiceWindow(Player PlayerOne, Player PlayerTwo, String p1Action, String p2Action) {
		PlayerOne.setBoa(Integer.parseInt(spn_p1boa.getText()));
		PlayerTwo.setBoa(Integer.parseInt(spn_p2boa.getText()));
		PlayerOne.setAction(p1Action);
		PlayerTwo.setAction(p2Action);
		DiceWindow dicewindow = new DiceWindow(PlayerOne, PlayerTwo);
		dicewindow.open(PlayerOne, PlayerTwo, p1Action, p2Action);
		totalResults.setText("Player 1 rolls " + PlayerOne.getAction() + " as a " +
				PlayerOne.getTestType() + " test and loses " + PlayerOne.getBoaLost() + 
				" from their Body of Argument\n\nPlayer 2 rolls " +
				PlayerTwo.getAction() + " as a " + PlayerTwo.getTestType() + 
				" test and loses " + PlayerTwo.getBoaLost() + " from their Body of Argument");
		refreshText();
	}
	
	//Used for BoA - don't need actions
	private void launchDiceWindow(Player PlayerOne, Player PlayerTwo) {
		PlayerOne.setBoa(0);
		PlayerTwo.setBoa(0);
		PlayerOne.setAction("BOA");
		PlayerTwo.setAction("BOA");
		DiceWindow dicewindow = new DiceWindow(PlayerOne, PlayerTwo);
		dicewindow.open(PlayerOne, PlayerTwo, "BOA", "BOA");
		totalResults.setText("Player 1's body of argument set to " + PlayerOne.getBoa() + 
				"\n\nPlayer 2's body of argument set to " + PlayerTwo.getBoa());
		refreshText();
	}
	
	private void refreshText() {
		p1Results.setText(PlayerOne.getResultsString());
		p2Results.setText(PlayerTwo.getResultsString());
		spn_p1boa.setSelection(PlayerOne.getBoa());
		spn_p2boa.setSelection(PlayerTwo.getBoa());
		shlDuelOfWits.layout();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlDuelOfWits = new Shell();
		shlDuelOfWits.setModified(true);
		shlDuelOfWits.setSize(1081, 448);
		shlDuelOfWits.setText("Duel of Wits");
		
		Label lblPlayerStatement = new Label(shlDuelOfWits, SWT.NONE);
		lblPlayerStatement.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblPlayerStatement.setBounds(10, 56, 176, 15);
		lblPlayerStatement.setText("Player 1 Statement of Purpose");
		
		Label lblPlayerStatement_2 = new Label(shlDuelOfWits, SWT.NONE);
		lblPlayerStatement_2.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblPlayerStatement_2.setText("Player 2 Statement of Purpose");
		lblPlayerStatement_2.setBounds(879, 56, 176, 15);
		
		text_1 = new Text(shlDuelOfWits, SWT.BORDER | SWT.WRAP);
		text_1.setBounds(10, 77, 258, 79);
		
		text_2 = new Text(shlDuelOfWits, SWT.BORDER | SWT.WRAP);
		text_2.setBounds(797, 77, 258, 79);
		
		Label lblBodyOfArgument = new Label(shlDuelOfWits, SWT.NONE);
		lblBodyOfArgument.setBounds(10, 165, 98, 15);
		lblBodyOfArgument.setText("Body of Argument");
		
		Label lblBodyOfArgument_1 = new Label(shlDuelOfWits, SWT.NONE);
		lblBodyOfArgument_1.setText("Body of Argument");
		lblBodyOfArgument_1.setBounds(957, 165, 98, 15);
		
		spn_p1boa = new Spinner(shlDuelOfWits, SWT.BORDER);
		spn_p1boa.setMinimum(-100);
		spn_p1boa.setBounds(114, 162, 47, 22);
		
		spn_p2boa = new Spinner(shlDuelOfWits, SWT.BORDER);
		spn_p2boa.setMinimum(-100);
		spn_p2boa.setBounds(904, 162, 47, 22);
		
		Label lblAction = new Label(shlDuelOfWits, SWT.NONE);
		lblAction.setBounds(274, 77, 55, 15);
		lblAction.setText("Action 1");
		
		Label lblAction_3 = new Label(shlDuelOfWits, SWT.NONE);
		lblAction_3.setText("Action 2");
		lblAction_3.setBounds(274, 122, 55, 15);
		
		Label lblAction_1 = new Label(shlDuelOfWits, SWT.NONE);
		lblAction_1.setText("Action 3");
		lblAction_1.setBounds(274, 165, 55, 15);
		
		Combo p1Action1List = new Combo(shlDuelOfWits, SWT.NONE);
		p1Action1List.setItems(new String[] {"Point", "Dismiss", "Avoid", "Obfuscate", "Rebuttal", "Feint", "Incite", "Cast Spell", "Command Spirit", "Drop Spell", "Sing, Howl, Pray", "Fall Prone", "Run Screaming", "Stand and Drool", "Swoon"});
		p1Action1List.setBounds(333, 74, 91, 23);
		
		Combo p1Action2List = new Combo(shlDuelOfWits, SWT.NONE);
		p1Action2List.setItems(new String[] {"Point", "Dismiss", "Avoid", "Obfuscate", "Rebuttal", "Feint", "Incite", "Cast Spell", "Command Spirit", "Drop Spell", "Sing, Howl, Pray", "Fall Prone", "Run Screaming", "Stand and Drool", "Swoon"});
		p1Action2List.setBounds(333, 119, 91, 23);
		
		Combo p1Action3List = new Combo(shlDuelOfWits, SWT.NONE);
		p1Action3List.setItems(new String[] {"Point", "Dismiss", "Avoid", "Obfuscate", "Rebuttal", "Feint", "Incite", "Cast Spell", "Command Spirit", "Drop Spell", "Sing, Howl, Pray", "Fall Prone", "Run Screaming", "Stand and Drool", "Swoon"});
		p1Action3List.setBounds(333, 162, 91, 23);
		
		Label lblAction_2 = new Label(shlDuelOfWits, SWT.NONE);
		lblAction_2.setText("Action 1");
		lblAction_2.setBounds(641, 80, 55, 15);
		
		Label lblAction_3_1 = new Label(shlDuelOfWits, SWT.NONE);
		lblAction_3_1.setText("Action 2");
		lblAction_3_1.setBounds(641, 125, 55, 15);
		
		Label lblAction_1_1 = new Label(shlDuelOfWits, SWT.NONE);
		lblAction_1_1.setText("Action 3");
		lblAction_1_1.setBounds(641, 168, 55, 15);
		
		Combo p2Action1List = new Combo(shlDuelOfWits, SWT.NONE);
		p2Action1List.setItems(new String[] {"Point", "Dismiss", "Avoid", "Obfuscate", "Rebuttal", "Feint", "Incite", "Cast Spell", "Command Spirit", "Drop Spell", "Sing, Howl, Pray", "Fall Prone", "Run Screaming", "Stand and Drool", "Swoon"});
		p2Action1List.setBounds(700, 77, 91, 23);
		
		Combo p2Action2List = new Combo(shlDuelOfWits, SWT.NONE);
		p2Action2List.setItems(new String[] {"Point", "Dismiss", "Avoid", "Obfuscate", "Rebuttal", "Feint", "Incite", "Cast Spell", "Command Spirit", "Drop Spell", "Sing, Howl, Pray", "Fall Prone", "Run Screaming", "Stand and Drool", "Swoon"});
		p2Action2List.setBounds(700, 122, 91, 23);
		
		Combo p2Action3List = new Combo(shlDuelOfWits, SWT.NONE);
		p2Action3List.setItems(new String[] {"Point", "Dismiss", "Avoid", "Obfuscate", "Rebuttal", "Feint", "Incite", "Cast Spell", "Command Spirit", "Drop Spell", "Sing, Howl, Pray", "Fall Prone", "Run Screaming", "Stand and Drool", "Swoon"});
		p2Action3List.setBounds(700, 165, 91, 23);
		
		Button btnFirstVolley = new Button(shlDuelOfWits, SWT.NONE);
		btnFirstVolley.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				launchDiceWindow(PlayerOne, PlayerTwo, p1Action1List.getText(), p2Action1List.getText());
			}
		});
		btnFirstVolley.setBounds(467, 75, 138, 25);
		btnFirstVolley.setText("First Volley");
		
		Button btnSecondVolley = new Button(shlDuelOfWits, SWT.NONE);
		btnSecondVolley.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				launchDiceWindow(PlayerOne, PlayerTwo, p1Action2List.getText(), p2Action2List.getText());
			}
		});
		btnSecondVolley.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnSecondVolley.setText("Second Volley");
		btnSecondVolley.setBounds(467, 117, 138, 25);
		
		Button btnThirdVolley = new Button(shlDuelOfWits, SWT.NONE);
		btnThirdVolley.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				launchDiceWindow(PlayerOne, PlayerTwo, p1Action3List.getText(), p2Action3List.getText());
			}
		});
		btnThirdVolley.setText("Third Volley");
		btnThirdVolley.setBounds(467, 160, 138, 25);
		
		Button btnNewRound = new Button(shlDuelOfWits, SWT.NONE);
		btnNewRound.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				p1Action1List.deselectAll();
				p1Action2List.deselectAll();
				p1Action3List.deselectAll();
				p2Action1List.deselectAll();
				p2Action2List.deselectAll();
				p2Action3List.deselectAll();
			}
		});
		btnNewRound.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		btnNewRound.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNewRound.setBounds(467, 204, 138, 42);
		btnNewRound.setText("New Round");
		
		Label lblNewLabel = new Label(shlDuelOfWits, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblNewLabel.setBounds(10, 260, 98, 22);
		lblNewLabel.setText("Results");
		
		Label lblNewLabel_1 = new Label(shlDuelOfWits, SWT.NONE);
		lblNewLabel_1.setText("Results");
		lblNewLabel_1.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.BOLD));
		lblNewLabel_1.setBounds(1000, 260, 55, 22);
		
		p1Results = new Label(shlDuelOfWits, SWT.NONE);
		p1Results.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		p1Results.setText(PlayerOne.getResultsString());
		p1Results.setBounds(10, 286, 258, 113);
		
		p2Results = new Label(shlDuelOfWits, SWT.NONE);
		p2Results.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		p2Results.setAlignment(SWT.RIGHT);
		p2Results.setText(PlayerTwo.getResultsString());		
		p2Results.setBounds(797, 286, 258, 113);
		
		totalResults = new Label(shlDuelOfWits, SWT.WRAP);
		totalResults.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		totalResults.setAlignment(SWT.CENTER);
		totalResults.setBounds(334, 265, 407, 134);
		
		Button btnRollBoa = new Button(shlDuelOfWits, SWT.NONE);
		btnRollBoa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				launchDiceWindow(PlayerOne, PlayerTwo);
			}
		});
		btnRollBoa.setBounds(467, 10, 138, 42);
		btnRollBoa.setText("Roll BoA");
		
		
		
	}
}
