import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static String[] products = {"1.молоко", "2.греча", "3.хлеб", "4.яблоки"}; // массив продуктов
    static int[] prices = {60, 80, 40, 75}; // массив цен
    static int[] amountInBusket = new int[4]; // массив кол-ва продуктов
    //static int sumProducts = 0; // сумма корзины
    static File saving = new File("busket.txt");
    public static void main(String[] args) throws IOException {

        Busket busket = null;

        if (saving.exists()) {
            busket = Busket.loadFromTxtFile(saving);
        } else {
            busket = new Busket(products, prices);
        }


        Scanner sc = new Scanner(System.in);
        int i;

        for (i = 0; i < products.length; i++) { // вывод продуктов
            System.out.println("Товар: " + products[i] + ". Цена: " + prices[i] + " руб./шт");
        }

        while (true) {
            System.out.println("Введите номер продукта и его количество или введите end"); // интеракция
            String inputString = sc.nextLine();
            if (inputString.equals("end")) {

                busket.printBusket();
                break;
            }
            String[] inputData = inputString.split(" "); // оперативные данные
            int numOfProduct = Integer.parseInt(inputData[0]);
            int amountOfProduct = Integer.parseInt(inputData[1]);

            if (numOfProduct > 0 || numOfProduct <= products.length) { // запоминание кол-ва продуктов,
                amountInBusket[numOfProduct - 1] += amountOfProduct;
                busket.addToCart((numOfProduct - 1), amountOfProduct);
                busket.saveTxt(saving);
            }
        }
    }
}