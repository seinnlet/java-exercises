package shiritori_2players;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ShiritoriGame {

    static String errorMessage;
    
    static void init() {
    	System.out.println("しりとりゲーム\n（ひらがなかカタカナで入力してください）\n-------");
    }
    
    // クライアントとサーバー、順番で遊ぶ
    static void play(PrintWriter output, BufferedReader input) throws IOException {
        String currentWord = "しりとり";
        ArrayList<String> words = new ArrayList<>();
        
        serverLoop:
        while (true) {
            // Client's turn
            output.println(currentWord + "　「" + getLastCharacter(formatWord(currentWord)) + "」で始まる言葉");
            String newWord = input.readLine();

            while (!isValidWord(newWord, currentWord, words)) {
                output.println(errorMessage);
                System.out.print("Client: " + newWord);
                
                if (getLastCharacter(formatWord(newWord)) == 'ん') {
	                System.out.println("（クライアントの負け、君の勝ち！）");
	                break serverLoop;
	            }
                System.out.println();
                newWord = input.readLine();
            }
            System.out.println("Client: " + newWord);
            currentWord = newWord;
            words.add(newWord);

            // Server's turn
            System.out.print("Your turn: 「" + getLastCharacter(formatWord(currentWord)) + "」で始まる言葉 ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            newWord = reader.readLine();
            
            while (!isValidWord(newWord, currentWord, words)) {
                System.out.println(errorMessage);
                
                if (getLastCharacter(formatWord(newWord)) == 'ん') {
                	output.println(newWord + "（サーバーの負け、君の勝ち！）");
                	break serverLoop;
                }
                System.out.print("Your turn: ");
                newWord = reader.readLine();
            }

            currentWord = newWord;
            words.add(newWord);
        }
        
        System.out.println("\n-------ゲーム終了-------");
    }
    
    // 入力した言葉をチェックする
    static boolean isValidWord(String newWord, String currentWord, ArrayList<String> usedWords) {
    	if (getLastCharacter(formatWord(newWord)) == 'ん') {
            errorMessage = "負けました。「ん」で終わってはいけません。";
            return false;
        }
    	
    	if (newWord.strip().length() <= 1) {
    		errorMessage = "2文字以上の「"+ getLastCharacter(formatWord(currentWord)) +"」で始まる言葉を入力してください。";
            return false;
        }
        
        if (!checkCharacter(getFirstCharacter(formatWord(newWord)), getLastCharacter(formatWord(currentWord)))) {
        	errorMessage = "違います。もう一回「"+ getLastCharacter(formatWord(currentWord)) +"」で始まる言葉を入力してください。";
            return false;
        }

        if (usedWords.contains(newWord)) {
        	errorMessage = "同じ言葉です。もう一回「"+ getLastCharacter(formatWord(currentWord)) + "」で始まる言葉を入力してください。";
            return false;
        }
        
        if (!checkWordByWeblio(newWord)) {
			errorMessage = "辞書で見つかりません。もう一回「"+ getLastCharacter(formatWord(currentWord)) + "」で始まる言葉を入力してください。";
        	return false;
		}
        
        return true;
    }
    
    // カタカナをひらがなに交換、スペースやコンマなどを消す
    static String formatWord(String word) {
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

    // 最後の文字を求める
    static char getLastCharacter(String str) {
        char lastChar = '　';
        if (!str.strip().equals("")) {
            lastChar = toUpperCharacter(str.charAt(str.length() - 1));

            if (lastChar == 'ー') {
                lastChar = changeLongVowel(toUpperCharacter(str.charAt(str.length() - 2)));
            }
        }
        return lastChar;
    }
    
    // 最初の文字を求める
    static char getFirstCharacter(String word) {
		return word.charAt(0);
	}
    
    // 最初の文字と最後の文字をチェックする
    static boolean checkCharacter(char head, char tail) {
        return head == tail;
    }
    
    // 小文字を大文字に交換する
    static char toUpperCharacter(char lastChar) {
        return switch (lastChar) {
            case 'ゃ', 'ゅ', 'ょ', 'ぁ', 'ぃ', 'ぅ', 'ぇ', 'ぉ' -> (char) (lastChar + 1);
            default -> lastChar;
        };
    }
    
    // カタカナの長音を「あ、い、う、え、お」に変える。例えば、インタビュー → う
    static char changeLongVowel(char lastChar) {
        return switch (lastChar) {
            case 'か', 'さ', 'た', 'な', 'は', 'ま', 'や', 'ら', 'わ', 'が', 'ざ', 'だ', 'ば', 'ぱ' -> 'あ';
            case 'き', 'し', 'ち', 'に', 'ひ', 'み', 'り', 'ぎ', 'じ', 'ぢ', 'び', 'ぴ' -> 'い';
            case 'く', 'す', 'つ', 'ぬ', 'ふ', 'む', 'ゆ', 'る', 'ぐ', 'ず', 'づ', 'ぶ', 'ぷ' -> 'う';
            case 'け', 'せ', 'て', 'ね', 'へ', 'め', 'れ', 'げ', 'ぜ', 'で', 'べ', 'ぺ' -> 'え';
            case 'こ', 'そ', 'と', 'の', 'ほ', 'も', 'よ', 'ろ', 'を', 'ご', 'ぞ', 'ど', 'ぼ', 'ぽ' -> 'お';
            default -> lastChar;
        };
    }
    
    // 言葉を辞書でチェックする
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
			System.out.println(e);
		}
        return true;
    }
}
