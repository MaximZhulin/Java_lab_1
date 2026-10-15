import java.util.ArrayList;
import java.util.Scanner;
//Жулин Максим Александрович 25КНТ-4
public class UserInput {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {
            System.out.println("Введите матрицу A (пустая строка - конец ввода):");
            ComplexMatrix matrix_1 = read_matrix(input);

            System.out.println("Выберите операцию:\n" +
                    "1)Сложение\n2)Вычитание\n3)Умножение\n4)Деление\n5)Транспонирование\n6)Нахождение определителя");
            String choice = input.nextLine().strip();

            if (choice.equals("5")) {
                System.out.println("Результат:\n" + matrix_1.transpose());
            } else if (choice.equals("6")) {
                System.out.println("Результат:\n" + matrix_1.determinant());
            } else if (choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4")) {
                System.out.println("Введите матрицу B:");
                ComplexMatrix matrix_2 = read_matrix(input);
                if (choice.equals("1")) {
                    System.out.println("Результат:\n" + matrix_1.add(matrix_2));
                } else if (choice.equals("2")) {
                    System.out.println("Результат:\n" + matrix_1.sub(matrix_2));
                } else if (choice.equals("3")) {
                    System.out.println("Результат:\n" + matrix_1.multiply(matrix_2));
                } else {
                    System.out.println("Результат:\n" + matrix_1.divide(matrix_2));
                }
            } else {
                System.out.println("Неизвестная операция");
            }
        } catch (IllegalArgumentException | ArithmeticException error) {
            System.out.println("Ошибка: " + error.getMessage());
        }
    }

    public static ComplexMatrix read_matrix(Scanner input) {
        ArrayList<Complex[]> rows = new ArrayList<>();
        String line = input.nextLine();
        line = line.strip();
        while (line.isEmpty() == false) {
            ArrayList<Complex> row = new ArrayList<>();
            String word = "";
            for (int i = 0; i <= line.length(); i++) {
                if (i == line.length() || line.charAt(i) == ' ') {
                    if (word.isEmpty() == false) {
                        row.add(to_complex(word));
                        word = "";
                    }
                } else {
                    word += line.charAt(i);
                }
            }
            rows.add(row.toArray(new Complex[0]));
            line = input.nextLine();
            line = line.strip();
        }
        return new ComplexMatrix(rows.toArray(new Complex[0][]));
    }

    public static Complex to_complex(String str) {
        if (str.endsWith("i") == false) {
            return new Complex(Double.parseDouble(str), 0);
        }
        String skelet = str.substring(0, str.length() - 1);
        int sign = -1;
        if(skelet.lastIndexOf('+') != -1){
            sign = skelet.lastIndexOf('+');
        }else if(skelet.lastIndexOf('-') != -1){
            sign = skelet.lastIndexOf('-');
        }
        double real = 0;
        String str_imz = skelet;
        if (sign > 0) {
            real = Double.parseDouble(skelet.substring(0, sign));
            str_imz = skelet.substring(sign);
        }
        double imaginary;
        if (str_imz.isEmpty() || str_imz.equals("+")) {
            imaginary = 1;
        } else if (str_imz.equals("-")) {
            imaginary = -1;
        } else {
            imaginary = Double.parseDouble(str_imz);
        }
        return new Complex(real, imaginary);
    }
}