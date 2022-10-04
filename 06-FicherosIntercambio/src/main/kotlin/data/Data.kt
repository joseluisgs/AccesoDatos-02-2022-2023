package data


import models.Tenista
import java.time.LocalDate


fun getTenistasInit() = listOf(
    Tenista(
        nombre = "Rafael Nadal",
        ranking = 2,
        fechaNacimiento = LocalDate.parse("1985-06-04"),
        añoProfesional = 2005,
        altura = 185,
        peso = 81.0,
        ganancias = 15000300.0,
        manoDominante = Tenista.ManoDominante.IZQUIERDA,
        tipoReves = Tenista.TipoReves.DOS_MANOS,
        puntos = 6789,
        pais = "España",
    ),
    Tenista(
        nombre = "Roger Federer",
        ranking = 3,
        fechaNacimiento = LocalDate.parse("1981-01-01"),
        añoProfesional = 2000,
        altura = 188,
        peso = 83.0,
        ganancias = 20040608.0,
        manoDominante = Tenista.ManoDominante.DERECHA,
        tipoReves = Tenista.TipoReves.UNA_MANO,
        puntos = 3789,
        pais = "Suiza",
    ),
    Tenista(
        nombre = "Novak Djokovic",
        ranking = 4,
        fechaNacimiento = LocalDate.parse("1986-05-05"),
        añoProfesional = 2004,
        altura = 189,
        peso = 81.6,
        ganancias = 10020054.0,
        manoDominante = Tenista.ManoDominante.DERECHA,
        tipoReves = Tenista.TipoReves.DOS_MANOS,
        puntos = 1970,
        pais = "Serbia",
    ),
    Tenista(
        nombre = "Dominic Thiem",
        ranking = 5,
        fechaNacimiento = LocalDate.parse("1985-06-04"),
        añoProfesional = 2015,
        altura = 188,
        peso = 82.5,
        ganancias = 10304.25,
        manoDominante = Tenista.ManoDominante.DERECHA,
        tipoReves = Tenista.TipoReves.UNA_MANO,
        puntos = 1234,
        pais = "Austria",
    ),
    Tenista(
        nombre = "Carlos Alcaraz",
        ranking = 1,
        fechaNacimiento = LocalDate.parse("2003-05-05"),
        añoProfesional = 2019,
        altura = 185,
        peso = 80.75,
        ganancias = 5005555.5,
        manoDominante = Tenista.ManoDominante.DERECHA,
        tipoReves = Tenista.TipoReves.DOS_MANOS,
        puntos = 6880,
        pais = "España",
    ),
)
