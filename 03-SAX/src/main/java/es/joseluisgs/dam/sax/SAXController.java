package es.joseluisgs.dam.sax;

import es.joseluisgs.dam.sax.model.User;
import lombok.NonNull;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

// @Getter
public class SAXController {
    private static SAXController controller;
    private final String uri;
    private List<User> userList;

    private SAXController(String uri) {
        this.uri = uri;
    }

    public static SAXController getInstance(@NonNull String uri) {
        if (controller == null)
            controller = new SAXController(uri);
        return controller;
    }

    public void loadData() throws ParserConfigurationException, IOException, SAXException {
        // Creamos el parseador
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        // Indicamos cómo vamos a detectar los eventos
        UserHandler handler = new UserHandler();
        // Parseamos el fichero, con nuestro manejador de eventos
        saxParser.parse(this.uri, handler);
        // Obtenemos la lista de usuarios
        this.userList = handler.getUsers();
    }

    public List<User> getUsers() {
        return this.userList;
    }

}
