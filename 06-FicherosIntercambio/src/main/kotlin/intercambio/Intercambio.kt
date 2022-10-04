package intercambio

import dto.TenistaDto
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import nl.adaptivity.xmlutil.serialization.XML
import java.io.File

private val logger = KotlinLogging.logger {}

object Intercambio {
    val dir = System.getProperty("user.dir") + File.separator + "data"

    fun writeCsv(tenistas: List<TenistaDto>) {
        logger.debug { "Escribiendo fichero CSV" }
        val file = File(dir + File.separator + "tenistas.csv")
        // Escribimos la cabecera seprarada por;
        file.writeText("uuid;nombre;ranking;fechaNacimiento;añoProfesional;altura;peso;ganancias;manoDominante;tipoReves;puntos;pais")
        tenistas.forEach {
            // Escribimos cada tenista en una linea separada por;
            file.appendText("\n${it.uuid};${it.nombre};${it.ranking};${it.fechaNacimiento};${it.añoProfesional};${it.altura};${it.peso};${it.ganancias};${it.manoDominante};${it.tipoReves};${it.puntos};${it.pais}")
        }
    }

    fun readCsv(): List<TenistaDto> {
        logger.debug { "Leyendo fichero CSV" }
        val file = File(dir + File.separator + "tenistas.csv")
        if (file.exists()) {
            // Lemos todas las lineas
            return file.readLines().drop(1)
                .map { it.split(";") }
                .map { field ->
                    TenistaDto(
                        uuid = field[0],
                        nombre = field[1],
                        ranking = field[2].toInt(),
                        fechaNacimiento = field[3],
                        añoProfesional = field[4].toInt(),
                        altura = field[5].toInt(),
                        peso = field[6].toDouble(),
                        ganancias = field[7].toDouble(),
                        manoDominante = field[8],
                        tipoReves = field[9],
                        puntos = field[10].toInt(),
                        pais = field[11]
                    )
                }
        } else {
            throw IllegalArgumentException("El fichero ${file.absolutePath} no existe")
        }
    }

    fun writeJson(tenistasDto: List<TenistaDto>) {
        logger.debug { "Escribiendo fichero JSON" }
        val file = File(dir + File.separator + "tenistas.json")
        val json = Json { prettyPrint = true }
        file.writeText(json.encodeToString(tenistasDto))
    }

    fun readJson(): List<TenistaDto> {
        logger.debug { "Leyendo fichero JSON" }
        val file = File(dir + File.separator + "tenistas.json")
        if (file.exists()) {
            return Json.decodeFromString<List<TenistaDto>>(file.readText())
        } else {
            throw IllegalArgumentException("El fichero ${file.absolutePath} no existe")
        }
    }

    fun writeXML(tenistasDto: List<TenistaDto>) {
        logger.debug { "Escribiendo fichero XML" }
        val file = File(dir + File.separator + "tenistas.xml")
        val xml = XML { indent = 4 }
        file.writeText(xml.encodeToString(tenistasDto))
    }

    fun readXML(): List<TenistaDto> {
        logger.debug { "Leyendo fichero XML" }
        val file = File(dir + File.separator + "tenistas.xml")
        if (file.exists()) {
            return XML.decodeFromString<List<TenistaDto>>(file.readText())
        } else {
            throw IllegalArgumentException("El fichero ${file.absolutePath} no existe")
        }
    }
}