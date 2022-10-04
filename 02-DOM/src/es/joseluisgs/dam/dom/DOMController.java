package es.joseluisgs.dam.dom;

import es.joseluisgs.dam.dom.model.User;
import lombok.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// @Getter
public class DOMController {
    private final String uri;
    private static DOMController controller;
    private Document data;

    private DOMController(String uri) {
        this.uri = uri;
    }

    /**
     * Obtiene una instancia del controlador
     * @param uri
     * @return
     */
    public static DOMController getInstance(@NonNull String uri) {
        if (controller == null)
            controller = new DOMController(uri);
        return controller;
    }


    /**
     * Si queremos un fichero vacío y crearlo desde cero
     * @throws ParserConfigurationException
     */
    public void initData() throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        dBuilder = dbFactory.newDocumentBuilder();
        this.data = dBuilder.newDocument();
        // Creamos el elemento raíz
        Element rootElement = this.data.createElement("Users");
        // Lo añadimos al ducumento
        this.data.appendChild(rootElement);
    }

    /**
     * Cagamos los datos desde un fichero dada su URI
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public void loadData() throws ParserConfigurationException, IOException, SAXException {
        File xmlFile = new File(this.uri);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        // Cargamos el documento normalizado
        dBuilder = dbFactory.newDocumentBuilder();
        this.data = dBuilder.parse(xmlFile);
        this.data.getDocumentElement().normalize();
    }

    /**
     * Obtenemos el valor existente de un elemento en base a su tag
     *
     * @param tag
     * @param element
     * @return
     */
    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        if(node != null)
            return node.getNodeValue();
        else
            return null;
    }

    /**
     * Obtiene el usuario de un Nodo del DOM
     *
     * @param node
     * @return
     */
    private User getUser(Node node) {
        User user = new User();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            // Leemos la ID que es un atributo
            user.setId(Integer.parseInt(element.getAttribute("id")));
            // Leemos el resto de elementos en base a su tag
            user.setFirstName(getTagValue("firstName", element));
            user.setLastName(getTagValue("lastName", element));
            user.setGender(getTagValue("gender", element));
            user.setAge(Integer.parseInt(getTagValue("age", element)));
        }
        return user;
    }

    /**
     * Obtiene la lista de usuarios del DOM cargado
     * @return
     */
    public List<User> getUsers() {
        // Recorremos y obtenemos todos los ususarios obteniendo todos los nodos user reiniciando la lista
        List<User> userList = new ArrayList<User>();
        NodeList nodeList = this.data.getElementsByTagName("User");
        for (int i = 0; i < nodeList.getLength(); i++) {
            userList.add(getUser(nodeList.item(i)));
        }
        return userList;
    }

    /**
     * Parámetros para preProcesar el XML para imprimir
     * @return
     * @throws TransformerConfigurationException
     */
    private Transformer preProcess() throws TransformerConfigurationException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        return transformer;
    }

    /**
     * Guarda el DOM en un fichero XML indicado por la URI
     * @param uri
     * @throws TransformerException
     */
    public void writeXMLFile(String uri) throws TransformerException {
        Transformer transformer= this.preProcess();
        DOMSource source = new DOMSource(this.data);
        StreamResult file = new StreamResult(new File(uri));
        transformer.transform(source, file);
        System.out.println("Fichero XML generado con éxito");
    }

    /**
     * Imprime el DOM por consola
     * @throws TransformerException
     */
    public void printXML() throws TransformerException {
        Transformer transformer= this.preProcess();
        DOMSource source = new DOMSource(this.data);
        StreamResult console = new StreamResult(System.out);
        transformer.transform(source, console);
    }

    // Operaciones CRUD

    /**
     * Utilidad para crear nodos de tipo texto
     * @param element
     * @param name
     * @param value
     * @return
     */
    private Node createUserElements(Element element, String name, String value) {
        Element node = this.data.createElement(name);
        node.appendChild(this.data.createTextNode(value));
        return node;
    }

    /**
     * Crea un elemento en base a un usuario
     * @return
     */
    private Node createUserElement(User user) {
        // Creamos el nodo user
        Element userElement = this.data.createElement("User");
        // Establecemos los datos
        userElement.setAttribute("id", Integer.toString(user.getId()));
        userElement.appendChild(createUserElements(userElement, "firstName", user.getFirstName()));
        userElement.appendChild(createUserElements(userElement, "lastName", user.getLastName()));
        userElement.appendChild(createUserElements(userElement, "age", Integer.toString(user.getAge())));
        userElement.appendChild(createUserElements(userElement, "gender", user.getGender()));
        return userElement;
    }

    /**
     * Añade un usuario al DOM
     * @param user
     */
    public void addUser(User user) {
        Element rootElement = (Element) this.data.getElementsByTagName("Users").item(0);
        rootElement.appendChild(createUserElement(user));
    }


    /**
     * Añade un nuevo elemento con un determinado tag al usuario
     * @param tag
     * @param value
     */
    public void addElement(String tag, String value) {
        // Obtenemos los usuarios
        NodeList users = this.data.getElementsByTagName("User");
        Element emp = null;

        // Para cada usuario, le vamos a añadir el elemento salario
        for (int i = 0; i < users.getLength(); i++) {
            emp = (Element) users.item(i);
            Element salaryElement = this.data.createElement(tag);
            salaryElement.appendChild(this.data.createTextNode(value));
            emp.appendChild(salaryElement); // Método que agrega un elemento hijo
        }
    }

    /**
     * Elimina un elemento dado un tag
     * @param tag
     */
    public void deleteElement(String tag) {
        NodeList users = this.data.getElementsByTagName("User");
        Element user = null;

        // por cada usuario, eliminamos el elemento de dicha etiqueta
        for (int i = 0; i < users.getLength(); i++) {
            user = (Element) users.item(i);
            Node genderNode = user.getElementsByTagName(tag).item(0);
            user.removeChild(genderNode); // Método que elimina un elemento hijo
        }
    }

    /**
     * Pasa a mayúsculas el texto de un elemento de texto tag
     * @param tag
     */
    public void updateElementValue(String tag) {
        NodeList users = this.data.getElementsByTagName("User");
        Element user = null;
        // loop for each user
        for (int i = 0; i < users.getLength(); i++) {
            user = (Element) users.item(i);
            Node name = user.getElementsByTagName(tag).item(0).getFirstChild();
            name.setNodeValue(name.getNodeValue().toUpperCase()); // Método para cambiar el valor de un nodo
        }
    }


}
