package duelofwits;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {

	public int diceNumber;
	public int diceShade;
	public String resultsString;
	public String testType;
	public int successCnt;
	public String action;
	public int bodyOfArgument;
	public int boaLost;
	
	public Player() {
		this.resultsString = "N/A";
	}
	
	//We will roll numberofDice dice, and any number >= successValue will succeed
	public void setDiceValues(int numberOfDice,int successValue) {
		this.diceNumber = numberOfDice;
		this.diceShade = successValue;
	}
	
	public void setResultsString(String resultsString) {
		this.resultsString = resultsString;
	}
	
	public String getResultsString() {
		return this.resultsString;
	}
	
	public void setTestType(String testType) {
		this.testType = testType;
	}
	
	public String getTestType() {
		return this.testType;
	}
	
	public void setSuccessCnt(int successCnt) {
		this.successCnt = successCnt;
	}
	
	public int getSuccessCnt() {
		return this.successCnt;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getAction() {
		return this.action;
	}
	
	public void changeBoa(int boaChange) {
		this.bodyOfArgument = this.bodyOfArgument + boaChange;
		this.boaLost = -boaChange;
	}
	
	public int getBoa() {
		return this.bodyOfArgument;
	}
	
	public void setBoa(int boa) {
		this.bodyOfArgument = boa;
	}
	
	public int getBoaLost() {
		return this.boaLost;
	}
	

	//Returns a list.  First value in the list is the number of successes.  Each
	//other value is one of the die rolls.
	public List<Integer> rollDice() {
		List<Integer> resultList = new ArrayList<Integer>();
		int dieResult;
		int successCnt = 0;
		Random rand = new Random();
		resultList.add(0,successCnt);
		for (int i=1; i <= this.diceNumber; i++) {
			dieResult = 1 + rand.nextInt(6);
			resultList.add(i, dieResult);
			if (dieResult >= this.diceShade) {
				successCnt++;
			}
		}
		resultList.set(0,successCnt);
		return resultList;
	}
}
