package al.rouin.plaid

import retrofit2.Call


fun <T> Call<T>.executeBody(): T {
    val response = this.execute()
    return response.body()!!
}