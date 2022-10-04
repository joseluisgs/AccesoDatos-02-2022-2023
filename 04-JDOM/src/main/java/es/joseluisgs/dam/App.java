package es.joseluisgs.dam;


import es.joseluisgs.dam.jdom.JDOMController;
import es.joseluisgs.dam.jdom.model.User;
import org.jdom2.JDOMException;

import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        String INPUT_XML = System.getProperty("user.dir") + File.separator + "data" + File.separator + "users.xml";
        String OUTPUT_XML = System.getProperty("user.dir") + File.separator + "data" + File.separator + "users_updated.xml";

        System.out.println(INPUT_XML);

        try {
            System.out.println("Cargamos nuestros Datos usando JDOM desde fichero");
            JDOMController controller = JDOMController.getInstance(INPUT_XML);
            controller.loadData();
            System.out.println("Exportamos los datos a un XML");
            controller.printXML();
            controller.writeXMLFile(OUTPUT_XML);
            System.out.println();

            System.out.println("*** JDOM XML *** ");
            System.out.println("Listado");
            System.out.println("Todos");
            controller.getUsers().forEach(System.out::println);

            System.out.println("Todos Mayores de 40");
            controller.getUsers().stream().filter(user -> user.getAge() >= 40).forEach(System.out::println);
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
            System.out.println("AActualizamos el ID");
            controller.updateID();
            System.out.println("Eliminamos Gender");
            controller.deleteElement("gender");
            System.out.println("Ponemos en Mayúscula el valor de firstNAme");
            controller.updateElementValue("firstName");
            controller.writeXMLFile(OUTPUT_XML);
            controller.printXML();
            System.out.println();

            System.out.println("Operaciones XPATH");
            System.out.println("Obtenemos todos los nombres");
            controller.getAllNames().forEach(System.out::println);
            System.out.println("Obtenemos todos los Ids");
            controller.getAllIds().forEach(System.out::println);
            System.out.println("Obtenemos el nombre del 2º elemento");
            System.out.println(controller.getFirstName(2));
            System.out.println("Obtenemos el nombre del elemento con id 3M");
            System.out.println(controller.getFirstNameById("3M"));

        } catch (IOException | JDOMException e) {
            System.err.println("ERROR:" + e.getMessage());
        }

    }
}
