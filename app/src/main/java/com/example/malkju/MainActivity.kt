package com.example.weatherapp.ui

import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.weatherapp.R
import com.example.weatherapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var weatherInfo: TextView
    private lateinit var weatherIcon: ImageView

    private val regions = mapOf(
        "Seoul" to Pair(60, 127),
        "Busan" to Pair(98, 76),
        "Daegu" to Pair(89, 90),
        "Incheon" to Pair(55, 124),
        "Gwangju" to Pair(58, 74),
        "Daejeon" to Pair(67, 100),
        "Ulsan" to Pair(102, 84),
        "Sejong" to Pair(66, 103)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherInfo = findViewById(R.id.weatherInfo)
        weatherIcon = findViewById(R.id.weatherIcon)

        // 애니메이션 로드
        val clickAnimation = AnimationUtils.loadAnimation(this, R.anim.button_click_effect)

        // 각 버튼 클릭 시 애니메이션 적용 및 날씨 정보 가져오기
        findViewById<Button>(R.id.buttonSeoul).apply {
            setOnClickListener {
                it.startAnimation(clickAnimation)
                fetchWeather("Seoul")
            }
        }

        findViewById<Button>(R.id.buttonBusan).apply {
            setOnClickListener {
                it.startAnimation(clickAnimation)
                fetchWeather("Busan")
            }
        }

        findViewById<Button>(R.id.buttonDaegu).apply {
            setOnClickListener {
                it.startAnimation(clickAnimation)
                fetchWeather("Daegu")
            }
        }

        findViewById<Button>(R.id.buttonIncheon).apply {
            setOnClickListener {
                it.startAnimation(clickAnimation)
                fetchWeather("Incheon")
            }
        }

        findViewById<Button>(R.id.buttonGwangju).apply {
            setOnClickListener {
                it.startAnimation(clickAnimation)
                fetchWeather("Gwangju")
            }
        }

        findViewById<Button>(R.id.buttonDaejeon).apply {
            setOnClickListener {
                it.startAnimation(clickAnimation)
                fetchWeather("Daejeon")
            }
        }

        findViewById<Button>(R.id.buttonUlsan).apply {
            setOnClickListener {
                it.startAnimation(clickAnimation)
                fetchWeather("Ulsan")
            }
        }

        findViewById<Button>(R.id.buttonSejong).apply {
            setOnClickListener {
                it.startAnimation(clickAnimation)
                fetchWeather("Sejong")
            }
        }

        // 노르웨이 기상청 데이터 관찰
        viewModel.norwayWeather.observe(this, Observer { weather ->
            weather?.let {
                val temperature = it.timeseries[0].data.instant.details.air_temperature
                weatherInfo.text = "Norway Temp: $temperature°C"
                updateWeatherIcon(temperature, "clear", weatherIcon)
            }
        })

        // 한국 기상청 데이터 관찰
        viewModel.koreaWeather.observe(this, Observer { weather ->
            weather?.let {
                val temperatureItem =
                    it.response.body.items.item.firstOrNull { item -> item.category == "T1H" }
                temperatureItem?.let { tempItem ->
                    val temperature = tempItem.fcstValue.toDoubleOrNull()
                    weatherInfo.text = "Korea Temp: $temperature°C"
                    updateWeatherIcon(temperature ?: 0.0, "clear", weatherIcon)
                }
            }
        })
    }

    // 지역에 맞는 날씨 정보를 가져오는 함수
    private fun fetchWeather(region: String) {
        val (nx, ny) = regions[region] ?: return
        viewModel.fetchKoreaWeather("gWFrJu_TDKFhaybvwwyfQ", "20241002", "0600", nx, ny)
        // 노르웨이 기상청 API 호출 (Oslo 좌표 예시)
        viewModel.fetchNorwayWeather(59.91, 10.75)
    }

    // 날씨 아이콘 업데이트 함수
    private fun updateWeatherIcon(temperature: Double, condition: String, imageView: ImageView) {
        when {
            condition.contains("thunderstorm", ignoreCase = true) -> {
                imageView.setImageResource(R.drawable.thunderstoms)
            }
            condition.contains("cloud", ignoreCase = true) -> {
                imageView.setImageResource(R.drawable.cloud)
            }
            temperature >= 25 -> {
                imageView.setImageResource(R.drawable.sunny)
            }
            temperature in 0.0..24.9 -> {
                imageView.setImageResource(R.drawable.cloud)
            }
            else -> {
                imageView.setImageResource(R.drawable.snow)
            }
        }
    }
}
