import java.util.Random;

public class Main {
    public static final int NUM_SHORT_WORDS = 100_000;

    public static void main(String[] args) throws InterruptedException {

        Random random = new Random();
        String[] texts = new String[NUM_SHORT_WORDS];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
            // System.out.println(texts[i]);
        }

        Thread polindromeThread = new Thread(() -> {
            for (String text : texts) {
                if (Criteria.isPolindrome(text) && !Criteria.isSameChar(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        polindromeThread.start();

        Thread someCharThread = new Thread(() -> {
            for (String text : texts) {
                if (Criteria.isSameChar(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        someCharThread.start();

        Thread ascendingOrderThread = new Thread(() -> {
            for (String text : texts) {
                if (Criteria.isAscendingOrder(text) && !Criteria.isPolindrome(text)) {
                    incrementCounter(text.length());
                }
            }
        });
        ascendingOrderThread.start();

        ascendingOrderThread.join();
        someCharThread.join();
        polindromeThread.join();

        System.out.println("Красивых слов с длиной 3: " + Criteria.counter3);
        System.out.println("Красивых слов с длиной 4: " + Criteria.counter4);
        System.out.println("Красивых слов с длиной 5: " + Criteria.counter5);

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static void incrementCounter(int textLength) {
        if (textLength == 3) {
            Criteria.counter3.getAndIncrement();
        } else if (textLength == 4) {
            Criteria.counter4.getAndIncrement();
        } else {
            Criteria.counter5.getAndIncrement();
        }
    }
}
