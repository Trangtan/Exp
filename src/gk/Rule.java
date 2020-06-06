package gk;

import java.util.Iterator;
import java.util.TreeSet;

public class Rule {
    String left;
    TreeSet<String> right = new TreeSet<String>();

    Rule() {}
    Rule(String left) {
        this.left = left;
    }

    public void add(String s) {
        right.add(s);
    }

    public TreeSet<String> getRight() {
        return right;
    }

    public String getLeft() {
        return left;
    }

    public void print() {
        Iterator<String> iter = right.iterator();
        System.out.print(left + "::=" + iter.next());
        while (iter.hasNext()) {
            System.out.print("|" + iter.next());
        }
        System.out.println();
    }
}
