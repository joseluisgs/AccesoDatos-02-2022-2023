import data.getTenistasInit
import intercambio.Intercambio
import mappers.toDto
import mappers.toTenista

fun main() {
    println("Ficheros de Intercambios de Datos")

    var tenistas = getTenistasInit()
    tenistas.forEach(::println)

    var tenistasDto = tenistas.map { it.toDto() }

    tenistasDto.forEach(::println)

    // Intercambio.writeCsv(tenistasDto)
    Intercambio.writeCsv(tenistasDto)

    tenistasDto = Intercambio.readCsv()
    tenistasDto.forEach(::println)

    tenistas = tenistasDto.map { it.toTenista() }
    tenistas.forEach(::println)

    Intercambio.writeJson(tenistasDto)
    tenistasDto = Intercambio.readJson()
    tenistasDto.forEach(::println)

    Intercambio.writeXML(tenistasDto)
    tenistasDto = Intercambio.readXML()
    tenistasDto.forEach(::println)

}