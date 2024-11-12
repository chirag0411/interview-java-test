package com.techlink.interview_java_test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AllProgramStore {
    public static void main(String[] args) {
        System.out.println("Hello Test Program....!!");
        countOccurrences(); // Count countOccurrences from String, array or List : Number, Char, words
        isPrimeNumber(31);
        printPrimeNumbers(101);
        checkLongestRepeatingSequence();
        getUniqueWordFromString();
        pairNumbersHavingSum();
        listOfMapWithFilterAndFlatMap();
        findLastNonRepatedChar();
    }

    private static void findLastNonRepatedChar() {
        String s = "swqiss"; // OutPut : i -> Last Non Repeating Character From String
        Character c = s.chars().mapToObj(i -> (char) i).collect(Collectors.groupingBy(Function.identity(),
                        LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream().filter(e -> e.getValue() == 1L)
                .map(entry -> entry.getKey()).reduce((first, second) -> second).get();
        System.out.println("findLastNonRepatedChar :  " + c);
    }

    private static void listOfMapWithFilterAndFlatMap() {
        List<Map<String, String>> collData = new ArrayList<>();

        // Map of student and course name
        Map<String, String> stuMap = new HashMap<>();
        // Add map 1
        stuMap.put("S1", "Course_1");
        stuMap.put("S2", "Course_2");
        stuMap.put("S3", "Course_1");
        stuMap.put("S4", "Course_2");
        stuMap.put("S5", "Course_1");
        stuMap.put(null, "College_A");
        collData.add(stuMap);

        // Add map 2
        stuMap = new HashMap<>();
        stuMap.put("Q1", "Course_1");
        stuMap.put("Q2", "Course_2");
        stuMap.put("Q3", "Course_1");
        stuMap.put("Q4", "Course_2");
        stuMap.put("Q5", "Course_1");
        stuMap.put(null, "College_B");
        collData.add(stuMap);

        // Extract all entries with value "Course_1" into a single list
        List<Map.Entry<String, String>> course1Entries = collData.stream()
                .flatMap(map -> map.entrySet().stream())
                .filter(entry -> "Course_1".equals(entry.getValue()))
                .collect(Collectors.toList());
        // Print the result
        course1Entries.forEach(entry -> System.out.println(entry.getKey() + " : " + entry.getValue()));
    }

    private static void countOccurrences() {
        String wordStr = "Apple,Orange,Apple,Mango,Apple,Mango"; // {Apple=3, Mango=2, Banna=1}
        //List<String> wordStr = List.of("Apple", "orange", "Apple");
        Map<String, Long> result = Arrays.asList(wordStr.split(",")).stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())); // {Apple=3, Mango=2, Banna=1}
        System.out.println(result);

        String numberStr = " 1,2, 3,1,2, 3 ,4 ,5 "; // {1=2, 2=2, 3=2, 4=1, 5=1}
        Map<Integer, Long> occurrences = Arrays.asList(numberStr.replace(" ", "").split("\\s*,\\s*"))
                .stream().map(Integer::parseInt).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())); // {Apple=3, Mango=2, Banna=1}
        System.out.println(occurrences);

        String str = "abcbadbabcbab"; // {a=4, b=6, c=2, d=1}
        Map<Character, Long> withSpace = str.chars().mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        System.out.println("withSpace " + withSpace); // withSpace {a=4, b=6, c=2, d=1}

        String charInt = "123232413253598976"; // {1=2, 2=4, 3=4, 4=1, 5=2, 6=1, 7=1, 8=1, 9=2}
        Map<Character, Long> charIntResult = charInt.chars().mapToObj(c -> (char) c) // Convert int to char
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(charIntResult);
    }

    private static boolean isPrimeNumber(int n) {
        if (n <= 1) return false; // Handle edge cases
        if (n <= 3) return true; // 2 and 3 are prime numbers

        // Check if n is divisible by any number from 2 to sqrt(n)
        return IntStream.rangeClosed(2, (int) Math.sqrt(n))
                .noneMatch(i -> n % i == 0);
    }

    private static void printPrimeNumbers(int limit) {
        IntStream.rangeClosed(2, limit) // Generate a stream of numbers from 2 to limit
                .filter(AllProgramStore::isPrimeNumber) // Filter out non-prime numbers
                .forEach(System.out::println); // Print each prime number
    }

    private static void checkLongestRepeatingSequence() {
        String str = "abracadabra";
        String longestRS = IntStream.range(0, str.length())
                .boxed() // Convert IntStream to Stream<Integer>
                .flatMap(i -> IntStream.range(i + 1, str.length())
                        .mapToObj(j -> lcp(str.substring(i), str.substring(j))))
                .max((s1, s2) -> Integer.compare(s1.length(), s2.length()))
                .orElse("");
        System.out.println("Longest repeating sequence: " + longestRS);
    }

    private static String lcp(String s, String t) {
        int n = Math.min(s.length(), t.length());
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != t.charAt(i)) {
                return s.substring(0, i);
            }
        }
        return s.substring(0, n);
    }

    private static void getUniqueWordFromString() {
        // get unique string from the inputs - string and using only from the group of words provided
        List<String> MY_LIST = Arrays.asList(new String[]{"Microsoft Windows", "Mac OS", "GNU Linux", "Free BSD", "GNU Linux", "Mac OS"});
        List<String> result = new ArrayList<>(new HashSet<>(MY_LIST));
        System.out.println(result); // [GNU Linux, Microsoft Windows, Mac OS, Free BSD]

        List<String> result1 = MY_LIST.stream().distinct().collect(Collectors.toList());
        System.out.println(result1); // [Microsoft Windows, Mac OS, GNU Linux, Free BSD]

        String str = "Welcome to geeks for geeks";
        String[] words = str.split(" ");
        List<String> al = new ArrayList<>(Arrays.asList(words));
        for (String x : al) {
            if (Collections.frequency(al, x) == 1) {
                System.out.println(x); // Welcome, to, for
            }
        }
    }

    private static void pairNumbersHavingSum() {
        int arr[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        int sum = 13;
        Set<Integer> pair = new HashSet<>();

        for (int i = 0; i < arr.length; i++) {
            int temp = sum - arr[i];
            if (pair.contains(temp)) {
                System.out.println("Pair : " + temp + ", " + arr[i]);
            }
            pair.add(arr[i]);
        }
    }
}
