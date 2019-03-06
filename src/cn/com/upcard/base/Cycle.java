package cn.com.upcard.base;

/**
 * ++ -- 运算符 在编译器中使用缓存变量机制；
 * 原始值存入 堆栈 暂存区，变量则进行自增操作，此时变量区的值进行了增加，最后将堆栈区中的值赋给变量区的变量
 * 例如：i++可表现为以下形式：
 * temp = i;
 * i = i++;
 * i = temp;
 * 
 * ++i可以表示以下形式：变量放入暂存区
 * i = i+1;
 * temp = i;
 * i = temp;
 * @author chenliang
 *
 */
public class Cycle {
	public static void main(String[] args) {
		int j = 0;
		for (int i = 0; i < 100; i++) {
//			j++;
//			System.out.print(j + "==>");
			j = j++;
			System.out.println(i + "---->" + j);
		}
		System.out.println(j);
	}
}
