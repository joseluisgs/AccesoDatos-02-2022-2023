package es.joseluisgs.dam.sax;

import es.joseluisgs.dam.sax.model.User;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class UserHandler extends DefaultHandler {

    private boolean hasFirstName = false;
    private boolean hasLastName = false;
    private boolean hasAge = false;
    private boolean hasGender = false;

    // Donde vamos a almacenar nuestros objetos
    private List<User> userList = null;
    private User user = null;

    /**
     * Getter de Lista de uusarios
     *
     * @return
     */
    public List<User> getUsers() {
        return userList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // Inicializamos la lista
        if (userList == null)
            userList = new ArrayList<>();
        // Detectamos que llegamos a usuario
        if (qName.equalsIgnoreCase("User")) {
            // Creamos un nuevo usuario
            user = new User();
            String id = attributes.getValue("id");
            user.setId(Integer.parseInt(id));
            // Detectamos otros campos
        } else if (qName.equalsIgnoreCase("firstName")) {
            hasFirstName = true;
        } else if (qName.equalsIgnoreCase("age")) {
            hasAge = true;
        } else if (qName.equalsIgnoreCase("gender")) {
            hasGender = true;
        } else if (qName.equalsIgnoreCase("lastName")) {
            hasLastName = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("User")) {
            // add User object to list
            userList.add(user);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {

        if (hasAge) {
            user.setAge(Integer.parseInt(new String(ch, start, length)));
            hasAge = false;
        } else if (hasFirstName) {
            user.setFirstName(new String(ch, start, length));
            hasFirstName = false;
        } else if (hasLastName) {
            user.setLastName(new String(ch, start, length));
            hasLastName = false;
        } else if (hasGender) {
            user.setGender(new String(ch, start, length));
            hasGender = false;
        }
    }
}