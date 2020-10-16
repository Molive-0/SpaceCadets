package ml.molive.scchallengeemail;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class MainSmol {
    public static void main(String[] args) throws IOException {
        System.out.println(new Scanner(new URL("https://www.ecs.soton.ac.uk/people/" + new Scanner(System.in).nextLine()).openStream()).useDelimiter("\\s*\\|").next().split("<title>")[1]);
    }
}
