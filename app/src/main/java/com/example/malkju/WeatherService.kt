import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// API 통신 인터페이스 정의
interface WeatherApi {
    @GET("weather")
    suspend fun getWeatherData(
        @Query("q") city: String, // @Quality가 아니라 @Query로 수정
        @Query("appid") apiKey: String
    ): WeatherResponse
}

// 데이터 클래스 (JSON 응답 파싱)
data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>
)

data class Main(val temp: Double, val humidity: Int)
data class Weather(val description: String, val icon: String)

// Retrofit 서비스 생성
object WeatherService {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/" // 베이스 URL

    // Retrofit 인스턴스 생성
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // WeatherApi 생성
    val api: WeatherApi = retrofit.create(WeatherApi::class.java)
}

// 날씨 데이터를 호출하는 함수
suspend fun fetchWeather(city: String, apiKey: String): WeatherResponse? {
    return try {
        WeatherService.api.getWeatherData(city, apiKey)
    } catch (e: Exception) {
        e.printStackTrace() // 오류 로그 출력
        null // 오류 발생 시 null 반환
    }
}
