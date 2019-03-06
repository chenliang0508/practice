package cn.com.upcard.lintcode;

import java.util.Scanner;
/**
*无论是反转数字还是反转字符串，可以使用栈 的结构，栈是先进后出，可以把数字或者字符串转换成char[],然后循环入栈，再出栈
*/
public class ReverseInteger {

	public static void main(String[] args) {
		while(true) {
			System.out.println("输入3为整数");
			Scanner sc = new Scanner(System.in);
			int number = Integer.valueOf(sc.next());
			System.out.println(reverseInteger(number));
		}
		
	}
	/**
     * @param number: A 3-digit number.
     * @return: Reversed number.
     */
    public static int reverseInteger(int number) {
        // write your code here
        if(number < 100 || number >= 1000) {
            throw new RuntimeException("number should be a 3-digit");
        }
        //00结尾 被100整除
        if(number % 100  == 0) {
            return number / 100;
        }
        //以0结尾，被10整除
        if(number % 10  == 0) {
            int waitReverseNumber = number / 10;
            //两位数翻转,没有0
            int ge = waitReverseNumber % 10;
            int shi = waitReverseNumber / 10;
            return ge * 10 + shi;
        }
        //3位翻转,十位数不变，个位和百位改变
        int bai = number / 100;
        int shi = (number % 100) / 10;
        int ge = (number % 100) % 10;
        return ge * 100 + shi * 10 + bai;
    }
}
