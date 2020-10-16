package ml.molive.scchallengeemail;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        URL url = new URL("https://www.ecs.soton.ac.uk/people/" + name);
        Scanner web = new Scanner(url.openStream()).useDelimiter("\\s*\\|");
        System.out.println(web.next().split("<title>")[1]);
    }
}
