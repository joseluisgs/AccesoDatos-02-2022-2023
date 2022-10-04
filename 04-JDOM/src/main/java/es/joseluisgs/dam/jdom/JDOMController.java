package es.joseluisgs.dam.jdom;

import es.joseluisgs.dam.jdom.model.User;
import lombok.Getter;
import lombok.NonNull;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;

@Getter
public class JDOMController {
    private static JDOMController controller;
    private final String uri;
    private Document data;

    private JDOMController(String uri) {
        this.uri = uri;
    }

    /**
     * Obtiene una instancia del controlador
     *
     * @param uri
     * @return
     */
    public static JDOMController getInstance(@NonNull String uri) {
        if (controller == null)
            controller = new JDOMController(uri);
        return controller;
    }

    /**
     * Añade un elemento de User
     *
     * @param user
     * @return
     */
    private Element createUserElement(User user) {
        Element userElement = new Element("User");
        userElement.setAttribute(new Attribute("id", Integer.toString(user.getId())));
        userElement.addContent(new Element("firstName").setText(user.getFirstName()));
        userElement.addContent(new Element("lastName").setText(user.getLastName()));
        userElement.addContent(new Element("age").setText(Integer.toString(user.getAge())));
        userElement.addContent(new Element("gender").setText(user.getGender()));
        return userElement;
    }

    /**
     * Carga los datos desde un fichero dada su URI
     *
     * @throws IOException
     * @throws JDOMException
     */
    public void loadData() throws IOException, JDOMException {
        // JDOM Document trabaja con DOM, SAX y STAX Parser Builder classes
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(this.uri);
        this.data = (Document) builder.build(xmlFile);
    }

    public void initData() {
        // Documento vacío
        this.data = new Document();
        // Nodo raíz
        this.data.setRootElement(new Element("Users"));
    }

    private XMLOutputter preProcess() {
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        return xmlOutput;
    }

    public void writeXMLFile(String uri) throws IOException {
        XMLOutputter xmlOutput = this.preProcess();
        xmlOutput.output(this.data, new FileWriter(uri));
        System.out.println("Fichero XML generado con éxito");
    }

    public void printXML() throws IOException {
        XMLOutputter xmlOutput = this.preProcess();
        xmlOutput.output(this.data, System.out);
    }

    /**
     * Obtiene la lista de usuarios del DOM cargado
     *
     * @return
     */
    public List<User> getUsers() {
        // Tomamos el elemento raiz y obtenemos sus hijos
        Element root = (Element) this.data.getRootElement();
        List<Element> listOfUsers = root.getChildren("User");
        return listOfUsers.stream().map(this::getUserFromElement)
                .collect(Collectors.toList());
    }

    private User getUserFromElement(Element userElement) {
        User user = new User();
        user.setId(Integer.parseInt(userElement.getAttributeValue("id")));
        user.setAge(Integer.parseInt(userElement.getChildText("age")));
        user.setFirstName(userElement.getChildText("firstName"));
        user.setLastName(userElement.getChildText("lastName"));
        user.setGender(userElement.getChildText("gender"));
        return user;
    }

    /**
     * Añade un usuario al DOM
     *
     * @param user
     */
    public void addUser(User user) {
        Element root = (Element) this.data.getRootElement();
        root.addContent(createUserElement(user));
    }

    /**
     * Añade un nuevo elemento con un determinado tag al usuario
     *
     * @param tag
     * @param value
     */
    public void addElement(String tag, String value) {
        Element rootElement = this.data.getRootElement();
        List<Element> listUserElement = rootElement.getChildren("User");
        listUserElement.forEach(userElement -> {
            userElement.addContent(new Element(tag).setText(value));
        });
    }

    /**
     * Pasa a mayúsculas el texto de un elemento de texto tag
     *
     * @param tag
     */
    public void updateElementValue(String tag) {
        Element rootElement = this.data.getRootElement();
        List<Element> listUserElement = rootElement.getChildren("User");
        listUserElement.forEach(userElement -> {
            String name = userElement.getChildText("firstName");
            if (name != null)
                userElement.getChild("firstName").setText(name.toUpperCase());

        });
    }

    /**
     * Elimina un elemento dado un tag
     *
     * @param tag
     */
    public void deleteElement(String tag) {
        Element rootElement = this.data.getRootElement();
        List<Element> listUserElement = rootElement.getChildren("User");
        listUserElement.forEach(userElement -> {
            userElement.removeChild(tag);
        });
    }

    /**
     * Actualiza el Id según el género
     */
    public void updateID() {
        Element rootElement = this.data.getRootElement();
        List<Element> listUserElement = rootElement.getChildren("User");
        listUserElement.forEach(userElement -> {
            String gender = userElement.getChildText("gender");
            if (gender != null && gender.equalsIgnoreCase("female")) {
                String id = userElement.getAttributeValue("id");
                userElement.getAttribute("id").setValue(id + "F");

            } else {
                String id = userElement.getAttributeValue("id");
                userElement.getAttribute("id").setValue(id + "M");
            }
        });
    }

    // Consultas con XPATH

    /**
     * Obtener todos los elementos nombre
     * @return
     */
    public List<String> getAllNames() {
        XPathFactory xpath = XPathFactory.instance();
        XPathExpression<Element> expr = xpath.compile("//User/firstName", Filters.element());
        List<Element> users = expr.evaluate(this.data);
        return users.parallelStream()
                .map(user -> user.getValue().trim())
                .sorted(String::compareTo)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los atributos ID
     * @return
     */
    public List<String> getAllIds() {
        XPathFactory xpath = XPathFactory.instance();
        XPathExpression<Attribute> expr = xpath.compile("//User/@id", Filters.attribute());
        List<Attribute> users = expr.evaluate(this.data);
        return users.parallelStream()
                .map(user -> user.getValue().trim())
                .sorted(String::compareTo)
                // .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    /**
     * Obtiene el nombre del elemento con indice indicado
     * @param index
     * @return
     */
    public String getFirstName(int index) {
        XPathFactory xpath = XPathFactory.instance();
        XPathExpression<Element> expr = xpath.compile("//User["+index+"]/firstName", Filters.element());
        Element name = expr.evaluateFirst(this.data);
        return name.getValue();
    }

    /**
     * Obtiene el nombre dado el ID
     * @param id
     * @return
     */
    public String getFirstNameById(String id) {
        XPathFactory xpath = XPathFactory.instance();
        XPathExpression<Element> expr = xpath.compile("//User[@id='"+id+"']/firstName", Filters.element());
        Element name = expr.evaluateFirst(this.data);
        return name.getValue();
    }

}
