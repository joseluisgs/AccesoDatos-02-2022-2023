package es.joseluisgs.dam.jaxb.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.UUID;

@XmlRootElement(namespace = "es.joseluisgs.dam.jaxb.model")
public class Bookstore {

    @XmlElementWrapper(name = "bookList")
    @XmlElement(name = "book")
    private List<Book> bookList;
    private String name;
    private String location;


    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public List<Book> getBooksList() {
        return bookList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Bookstore{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}