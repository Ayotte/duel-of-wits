package duelofwits;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.wb.swt.SWTResourceManager;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class DiceWindow {

	protected Shell shlRoll;
	public static final String VULNERABLE = "vulnerable";
	public static final String STANDARD = "standard";
	public static final String VERSUS = "versus";
	public static final String SPECIAL = "special";
	public static final String STANDARD_VS_SPECIAL = "standard vs special";
	public static final String BOA = "BOA";

	public DiceWindow(Player PlayerOne,Player PlayerTwo) {
		
	}
	
	public DiceWindow() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Launch the application.
	 * @param args
	 */
//	public static void main(String[] args) {
//		try {
//			DiceWindow window = new DiceWindow();
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Open the window.
	 */
	public void open(Player PlayerOne,Player PlayerTwo,String p1Action,String p2Action) {
		Display display = Display.getDefault();
		PlayerOne.setAction(p1Action);
		PlayerTwo.setAction(p2Action);
		createContents(PlayerOne, PlayerTwo);
		shlRoll.open();
		shlRoll.layout();
		while (!shlRoll.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	private void rollDice(Player PlayerOne,Player PlayerTwo,int p1NumberOfDice,int p1Shade,int p2NumberOfDice,int p2Shade) {
		List<Integer> p1Results = new ArrayList<Integer>();
		List<Integer> p2Results = new ArrayList<Integer>();	
		
		PlayerOne.setDiceValues(p1NumberOfDice,p1Shade);
		PlayerTwo.setDiceValues(p2NumberOfDice,p2Shade);
		
		p1Results = PlayerOne.rollDice();
		p2Results = PlayerTwo.rollDice();
		int p1SuccessCnt = p1Results.get(0);
		int p2SuccessCnt = p2Results.get(0);
		String p1Action = PlayerOne.getAction();
		String p2Action = PlayerTwo.getAction();
		
		PlayerOne.setSuccessCnt(p1SuccessCnt);
		PlayerTwo.setSuccessCnt(p2SuccessCnt);
		
		String p1Contest = mapContest(p1Action,p2Action);
		String p2Contest = mapContest(p1Action,p2Action);
		PlayerOne.setTestType(p1Contest);
		PlayerTwo.setTestType(p2Contest);
		
		//Compare results and change BoA
		int[] resultAry = compareResults(p1SuccessCnt,p2SuccessCnt,p1Contest,p2Contest);
		PlayerOne.changeBoa(resultAry[0]);
		PlayerTwo.changeBoa(resultAry[1]);
		
		//set result String for display in MainWindow
		String resultString1 = new String();
		resultString1 = Integer.toString(PlayerOne.getSuccessCnt()) + " successes: \n";
		for (int i=1; i<p1Results.size(); i++) {
			resultString1 = resultString1 + " " + p1Results.get(i);
		}
		String resultString2 = new String();
		resultString2 = Integer.toString(PlayerTwo.getSuccessCnt()) + " successes: \n";
		for (int i=1; i<p2Results.size(); i++) {
			resultString2 = resultString2 + " " + p2Results.get(i);
		}
		PlayerOne.setResultsString(resultString1);
		PlayerTwo.setResultsString(resultString2);
	}
	
	//returns an array [player1 body of argument change,player2 body of argument change]
	//p1Type and p2Type = "vulnerable";"standard";"versus";"special";"standard vs special";
	private int[] compareResults(int p1SuccessCnt, int p2SuccessCnt, String p1Type, String p2Type) {
		int[] resultAry = new int[2];
		int p1Change = 0;
		int p2Change = 0;
		
		if(p1Type == BOA) {
			p1Change = p1SuccessCnt;
		}
		else if(p1Type == STANDARD || p1Type == STANDARD_VS_SPECIAL) {
			p2Change = 0 - p1SuccessCnt;
		}
		else if(p1Type == VULNERABLE) {
			p1Change = 0;
		}
		else if(p1Type == SPECIAL) {
			p2Change = 0;
		}
		
		if (p2Type == BOA) {
			p2Change = p2SuccessCnt;
		}
		else if(p2Type == STANDARD || p2Type == STANDARD_VS_SPECIAL) {
			p1Change = 0 - p2SuccessCnt;
		}
		else if(p2Type == VULNERABLE) {
			p2Change = 0;
		}
		else if(p2Type == SPECIAL) {
			p1Change = 0;
		}
		
		if(p1Type == VERSUS & p2Type == VERSUS) {
			if(p1SuccessCnt > p2SuccessCnt) {
				p2Change = p2SuccessCnt - p1SuccessCnt;
			}
			else if(p2SuccessCnt > p1SuccessCnt) {
				p1Change = p1SuccessCnt - p2SuccessCnt;
			}
			
		}
		
		resultAry[0] = p1Change;
		resultAry[1] = p2Change;
		
		return resultAry;
	}
	
	//Each player is either rolling versus, a standard test, or is vulnerable
	//This will be called twice - once for each player
	private String mapContest(String playerAction,String opponentAction) {
		if (playerAction.equals(BOA)) {
			return BOA;
		}
		if (playerAction.equals("Avoid")) {
			if (opponentAction.equals("Avoid") || opponentAction.equals("Dismiss") || opponentAction.equals("Feint") || opponentAction.equals("Rebuttal")) {
				return VULNERABLE;
				
			}
			else if (opponentAction.equals("Incite") || opponentAction.equals("Obfuscate") || opponentAction.equals("Point")) {
				return VERSUS;
			}
			else {
				return STANDARD_VS_SPECIAL;
			}
		}
		else if (playerAction.equals("Dismiss")) {
			if (opponentAction.equals("Avoid") || opponentAction.equals("Dismiss") || opponentAction.equals("Feint") || opponentAction.equals("Incite") || opponentAction.equals("Point")) {
				return STANDARD;
			}
			else if (opponentAction.equals("Obfuscate") || opponentAction.equals("Point")) {
				return VERSUS;
			}
			else {
				return STANDARD_VS_SPECIAL;
			}
		}
		else if (playerAction.equals("Feint")) {
			if (opponentAction.equals("Avoid") || opponentAction.equals("Dismiss") || opponentAction.equals("Point")) {
				return VULNERABLE;
			}
			else if (opponentAction.equals("Rebuttal")) {
				return STANDARD;
			}
			else if (opponentAction.equals("Feint") || opponentAction.equals("Incite") || opponentAction.equals("Obfuscate")) {
				return VERSUS;
			}
			else {
				return STANDARD_VS_SPECIAL;
			}
			
		}
		else if (playerAction.equals("Incite")) {
			if (opponentAction=="Dismiss" || opponentAction.equals("Incite") || opponentAction.equals("Point") || opponentAction.equals("Rebuttal")) {
				return STANDARD;
			}
			else if (opponentAction.equals("Avoid") || opponentAction.equals("Feint") || opponentAction.equals("Obfuscate")) {
				return VERSUS;
			}
			else {
				return STANDARD_VS_SPECIAL;
			}
		}
		else if (playerAction.equals("Obfuscate")) {
			if (opponentAction.equals("Avoid") || opponentAction.equals("Dismiss") || opponentAction.equals("Feint") || opponentAction.equals("Incite") || opponentAction.equals("Obfuscate") || opponentAction.equals("Point") || opponentAction.equals("Rebuttal")) {
				return VERSUS;
			}
			else {
				return STANDARD_VS_SPECIAL;
			}
		}
		else if (playerAction.equals("Point")) {
			if (opponentAction.equals("Dismiss") || opponentAction.equals("Feint") || opponentAction.equals("Incite") || opponentAction.equals("Point")) {
				return STANDARD;
			}
			else if (opponentAction.equals("Avoid") || opponentAction.equals("Obfuscate") || opponentAction.equals("Rebuttal")) {
				return VERSUS;
			}
			else {
				return STANDARD_VS_SPECIAL;
			}
		}
		else if (playerAction.equals("Rebuttal")) {
			if (opponentAction.equals("Avoid") || opponentAction.equals("Feint") || opponentAction.equals("Incite") || opponentAction.equals("Rebuttal")) {
				return VULNERABLE;
			}
			else if (opponentAction.equals("Dismiss") || opponentAction.equals("Obfuscate") || opponentAction.equals("Point")) {
				return VERSUS;
			}
			else {
				return STANDARD_VS_SPECIAL;
			}
		}
		else {
			return SPECIAL;
		}
		
		
	}
//		playerOne.setResultsString("Rolling "+playerOne.diceNumber+" "+
//				playerOne.diceShade+" dice");
//		playerTwo.setResultsString("Rolling "+playerTwo.diceNumber+" "+
		
//				playerTwo.diceShade+" dice");
	/**
	 * Create contents of the window.
	 */
	protected void createContents(Player PlayerOne,Player PlayerTwo) {
		shlRoll = new Shell(SWT.APPLICATION_MODAL);
		shlRoll.setSize(450, 243);
		shlRoll.setText("Roll");
		
		Label lblShade = new Label(shlRoll, SWT.NONE);
		lblShade.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblShade.setBounds(104, 10, 55, 15);
		lblShade.setText("Shade");
		
		Label lblShade_1 = new Label(shlRoll, SWT.NONE);
		lblShade_1.setText("Shade");
		lblShade_1.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblShade_1.setBounds(104, 106, 55, 15);
		
		Label lblPlayerDice = new Label(shlRoll, SWT.NONE);
		lblPlayerDice.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblPlayerDice.setBounds(10, 10, 75, 15);
		lblPlayerDice.setText("Player 1 Dice");
		
		Label lblPlayerDice_2 = new Label(shlRoll, SWT.NONE);
		lblPlayerDice_2.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblPlayerDice_2.setText("Player 2 Dice");
		lblPlayerDice_2.setBounds(10, 106, 75, 15);
		
		Spinner spnP1NumberOfDice = new Spinner(shlRoll, SWT.BORDER);
		spnP1NumberOfDice.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		spnP1NumberOfDice.setBounds(10, 31, 67, 27);
		
		Spinner spnP2NumberOfDice = new Spinner(shlRoll, SWT.BORDER);
		spnP2NumberOfDice.setFont(SWTResourceManager.getFont("Segoe UI", 12, SWT.NORMAL));
		spnP2NumberOfDice.setBounds(10, 127, 67, 27);
		
		Group grpP1Shade = new Group(shlRoll, SWT.NONE);
		grpP1Shade.setBounds(93, 16, 96, 78);
		
		Button p1BlackShade = new Button(grpP1Shade, SWT.RADIO);
		p1BlackShade.setSelection(true);
		p1BlackShade.setBounds(3, 15, 90, 16);
		p1BlackShade.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		p1BlackShade.setText("Black");
		
		Button p1GreyShade = new Button(grpP1Shade, SWT.RADIO);
		p1GreyShade.setBounds(3, 37, 90, 16);
		p1GreyShade.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		p1GreyShade.setText("Grey");
		
		Button p1WhiteShade = new Button(grpP1Shade, SWT.RADIO);
		p1WhiteShade.setBounds(3, 59, 90, 16);
		p1WhiteShade.setText("White");
		
		Group grpP2Shade = new Group(shlRoll, SWT.NONE);
		grpP2Shade.setBounds(93, 112, 96, 78);
		
		Button p2BlackShade = new Button(grpP2Shade, SWT.RADIO);
		p2BlackShade.setSelection(true);
		p2BlackShade.setBounds(3, 15, 90, 16);
		p2BlackShade.setText("Black");
		
		Button p2GreyShade = new Button(grpP2Shade, SWT.RADIO);
		p2GreyShade.setBounds(3, 37, 90, 16);
		p2GreyShade.setText("Grey");
		
		Button p2WhiteShade = new Button(grpP2Shade, SWT.RADIO);
		p2WhiteShade.setBounds(3, 59, 90, 16);
		p2WhiteShade.setText("White");
		
		Button btnRoll = new Button(shlRoll, SWT.NONE);
		btnRoll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				int p1Shade = 0;
				int p2Shade = 0;
				if (p1BlackShade.getSelection()) p1Shade = 4;
				else if (p1GreyShade.getSelection()) p1Shade = 3;
				else if (p1WhiteShade.getSelection()) p1Shade = 2;
				if (p2BlackShade.getSelection()) p2Shade = 4;
				else if (p2GreyShade.getSelection()) p2Shade = 3;
				else if (p2WhiteShade.getSelection()) p2Shade = 2;
				int p1NumberOfDice=spnP1NumberOfDice.getSelection();
				int p2NumberOfDice=spnP2NumberOfDice.getSelection();
				rollDice(PlayerOne, PlayerTwo, p1NumberOfDice, p1Shade, p2NumberOfDice, p2Shade);
				shlRoll.close();

				}
			}
	);
		btnRoll.setFont(SWTResourceManager.getFont("Segoe UI", 16, SWT.NORMAL));
		btnRoll.setBounds(212, 10, 212, 180);
		btnRoll.setText("Roll");
		
		Button btnCancel = new Button(shlRoll, SWT.NONE);
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				shlRoll.close();
			}
		});
		btnCancel.setBounds(212, 196, 212, 27);
		btnCancel.setText("Cancel");

	}
}
