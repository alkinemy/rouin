package al.rouin.external

import retrofit2.Call


fun <T> Call<T>.executeBody(): T {
    val response = this.execute()
    return response.body()!!
}