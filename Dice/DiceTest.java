package dice;

import java.util.Scanner;

public class DiceTest {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("サイコロの最大の数値：");
		int maxNumber = sc.nextInt();
		Dice d1 = new Dice(maxNumber);

		System.out.print("何回振りますか？：");
		int times = sc.nextInt();
		
		displayMenu();
		int menuNo = sc.nextInt();

		while (!(menuNo == 1 || menuNo == 2 || menuNo == 3)) {
			System.out.print("1から３の番号を書いてください：");
			menuNo = sc.nextInt();
		}
		switch (menuNo) {
			case 1 -> {
				System.out.println("\nソロサイコロ");
				d1.rollDice(times);
			}
			case 2 -> {
				System.out.println("\nPlayer 1");
				d1.rollDice(times);
	
				System.out.println("\nPlayer 2");
				Dice d2 = new Dice(maxNumber);
				d2.rollDice(times);
				
				System.out.println("\n" + d1.compareDice(d2));
			}
			case 3 -> {
				System.out.println("\nPlayer 1(自分)");
				d1.rollDice(times);
	
				System.out.println("\nPlayer 2(パソコン)");
				Dice d3 = new Dice(maxNumber);
				d3.rollDice(times);
				
				System.out.println("\n" + d1.compareDice(d3));
			}
		}

		sc.close();
	}
	
	static void displayMenu() {
		System.out.println("\n1. 一人で振る");
		System.out.println("2. 二人で振る");
		System.out.println("3. パソコンと振る");
		System.out.print("やりたい番号を選んでください：");
	}

}
