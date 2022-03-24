package com.puskesmas.puskesmas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.puskesmas.puskesmas.admin.HomeAdminActivity
import com.puskesmas.puskesmas.auth.LoginActivity
import com.puskesmas.puskesmas.session.SessionManager
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask

class SplashScreenActivity : AppCompatActivity() {
    lateinit var handler: Handler
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        sessionManager = SessionManager(this)
        if (sessionManager.getLogin()==true){
            handler = Handler()
            handler.postDelayed({
                startActivity(intentFor<MainActivity>().clearTask().newTask())
                finish()
            }, 3000)
        }else if (sessionManager.getLoginadmin()==true){
            handler = Handler()
            handler.postDelayed({
                startActivity(intentFor<HomeAdminActivity>().clearTask().newTask())
                finish()
            }, 3000)
        }else{
            handler = Handler()
            handler.postDelayed({
                startActivity(intentFor<LoginActivity>().clearTask().newTask())
                finish()
            }, 3000)
        }


    }
}