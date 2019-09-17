package com.example.pomodoro

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity() : AppCompatActivity() {

    var ciclosCount = 0
    var sessionCount : Int = 0
    val timer25 : Long = 110000
    val timer5 : Long = 5000
    var numSession = 4
    var longPause : Boolean = false
    var statusConcent : Boolean = true
    var resumeTime : Long = 0
    var isPause : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionCount = 0


    }

    fun iniciar(view: View){

            // Concentration's cycle

            if (ciclosCount < numSession) {
                if (statusConcent) {
                    statusConcent = false
                    if (start_button.text.equals("Iniciar")) {

                        countdown(view, timer25).start()

                    } else if(start_button.text.equals("Parar")) {
                        isPause = true
                    }else {
                        countdown(view, resumeTime).start()
                        isPause = false

                    }
                    // coffe time
                } else {
                    statusConcent = true

                    if (start_button.text.equals("Iniciar")) {
                        countdown(view, timer5).start()
                    }else if(start_button.text.equals("Parar")) {
                        isPause = true
                    } else {
                        countdown(view, resumeTime).start()
                        isPause = false
                    }

                }
            } else {
                ++sessionCount
                if (start_button.text.equals("Iniciar")) {
                    longPause = true
                    label.text = "Pausa longa"
                    sessions.text = sessionCount.toString()
                    countdown(view, timer25).start()
                } else if(start_button.text.equals("Parar")) {
                    isPause = true
                }else {
                    countdown(view, resumeTime).start()
                    isPause = false

                }
            }

    }



    fun countdown(view: View, timer : Long) : CountDownTimer{

       return object : CountDownTimer (timer, 1000){
            override fun onFinish() {
                time.text = "0"

                if(statusConcent) {

                    time.text = (timer25 / 1000).toString()
                    if(!longPause) {
                        label.text = "Hora da concentração"
                        Toast.makeText(applicationContext, "Hora de se concentrar!", Toast.LENGTH_SHORT).show()
                        ciclosCount++
                    }else{
                        time.text = (timer25 / 1000).toString()
                        Toast.makeText(applicationContext, "Nova sessão!", Toast.LENGTH_SHORT).show()
                        longPause= false
                    }


                } else {
                    Toast.makeText(applicationContext, "Pequena pausa!", Toast.LENGTH_SHORT).show()
                    time.text = (timer5 / 1000).toString()
                    label.text = "Pausa pro café/chá"
                }
                start_button.text = "Iniciar"

            }

            override fun onTick(millisUntilFinished: Long) {
                start_button.text = "Parar"
                resumeTime = millisUntilFinished
                time.text = (millisUntilFinished / 1000).toString()
                if (isPause){
                    cancel()
                    start_button.text = "Continuar"
                    time.text = (resumeTime/1000).toString()
                }

            }

        }

    }
}
