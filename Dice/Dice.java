package Dice;

public class Dice {
	
	int maxNumber, diceResult, totalNumber;
	
	// constructor
	Dice() {
		this.maxNumber = 6;
	}
	Dice(int maxNumber) {
		if (maxNumber > 0) {
			this.maxNumber = maxNumber;
		} else {
			this.maxNumber = 6;
		}
	}
	
	/*
	 * サイコロを振る
	 */
	void rollDice() {
		this.diceResult = (int)(Math.random() * this.maxNumber) + 1;
		this.totalNumber += this.diceResult;
	}

	/*
	 * 回数でサイコロを振る 一回以上は合計を出す
	 * @param times 振る回数
	 */
	void rollDice(int times) {
		if (times > 1) {
			for (int i = 1; i <= times; i++) {
				rollDice();
				System.out.println(i + "回目のサイコロの結果：" + this.diceResult);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
			}
			System.out.println("合計：" + this.totalNumber);
		} else {
			rollDice();
			System.out.println("サイコロの結果:" + this.diceResult);
		}
			
	}
	
	/*
	 * サイコロの結果（合計）を比べる　（大きい値が勝つ）
	 * @param Dice 	比べるサイコロオブジェクト
	 * @return		比べた結果の文字列
	 */
	String compareDice(Dice d) {
		if (this.totalNumber > d.totalNumber) {
			return "Player1が勝ちました。";
		} else if (this.totalNumber < d.totalNumber) { 
			return "Player2が勝ちました。";
		} else {
			return "同じです。";
		}
	}
	
	// getter, setter
	public int getMaxNumber() {
		return maxNumber;
	}
	public void setMaxNumber(int maxNumber) {
		this.maxNumber = maxNumber;
	}
	public int getDiceNumber() {
		return diceResult;
	}
	public void setDiceNumber(int diceNumber) {
		this.diceResult = diceNumber;
	}
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	
	@Override
	public String toString() {
		return "最大の数値" + this.maxNumber + "持つサイコロ";
	}

}
