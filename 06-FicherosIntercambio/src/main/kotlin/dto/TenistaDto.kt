package dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TenistaDto(
    @SerialName("uuid")
    val uuid: String,
    var nombre: String,
    var ranking: Int,
    var fechaNacimiento: String,
    var añoProfesional: Int,
    var altura: Int,
    var peso: Double,
    var ganancias: Double,
    var manoDominante: String,
    var tipoReves: String,
    var puntos: Int,
    var pais: String,
    //var raqueta: Raqueta? = null, // No tiene por que tener raqueta
)

