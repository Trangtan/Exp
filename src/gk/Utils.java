package gk;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Utils {
    String start = "S";
    ArrayList<Rule> grammar = new ArrayList<Rule>();
    Map<String, Integer> map = new TreeMap<String, Integer>();

    // remove indirect recursion
    public void removeStepOne() {

        for (int i = 0, len = grammar.size(); i < len; i++) {
            for (int j = 0; j < i; j++) {
                ArrayList<String> generated = new ArrayList<String>();

                TreeSet<String> iGenRight = grammar.get(i).getRight();
                Iterator<String> iter = iGenRight.iterator();

                char temp = grammar.get(j).getLeft().charAt(0);
                while (iter.hasNext()) {
                    String nextRuleRight = iter.next();
                    if (nextRuleRight.charAt(0) == temp) {
                        Iterator<String> iterAnother = grammar.get(j).getRight().iterator();
                        while (iterAnother.hasNext()) {
                            generated.add(iterAnother.next() + nextRuleRight.substring(1));
                        }

                    }
                }

                for (int iAno = 0, lenAno = grammar.get(i).getRight().size(); iAno < lenAno; iAno++) {
                    String toBeRemoved = iGenRight.pollFirst();
                    if (toBeRemoved.charAt(0) != temp) {
                        grammar.get(i).getRight().add(toBeRemoved);
                    }
                }

                for (int iAno = generated.size(); iAno > 0; iAno--) {
                    grammar.get(i).getRight().add(generated.get(iAno-1));
                }
            }
        }

        checkFlag();
    }

    public boolean validation() {
        Set set1 = new HashSet();
        Set set2 = new HashSet();

        for (int i = 0, len = grammar.size(); i < len; i++) {
            char ch = grammar.get(i).getLeft().charAt(0);
            set1.add(ch);
            TreeSet<String> temp = grammar.get(i).getRight();
            Iterator<String> iter = temp.iterator();
            while (iter.hasNext()) {
                ch = iter.next().charAt(0);
                if (Character.isUpperCase(ch)) {
                    set2.add(ch);
                }
            }
        }

        if (set1.size() == set2.size()){
            for (int i = 0; i< set1.size(); i++){
                if (set2.contains(set1.iterator().next()))
                    continue;
                else
                    return false;
            }
            return true;
        }
        return false;
    }

    public boolean checkType() {
        for (int i = 0, len = grammar.size(); i < len; i++) {
            char ch = grammar.get(i).getLeft().charAt(0);
            TreeSet<String> temp = grammar.get(i).getRight();
            Iterator<String> iter = temp.iterator();
            while (iter.hasNext()) {
                if (iter.next().charAt(0) == ch) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkFlag() {

            char ch = grammar.get(0).getLeft().charAt(0);
            TreeSet<String> temp = grammar.get(0).getRight();
            Iterator<String> iter = temp.iterator();
            while (iter.hasNext()) {
                if (iter.next().charAt(0) == ch) {
                    return true;
                }
                grammar.remove(0);
            }

        return false;
    }


    public void removeStepTwo() {

            char ch = grammar.get(0).getLeft().charAt(0);
            TreeSet<String> temp = grammar.get(0).getRight();
            Iterator<String> iter;

            String newVN = ch + "'";
            grammar.add(new Rule(newVN));
            grammar.get(grammar.size()-1).add("~");
            map.put(newVN, grammar.size());

            TreeSet<String> generated = new TreeSet<String>();
            iter = temp.iterator();
            while (iter.hasNext()) {
                String ss = iter.next();
                if (ss.charAt(0) == ch) {
                    generated.add(ss.substring(1) + newVN);
                    grammar.get(grammar.size()-1).add(ss.substring(1) + newVN);
                } else {
                    generated.add(ss + newVN);
                }
            }

            temp.clear();
            for (String string : generated) {
                temp.add(string);
            }

        }

    public void parseInputFromFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        String curLine = scanner.next();
        String left ="", right = "";
        while (scanner.hasNext()) {
            curLine = scanner.next();
            left = curLine.substring(0, curLine.indexOf(":"));
            right = curLine.substring(curLine.indexOf("=") + 1);
            if (map.containsKey(left)) {
                grammar.get(grammar.size()-1).add(right);
            } else {
                grammar.add(new Rule(left));
                grammar.get(grammar.size()-1).add(right);
                map.put(left, grammar.size());
            }

        }
        scanner.close();
    }

    public void print() {
        for (Rule rule : grammar) {
            rule.print();
        }
    }
}