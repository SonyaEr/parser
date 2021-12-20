package ua;

import java.io.InputStream;
import java.util.Scanner;

public class IOData {
    private final Scanner scan;

    public IOData(InputStream input) {
        this.scan = new Scanner(input);
    }


    public void printData(String text) {
        System.out.println(text);
    }

    public String inputData(){
        return scan.nextLine();
    }
}
