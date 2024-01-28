import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class URLShortener {
    private HashMap<String, String> shortToLongMap;
    private HashMap<String, String> longToShortMap;
    private final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private final int shortURLLength = 6;

    public URLShortener() {
        shortToLongMap = new HashMap<>();
        longToShortMap = new HashMap<>();
    }

    public String shortenURL(String longURL) {
        if (longToShortMap.containsKey(longURL)) {
            return longToShortMap.get(longURL);
        }

        String shortURL = generateShortURL();
        shortToLongMap.put(shortURL, longURL);
        longToShortMap.put(longURL, shortURL);

        return shortURL;
    }

    public String expandURL(String shortURL) {
        return shortToLongMap.getOrDefault(shortURL, "Short URL not found!");
    }

    private String generateShortURL() {
        Random rand = new Random();
        StringBuilder shortURL = new StringBuilder();

        for (int i = 0; i < shortURLLength; i++) {
            int index = rand.nextInt(characters.length());
            shortURL.append(characters.charAt(index));
        }

        // Check for collision, generate new short URL if collision occurs
        while (shortToLongMap.containsKey(shortURL.toString())) {
            shortURL = new StringBuilder();
            for (int i = 0; i < shortURLLength; i++) {
                int index = rand.nextInt(characters.length());
                shortURL.append(characters.charAt(index));
            }
        }

        return shortURL.toString();
    }

    public static void main(String[] args) {
        URLShortener shortener = new URLShortener();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Shorten URL");
            System.out.println("2. Expand URL");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter the long URL:");
                    String longURL = scanner.nextLine();
                    String shortURL = shortener.shortenURL(longURL);
                    System.out.println("Shortened URL: " + shortURL);
                    break;
                case 2:
                    System.out.println("Enter the short URL:");
                    String inputShortURL = scanner.nextLine();
                    String expandedURL = shortener.expandURL(inputShortURL);
                    System.out.println("Expanded URL: " + expandedURL);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
