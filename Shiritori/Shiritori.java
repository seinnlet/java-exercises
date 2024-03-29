package shiritori;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Shiritori {

	public static void main(String[] args) {
		
		init();
		play("しりとり");
		
	}
	
	static void init() {
		System.out.println("しりとり（ひらがなかカタカナで入力してください）");
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
						
						if (checkWordByWeblio(newWord)) {		// 辞書でチェック	
							currentWord = newWord;
							words.add(newWord);
							i++;
						} else {
							System.out.println("辞書で見つかりません。もう一回入力してください。");
						}
					} else {
						System.out.println("違います。もう一回入力してください。");
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
	static char getLastCharacter(String word) {
		char lastChar = '　';
		if (!word.strip().equals("")) {
			lastChar = toUpperCharacter(word.charAt(word.length()-1));
			
			if (lastChar == 'ー' && word.length() >= 2) {
				lastChar = changeLongVowel(toUpperCharacter(word.charAt(word.length()-2)));
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
	
	/*
	 * 言葉を辞書でチェックする
	 * @param  String 言葉
	 * @return boolean 辞書であったら true、なかったら false
	 */
	static boolean checkWordByWeblio(String word) {
		URL url = null;
        try {
			url = new URL("https://www.weblio.jp/content/" + word);
		} catch (MalformedURLException e) {
			System.err.println(e);
		}
        
        try (InputStreamReader is  = new InputStreamReader(url.openStream());) {
			BufferedReader br = new BufferedReader(is);
			StringBuilder content = new StringBuilder();
			String s;
			while ((s = br.readLine()) != null) {
				content.append(s);
			}
			if (content.toString().contains("一致する見出し語は見つかりませんでした")) {
				return false;
			}
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println(e);
		}
        return true;
	}
}
