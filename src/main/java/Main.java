import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public Main(String fileName, int topN) throws IOException {
        //Complete this constructor
        String str = String.valueOf(Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8));
        String[] s = str.replaceAll("\\p{Punct}", "").toLowerCase().split(" ");
        List<String> list = Arrays.asList(s);

        computeAvgLengthByFirstChar(list);

        Set pairs = calculateMinPairDist(list, topN);

        ArrayList pairs2 = new ArrayList(pairs);
        for (int i = 0; i < pairs2.size(); i++) {
            System.out.println(pairs2.get(i)+"}");
        }

    }


    private void computeAvgLengthByFirstChar(List<String> list) {

        Map<Character, Integer> treemap = new TreeMap<>();
        Map<Character, Integer> treemap2 = new TreeMap<>();
        ListIterator<String> it = list.listIterator();
        int i = 0;
        char c;
        int lettersum;
        while (it.hasNext()) {
            if (!list.get(i).isEmpty()) {
                c = list.get(i).charAt(0);
                lettersum = list.get(i).length();
                if (treemap.containsKey(c)) {
                    treemap.replace(c, treemap.get(c) + lettersum);
                    treemap2.replace(c, treemap2.get(c) + 1);
                } else {
                    treemap.put(c, lettersum);
                    treemap2.put(c, 1);
                }
            }
            it.next();
            i++;
        }

        Set<Character> keySet = treemap.keySet();
        ArrayList<Character> listOfKeys = new ArrayList<>(keySet);
        Collection<Integer> values = treemap.values();
        ArrayList<Integer> listOfValues = new ArrayList<>(values);
        Collection<Integer> values2 = treemap2.values();
        ArrayList<Integer> listOfValues2 = new ArrayList<>(values2);
        int deger = 0;
        System.out.println(" InitialCharacter " + " " + " AverageLength ");
        Iterator it2 = listOfKeys.iterator();
        while (it2.hasNext()) {
            char key = listOfKeys.get(deger);
            double avr = listOfValues.get(deger) * 1.0 / listOfValues2.get(deger);
            System.out.println("\t" + key + " \t\t " + avr);
            deger++;
            it2.next();
        }


    }

    private Set calculateMinPairDist(List<String> list, int topN) {

        Map<String, Double> pair = new HashMap<>();
        double distance;
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).equals(list.get(j))) continue;
                else {
                    distance = getFactor(list.get(i), list.get(j), list);
                    pair.put("Pair{t1=" + list.get(i) + ", t2=" + list.get(j) + ", factor", distance);
                }
            }
        }


        TreeSet<Map.Entry<String, Double>> sorted1 = new TreeSet<>(new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                if (o1.getValue() < o2.getValue()) return 1;
                if (o1.getValue() > o2.getValue()) return -1;
                else return 1;

            }
        });
        sorted1.addAll(pair.entrySet());

        LinkedHashSet<Map.Entry<String, Double>> topnvalue = new LinkedHashSet<>();
        for (int i = 0; i < topN; i++) {
            topnvalue.add(sorted1.pollFirst());
        }

        return topnvalue;

    }

    private double getFactor(String term1, String term2, List<String> list) {
        int freq1 = Collections.frequency(list, term1);
        int freq2 = Collections.frequency(list, term2);
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(term1)) {
                for (int j = i; j < list.size(); j++) {
                    if (list.get(j).equals(term2)) {
                        sum = sum + j - i;
                        break;
                    }
                }
            }

        }
        return (freq1 * freq2) / (1 + Math.log(sum));
    }


    public static void main(String[] args) throws IOException {
        new Main(args[0], Integer.parseInt(args[1]));


    }


}
