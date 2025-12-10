import com.example.dms.models.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @POST("api/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("api/me")
    suspend fun getProfile(): UserProfile

    @POST("api/logout")
    fun logout(): Call<Void>

    // --- Новости ---
    @GET("api/news")
    fun getAllNews(): Call<List<News>>

    @GET("api/news/{id}")
    fun getNewsById(@Path("id") id: Int): Call<News>

    @POST("api/news")
    fun createNews(@Body news: NewsRequest): Call<Void>

    @PUT("api/news/{id}")
    fun updateNews(@Path("id") id: Int, @Body news: NewsRequest): Call<Void>

    @DELETE("api/news/{id}")
    fun deleteNews(@Path("id") id: Int): Call<Void>
}
