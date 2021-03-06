package com.ankuradlakha.weather.data

/**
 * Network statuses to manage response accordingly
 */
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

/**
 * Wrapper class to manage response results
 * @param status - enum representing response status
 * @param data - generic data requested by API call
 * @param message - string representing error message
 * @param code - int representing API call response code
 */
class Resource<T> constructor(
    val status: Status,
    val data: T?,
    val message: String?,
    val code: Int?
) {

    companion object {
        @JvmStatic
        fun <T> success(data: T?, code: Int = 200): Resource<T> =
            Resource(Status.SUCCESS, data, null, code)

        @JvmStatic
        fun <T> error(message: String?, data: T? = null, code: Int = -1): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message, code = code)

        @JvmStatic
        fun <T> error(code: Int = 400): Resource<T> =
            Resource(Status.ERROR, null, null, code)


        @JvmStatic
        fun <T> error(message: String = "", code: Int): Resource<T> =
            Resource(Status.ERROR, null, null, code)

        fun <T> loading(data: T?): Resource<T> = Resource(Status.LOADING, data, null, -1)

        @JvmStatic
        fun <T> loading(): Resource<T> = loading(null)
    }
}