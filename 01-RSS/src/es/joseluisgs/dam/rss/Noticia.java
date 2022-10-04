package es.joseluisgs.dam.rss;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Voy a usar a partir de ahora Lombok mucho
 * https://projectlombok.org/features/all
 * https://plugins.jetbrains.com/plugin/6317-lombok
 * @Data --- Para Hacer PJOS
 * All together now: A shortcut for @ToString, @EqualsAndHashCode, @Getter on all fields, and @Setter on all non-final fields,
 * and @RequiredArgsConstructor!
 */
@Data
public class Noticia implements Serializable {
    private String titulo;
    private String link;
    private String descripcion;
    private String contenido;
    private String fecha;
    private String imagen;

    public String toString() {
        return "* Noticia: " + "\n"+
                "\t--> Titular: " + this.titulo + "\n" +
                "\t--> Descripción: " + this.descripcion + "\n" +
                "\t--> Contenido: " + this.contenido + "\n" +
                "\t--> Fecha: " + this.fecha + "\n" +
                "\t--> Enlace: " + this.link + "\n" +
                "\t--> Imagen: " + this.imagen + "\n";
    }
}
