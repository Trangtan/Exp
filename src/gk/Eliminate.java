package gk;

import java.io.FileNotFoundException;

public class Eliminate {
    public static void main(String[] args) throws FileNotFoundException {
        Utils utils = new Utils();

        utils.parseInputFromFile("C:\\Users\\Yearning\\Java\\Exp\\src\\test.txt");
        System.out.println("The input rule set:");
        utils.print();

        if (!utils.validation()){
            System.out.println();
            System.out.println("\nNot A Invalid Left Recursion Set");
            return ;
        }

        if (utils.checkType()){
            System.out.println("\nThis is a direct recursion rule set.");
            utils.removeStepTwo();
            System.out.println();
            System.out.println("\nThe rule set after direct recursion remove:");
            utils.print();
        }else{
            System.out.println("\nThis is a indirect recursion rule set.");
            utils.removeStepOne();
            System.out.println("\nThe rule set after indirect recursion remove:");
            utils.print();

            utils.removeStepTwo();
            System.out.println("\nThe rule set after direct recursion remove:");
            utils.print();
        }
    }
}
