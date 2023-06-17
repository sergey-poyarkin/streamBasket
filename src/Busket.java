import java.io.*;
import java.util.Arrays;

public class Busket implements Serializable{

    private static final long serialVersionUID = 1L;
    private String[] products;
    private int[] prices;
    private int[] numOfProducts;
    private int sumProducts = 0;

    public Busket() {

    }

    public Busket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.numOfProducts = new int[products.length];
    }

    public void addToCart(int productNum, int amount) {
        numOfProducts[productNum] += amount;
    }

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(textFile)) {
            for (String product : products) {
                writer.append(product).append(" ");
            }
            writer.println();

            for (int price : prices) {
                writer.append(String.valueOf(price)).append(" ");
            }
            writer.println();

            for (int numOfProduct : numOfProducts) {
                writer.append(String.valueOf(numOfProduct)).append(" ");
            }
        }
    }

    public void printBusket () {
        System.out.println("Ваша корзина:");

        for (int t = 0; t < products.length; t++) { // вывод корзины
            if (numOfProducts[t] == 0) {
                continue;
            }
            System.out.println(products[t] + " " + numOfProducts[t] + " шт " + prices[t] + " руб/шт. "
                    + (numOfProducts[t] * prices[t]) + " руб в сумме");
            sumProducts += (numOfProducts[t] * prices[t]);
        }
        System.out.println("Итого товаров на сумму: " + sumProducts);
    }


    public static Busket loadFromTxtFile(File textFile) throws IOException {
        Busket busket = new Busket();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            String productLine= bufferedReader.readLine();
            String pricesLine= bufferedReader.readLine();
            String numOfProductsLine= bufferedReader.readLine();

            busket.products = productLine.split(" ");
            busket.prices = Arrays.stream(pricesLine.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();
            busket.numOfProducts = Arrays.stream(numOfProductsLine.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();

            return busket;
        }
    }

    public void saveBin(File file) throws IOException {
        try (ObjectOutputStream objStream = new ObjectOutputStream(new FileOutputStream(file))) {
            objStream.writeObject(this);
        }
    }

    public static Busket loadFromBinFile(File file) throws IOException, ClassNotFoundException {
        Busket busket = null;
        try (ObjectInputStream objInStr = new ObjectInputStream(new FileInputStream(file))) {
            busket = (Busket) objInStr.readObject();
        }
        return busket;
    }
}
