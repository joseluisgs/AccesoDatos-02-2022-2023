package mappers

import dto.TenistaDto
import models.Tenista
import java.time.LocalDate
import java.util.*

fun TenistaDto.toTenista(): Tenista {
    return Tenista(
        uuid = UUID.fromString(this.uuid),
        nombre = this.nombre,
        ranking = this.ranking,
        fechaNacimiento = LocalDate.parse(this.fechaNacimiento),
        añoProfesional = this.añoProfesional,
        altura = this.altura,
        peso = this.peso,
        ganancias = this.ganancias,
        manoDominante = Tenista.ManoDominante.from(this.manoDominante),
        tipoReves = Tenista.TipoReves.from(this.tipoReves),
        puntos = this.puntos,
        pais = this.pais,
    )
}

fun Tenista.toDto(): TenistaDto {
    return TenistaDto(
        uuid = this.uuid.toString(),
        nombre = this.nombre,
        ranking = this.ranking,
        fechaNacimiento = this.fechaNacimiento.toString(),
        añoProfesional = this.añoProfesional,
        altura = this.altura,
        peso = this.peso,
        ganancias = this.ganancias,
        manoDominante = this.manoDominante.mano,
        tipoReves = this.tipoReves.tipo,
        puntos = this.puntos,
        pais = this.pais,
    )
}