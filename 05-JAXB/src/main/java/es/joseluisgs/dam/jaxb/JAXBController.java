package es.joseluisgs.dam.jaxb;

import es.joseluisgs.dam.jaxb.model.Bookstore;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class JAXBController {
    private static JAXBController instance;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    private Bookstore bookstore;

    private JAXBController() {
    }

    /**
     * Devuelve la instancia del controlador
     *
     * @return
     */
    public static JAXBController getInstance() {
        if (instance == null) {
            instance = new JAXBController();
        }
        return instance;
    }

    /**
     * Convierte una lista de objetos en XML
     *
     * @param bookstore
     * @throws JAXBException
     */
    private void convertObjectToXML(Bookstore bookstore) throws JAXBException {
        this.bookstore = bookstore;
        // Creamos el contexto
        JAXBContext context = JAXBContext.newInstance(Bookstore.class);
        // Marshall --> Object to XML
        this.marshaller = context.createMarshaller();
        this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

    }

    /**
     * Carla los datos de Objetos y los trasforma en XML
     * @param bookstore
     * @throws JAXBException
     */
    public void setBookstore(Bookstore bookstore) throws JAXBException {
        convertObjectToXML(bookstore);
    }

    /**
     * Escribe un fichero XML con el contenido de los datos
     *
     * @param uri
     * @throws JAXBException
     */
    public void writeXMLFile(String uri) throws JAXBException {
        this.marshaller.marshal(bookstore, new File(uri));
        System.out.println("Fichero XML generado con éxito");
    }

    /**
     * Muestra por pantalla el contenido de nuestros datos
     *
     * @throws JAXBException
     */
    public void printXML() throws JAXBException {
        this.marshaller.marshal(bookstore, System.out);
    }

    private Bookstore convertXMLToObject(String uri) throws JAXBException {
        // Creamos el contexto
        JAXBContext context = JAXBContext.newInstance(Bookstore.class);
        this.unmarshaller = context.createUnmarshaller();
        // Unmarshall --> XML toObject
        this.bookstore = (Bookstore) this.unmarshaller.unmarshal(new File(uri));
        return this.bookstore;
    }

    /**
     * Carlos los datos de un XML y los transforma en Objetos
     * @param uri
     * @return
     * @throws JAXBException
     */
    public Bookstore getBookstore(String uri) throws JAXBException {
        return convertXMLToObject(uri);
    }
}
