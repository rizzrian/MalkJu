package com.example.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.api.KmaWeatherResponse
import com.example.weatherapp.api.NorwayWeatherResponse
import com.example.weatherapp.api.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log

class WeatherViewModel : ViewModel() {

    private val _norwayWeather = MutableLiveData<NorwayWeatherResponse>()
    val norwayWeather: LiveData<NorwayWeatherResponse> = _norwayWeather

    private val _koreaWeather = MutableLiveData<KmaWeatherResponse>()
    val koreaWeather: LiveData<KmaWeatherResponse> = _koreaWeather

    // 노르웨이 기상청 데이터 가져오기
    fun fetchNorwayWeather(lat: Double, lon: Double) {
        val service = WeatherService.api // Retrofit 인스턴스에서 바로 API 객체 사용
        service.getNorwayWeather(lat, lon).enqueue(object : Callback<NorwayWeatherResponse> {
            override fun onResponse(call: Call<NorwayWeatherResponse>, response: Response<NorwayWeatherResponse>) {
                if (response.isSuccessful) {
                    _norwayWeather.value = response.body() // 성공 시 LiveData 업데이트
                } else {
                    Log.e("WeatherViewModel", "Norway weather API error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<NorwayWeatherResponse>, t: Throwable) {
                Log.e("WeatherViewModel", "Norway weather API failure", t)
            }
        })
    }

    // 한국 기상청 데이터 가져오기
    fun fetchKoreaWeather(apiKey: String, baseDate: String, baseTime: String, nx: Int, ny: Int) {
        val service = WeatherService.api // Retrofit 인스턴스에서 바로 API 객체 사용
        service.getKoreaWeather(apiKey, baseDate, baseTime, nx, ny)
            .enqueue(object : Callback<KmaWeatherResponse> {
                override fun onResponse(call: Call<KmaWeatherResponse>, response: Response<KmaWeatherResponse>) {
                    if (response.isSuccessful) {
                        _koreaWeather.value = response.body() // 성공 시 LiveData 업데이트
                    } else {
                        Log.e("WeatherViewModel", "KMA weather API error: ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<KmaWeatherResponse>, t: Throwable) {
                    Log.e("WeatherViewModel", "KMA weather API failure", t)
                }
            })
    }
}
