package es.joseluisgs.dam;

import es.joseluisgs.dam.sax.SAXController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String INPUT_XML = System.getProperty("user.dir") + File.separator + "data" + File.separator + "users.xml";
        System.out.println(INPUT_XML);

        try {
            System.out.println("Cargamos nuestros Datos usando SAX desde fichero");
            SAXController controller = SAXController.getInstance(INPUT_XML);
            controller.loadData();

            System.out.println("*** SAX XML *** ");

            System.out.println("Listado");
            System.out.println("Todos");
            controller.getUsers().forEach(System.out::println);

            System.out.println("Todos Mayores de 40");
            controller.getUsers().stream().filter(user -> user.getAge() >= 40).forEach(System.out::println);
            System.out.println();

        } catch (SAXException | ParserConfigurationException | IOException e) {
            System.err.println("ERROR:" + e.getMessage());
        }


    }
}
