package com.techlink.interview_java_test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AllProgramStore {

    private static final System.Logger LOG = System.getLogger(AllProgramStore.class.getName());

    private AllProgramStore() {
    }

    static void main(String[] args) {
        System.out.println("Hello Test Program....!!");
        LOG.log(System.Logger.Level.INFO, "Starting AllProgramStore with args: {0}", Arrays.toString(args));

        countOccurrences();                             // Count occurrences in strings / numbers
        System.out.println("31 is prime? " + isPrimeNumber(31));
        printPrimeNumbers();                         // Print primes up to limit
        checkLongestRepeatingSequence();                // Longest repeating substring
        getUniqueWordFromString();                      // Unique/distinct words
        pairNumbersHavingSum();                         // Pairs summing to target
        findLastNonRepatedChar();                      // Last non-repeating char

        LOG.log(System.Logger.Level.INFO, "Finished AllProgramStore execution.");
    }

    // ---------- Occurrence counting ----------
    private static void countOccurrences() {
        // words
        var wordsCsv = "Apple,Orange,Apple,Mango,Apple,Mango";
        Map<String, Long> wordCounts = Arrays.stream(wordsCsv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));
        System.out.println("wordCounts = " + wordCounts); // {Apple=3, Orange=1, Mango=2}

        // numbers
        var numbersCsv = " 1,2, 3,1,2, 3 ,4 ,5 ";
        Map<Integer, Long> numberCounts = Arrays.stream(numbersCsv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));
        System.out.println("numberCounts = " + numberCounts); // {1=2, 2=2, 3=2, 4=1, 5=1}

        // char frequency (letters)
        var letters = "abcbadbabcbab";
        Map<Character, Long> charCounts = letters.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));
        System.out.println("charCounts = " + charCounts); // {a=4, b=6, c=2, d=1}

        // digit frequency
        var digits = "123232413253598976";
        Map<Character, Long> digitCounts = digits.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()));
        System.out.println("digitCounts = " + digitCounts);
    }

    // ---------- Prime helpers ----------
    private static boolean isPrimeNumber(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;            // 2 and 3
        if (n % 2 == 0 || n % 3 == 0) return false;

        // 6k ± 1 optimization
        for (int i = 5; (long) i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    private static void printPrimeNumbers() {
        System.out.println("Primes up to " + 101 + ":");
        IntStream.rangeClosed(2, 101)
                .filter(AllProgramStore::isPrimeNumber)
                .forEach(System.out::println);
    }

    // ---------- Longest repeating substring (suffix-array style) ----------
    private static void checkLongestRepeatingSequence() {
        String str = "abracadabra";
        String longest = longestRepeatingSubstring(str);
        System.out.println("Longest repeating sequence: " + longest);
    }

    private static String longestRepeatingSubstring(String s) {
        int n = s.length();
        if (n <= 1) return "";

        // Build all suffixes with starting index
        String[] suffixes = new String[n];
        for (int i = 0; i < n; i++) suffixes[i] = s.substring(i);

        // Sort suffixes lexicographically
        Arrays.sort(suffixes);

        // Compare adjacent suffixes for LCP
        String best = "";
        for (int i = 1; i < n; i++) {
            String lcp = lcp(suffixes[i - 1], suffixes[i]);
            if (lcp.length() > best.length()) best = lcp;
        }
        return best;
    }

    private static String lcp(String a, String b) {
        int len = Math.min(a.length(), b.length());
        int i = 0;
        while (i < len && a.charAt(i) == b.charAt(i)) i++;
        return a.substring(0, i);
    }

    // ---------- Unique / distinct words ----------
    private static void getUniqueWordFromString() {
        // Distinct (preserve first-seen order)
        List<String> osList = List.of("Microsoft Windows", "Mac OS", "GNU Linux", "Free BSD", "GNU Linux", "Mac OS");
        List<String> distinct = osList.stream().distinct().collect(Collectors.toList());
        System.out.println("distinct = " + distinct); // [Microsoft Windows, Mac OS, GNU Linux, Free BSD]

        // Words that occur exactly once in the sentence
        String sentence = "Welcome to geeks for geeks";
        List<String> uniqueOnce = Arrays.stream(sentence.split("\\s+"))
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() == 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        System.out.println("uniqueOnce = " + uniqueOnce); // [Welcome, to, for]
    }

    // ---------- Pairs with target sum (unique, order-independent) ----------
    private static void pairNumbersHavingSum() {
        int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        int sum = 13;

        Set<Integer> seen = new HashSet<>();
        Set<String> printed = new HashSet<>();

        for (int val : arr) {
            int comp = sum - val;
            if (seen.contains(comp)) {
                int a = Math.min(val, comp);
                int b = Math.max(val, comp);
                String key = a + ":" + b;
                if (printed.add(key)) {
                    System.out.println("Pair : " + a + ", " + b);
                }
            }
            seen.add(val);
        }
    }

    // ---------- Last non-repeating character ----------
    private static void findLastNonRepatedChar() {
        String s = "swqiss"; // expected: i
        Optional<Character> lastUnique = s.chars()
                .mapToObj(i -> (char) i)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() == 1L)
                .map(Map.Entry::getKey)
                .reduce((first, second) -> second); // take last

        System.out.println("findLastNonRepatedChar : " + lastUnique.orElse(null));
    }

    // ---------- Demo: list of maps + flatMap with filtering ----------
    private static void listOfMapWithFilterAndFlatMap() {
        List<Map<String, String>> collData = new ArrayList<>();

        Map<String, String> stuMap1 = new HashMap<>();
        stuMap1.put("S1", "Course_1");
        stuMap1.put("S2", "Course_2");
        stuMap1.put("S3", "Course_1");
        stuMap1.put("S4", "Course_2");
        stuMap1.put("S5", "Course_1");
        stuMap1.put(null, "College_A");
        collData.add(stuMap1);

        Map<String, String> stuMap2 = new HashMap<>();
        stuMap2.put("Q1", "Course_1");
        stuMap2.put("Q2", "Course_2");
        stuMap2.put("Q3", "Course_1");
        stuMap2.put("Q4", "Course_2");
        stuMap2.put("Q5", "Course_1");
        stuMap2.put(null, "College_B");
        collData.add(stuMap2);

        // Extract all entries with value "Course_1" into a single list
        List<Map.Entry<String, String>> course1Entries = collData.stream()
                .flatMap(map -> map.entrySet().stream())
                .filter(e -> e.getKey() != null)                // guard against null keys
                .filter(e -> "Course_1".equals(e.getValue()))
                .collect(Collectors.toList());

        course1Entries.forEach(e -> System.out.println(e.getKey() + " : " + e.getValue()));
    }
}
