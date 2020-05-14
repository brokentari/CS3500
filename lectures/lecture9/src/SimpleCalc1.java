import java.util.Scanner;

interface SimpleCalc {
  int add(int num1 , int num2);
}

class Calculator implements SimpleCalc {

  public int add(int num1, int num2) {
    return num1 + num2;
  }
}

 class SimpleCalc1 {
  public static void main(String[] args) {
    int num1, num2;
    Scanner scan = new Scanner(System.in);

    num1 = scan.nextInt();
    num2 = scan.nextInt();

    System.out.printf("%d", new Calculator().add(num1, num2));
  }
}
