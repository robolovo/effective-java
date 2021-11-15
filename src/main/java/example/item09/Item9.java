package example.item09;

import java.io.*;
import java.util.Scanner;

/*
 *   아이템 9. try-finally보다는 try-with-resources를 사용하라.
 *
 *   ⚠️ try-with-resources는 자바7 부터 도입된 문법이다.
 *
 *   꼭 회수해야 하는 자원을 다룰 때는 try-finally 말고, try-with-resources를 사용하자.
 *   예외는 없다. 코드는 더 짧고 분명해지고, 만들어지는 예외 정보도 훨씬 유용하다.
 *   try-finally로 작성하면 실용적이지 못할 만큼 코드가 지저분해지는 경우라도, try-with-resources로는 정확하고 쉽게 자원을 회수하 수 있다.
 */
public class Item9 {
    public static void main(String[] args) throws IOException {

        /*
        *   try-finally
        */
        PrintWriter pw = new PrintWriter(System.out);
        try {
            // 생략 ...
        } finally {
            pw.close();
        }

        /*
         *   try-with-resources
         *   ⚠ 이 구조를 사용하려면 해당 자원이 AutoCloseable 인터페이스를 구현해야한다.
         *      AutoCloseable은 단순히 void를 반환하는 close메소드 하나만 정의해 놓은 인터페이스이다.
         *      닫아야하는 자원을 뜻하는 클래스를 직접 작성한다면 AutoCloseable을 반드시 구현하자.
         */
        try (PrintWriter pwr = new PrintWriter(System.out);
             Scanner sc = new Scanner(System.in)) {  // resource는 try문 안에서 선언되고 초기화 되어야한다. 복수의 resources를 처리할 수 있다.

            // 생략 ...
        }

        /*
        *   try-with-resources
        *   자바 버전 9 이후부터 가능해진 문법이다.
       */
        PrintWriter printWriter = new PrintWriter(System.out);
        Scanner scanner = new Scanner(System.in);
        try (printWriter;scanner) {
            // 생략 ...
        }
    }
}
