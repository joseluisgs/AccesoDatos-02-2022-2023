package es.joseluisgs.dam;

import es.joseluisgs.dam.rss.Noticia;
import es.joseluisgs.dam.rss.RSSController;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("*** LECTOR DE NOTICIAS RSS ***");
        List<Noticia> noticias = RSSController.getInstance().getNoticias("http://ep00.epimg.net/rss/tags/ultimas_noticias.xml");
        if(noticias.size()>0) {
            System.out.println("Ultimas noticias encontradas: " + noticias.size());
            noticias.forEach(System.out::println);
        }else{
            System.out.println("No se han encontrado noticias");
        }
    }
}
