package shiritori;

import java.util.ArrayList;
import java.util.Scanner;

public class Shiritori {

	public static void main(String[] args) {
		
		init();
		play("しりとり");
		
	}
	
	static void init() {
		System.out.println("しりとり");
		System.out.println("----");
	}
	
	/*
	 * 「しりとり」 という言葉から始まり、「ん」で終わったらゲーム終了
	 * @param String	前回入力した言葉
	 */
	static void play(String currentWord) {
		int i = 1;
		ArrayList<String> words = new ArrayList<String>();
		
		System.out.print(currentWord + "\n" + i + "回目 「");	
		String newWord = inputWord(getLastCharacter(currentWord));
		
		while(getLastCharacter(newWord) != 'ん') {
			
			if (newWord.strip().length() > 1) {			// 2文字以上チェック
				
				if (words.contains(newWord)) {			// 同じ言葉チェック
					System.out.println("同じ言葉です。もう一回入力してください。");
				} else {
					
					if(checkCharacter(getFirstCharacter(newWord), getLastCharacter(currentWord))) {
						currentWord = newWord;
						words.add(newWord);
						i++;
					} else {
						System.out.println("違います。もう一回入力してください。	");
					}
				}
			} else {
				System.out.println("2文字以上入力してください。");
			}
			System.out.print(i + "回目 「");
			newWord = inputWord(getLastCharacter(currentWord));
		}
		
		System.out.println("負けました。「ん」で終わってはいけません。");
	}
	
	/*
	 * 新しい言葉を入力する（カタカナの場合、ひらがなに交換）
	 * @param  Char		前回の言葉の最後の文字
	 * @return String	新しい言葉
	 */
	static String inputWord(char lastCharacter) {
		System.out.print(lastCharacter + "」 で始まる言葉 ： ");
		Scanner scan = new Scanner(System.in);
		String word = scan.nextLine();
		
		if (!word.strip().equals("")) {
			StringBuffer sb = new StringBuffer(word);
			for (int i = 0; i < word.length(); i++) {
				char code = word.charAt(i);
				if ((code >= 0x30a1) && (code <= 0x30f3)) {
					sb.setCharAt(i, (char)(code - 0x60));
				}
				if ((code >= 0x3000) && (code <= 0x301C)) {		// りんご。 → りんご
					sb.setCharAt(i, '　');
				}
			}
			word = sb.toString().strip();
		}
		return word;
	}
	
	/*
	 * ヌルではない言葉の最後の文字をゲットする
	 * @param  String 	前回の言葉
	 * @return char		前回の言葉の最後の文字
	 */
	static char getLastCharacter(String str) {
		char lastChar = '　';
		if (!str.strip().equals("")) {
			lastChar = toUpperCharacter(str.charAt(str.length()-1));
			
			if (lastChar == 'ー') {
				lastChar = changeLongVowel(toUpperCharacter(str.charAt(str.length()-2)));
			}
		}
		return lastChar;
	}
	
	/*
	 * 最初の文字をゲットする
	 * @param  String 	新しい言葉
	 * @return char 	新しい言葉の最初の文字
	 */
	static char getFirstCharacter(String str) {
		return str.charAt(0);
	}
	
	/*
	 * 最初の文字と最後の文字をチェックする
	 * 
	 * @param  char		最初の文字、最後の文字
	 * @return boolean	結果
	 */
	static boolean checkCharacter(char head, char tail) {
		if (head == tail) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * 小さいかなを大きいかなにする
	 * @param  char 文字
	 * @return char 大きい文字
	 */
	static char toUpperCharacter(char lastChar) {
		return switch(lastChar) {
			case 'ゃ', 'ゅ', 'ょ', 'ぁ', 'ぃ', 'ぅ', 'ぇ', 'ぉ' -> (char)(lastChar+1);
			default -> lastChar;
		};
	}
	
	/*
	 * カタカナの長音を「あ、い、う、え、お」に変える。例えば、インタビュー → う
	 * @param  char 文字
	 * @return char 「あ、い、う、え、お」のいずれ
	 */
	static char changeLongVowel(char lastChar) {
		return switch(lastChar) {
		case 'か', 'さ', 'た', 'な', 'は', 'ま', 'や', 'ら', 'わ', 'が', 'ざ', 'だ', 'ば', 'ぱ' -> 'あ';
		case 'き', 'し', 'ち', 'に', 'ひ', 'み', 'り', 'ぎ', 'じ', 'ぢ', 'び', 'ぴ' -> 'い';
		case 'く', 'す', 'つ', 'ぬ', 'ふ', 'む', 'ゆ', 'る', 'ぐ', 'ず', 'づ', 'ぶ', 'ぷ' -> 'う';
		case 'け', 'せ', 'て', 'ね', 'へ', 'め', 'れ', 'げ', 'ぜ', 'で', 'べ', 'ぺ' -> 'え';
		case 'こ', 'そ', 'と', 'の', 'ほ', 'も', 'よ', 'ろ', 'を', 'ご', 'ぞ', 'ど', 'ぼ', 'ぽ' -> 'お';
		default -> lastChar;
	};
	}
	
}
