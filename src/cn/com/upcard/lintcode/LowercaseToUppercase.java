package cn.com.upcard.lintcode;

public class LowercaseToUppercase {
	public static void main(String[] args) {
		System.out.println(lowercaseToUppercase('a'));
	}
	static char lowercaseToUppercase(char character) {
		System.out.println(character);
		System.out.println(character - 32);
		return (char) (character - 32);
    }
}
