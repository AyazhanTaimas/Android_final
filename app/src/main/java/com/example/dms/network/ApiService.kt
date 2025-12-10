import retrofit2.Call

interface ApiService {
    @POST("api/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}
