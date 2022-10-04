package es.joseluisgs.dam;

import es.joseluisgs.dam.dom.DOMController;
import es.joseluisgs.dam.dom.model.User;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String INPUT_XML = System.getProperty("user.dir")+ File.separator+"data"+File.separator+"users.xml";
        String OUTPUT_XML = System.getProperty("user.dir")+ File.separator+"data"+File.separator+"users_updated.xml";
        System.out.println(INPUT_XML);
        // Cargamos el controlador e iniciamos el DOM
        try {
            System.out.println("Cargamos nuestros Datos y DOM desde fichero");
            DOMController controller = DOMController.getInstance(INPUT_XML);
            controller.loadData();
            System.out.println("Exportamos los datos a un XML");
            controller.printXML();
            controller.writeXMLFile(OUTPUT_XML);
            System.out.println();

            System.out.println("*** DOM XML *** ");


            System.out.println("Listado");
            System.out.println("Todos");
            controller.getUsers().forEach(System.out::println);

            System.out.println("Todos Mayores de 40");
            controller.getUsers().stream().filter(user -> user.getAge()>=40).forEach(System.out::println);
            System.out.println();

            System.out.println("Operaciones CRUD");

            System.out.println("Iniciamos un modelo de datos vacío y lo rellenamos nosotros");
            controller.initData();
            controller.addUser(new User(1, "Pepe", "Perez", 24, "Hombre"));
            controller.addUser(new User(2, "Ana", "Lopez", 28, "Mujer"));
            controller.addUser(new User(3, "Son Goku", "Saiyan", 42, "Hombre"));
            controller.printXML();
            controller.writeXMLFile(OUTPUT_XML);

            System.out.println("Añadimos un elemento salario");
            controller.addElement("salary", "1000");
            System.out.println("Eliminamos Gender");
            controller.deleteElement("gender");
            System.out.println("Ponemos en Mayúscula el valor de firstNAme");
            controller.updateElementValue("firstName");
            controller.writeXMLFile(OUTPUT_XML);
            controller.printXML();
            System.out.println();


        } catch (SAXException | ParserConfigurationException | IOException | TransformerException e) {
            System.err.println("ERROR:" + e.getMessage());
        }

    }
}
