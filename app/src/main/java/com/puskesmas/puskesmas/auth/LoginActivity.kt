package com.puskesmas.puskesmas.auth

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.android.material.snackbar.Snackbar
import com.puskesmas.puskesmas.MainActivity
import com.puskesmas.puskesmas.R
import com.puskesmas.puskesmas.admin.HomeAdminActivity
import com.puskesmas.puskesmas.model.LoginResponse
import com.puskesmas.puskesmas.session.SessionManager
import com.puskesmas.puskesmas.werbservice.ApiClient
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(),AnkoLogger {
    var tipelogin: String? = null
    lateinit var progressDialog: ProgressDialog
    lateinit var sessionManager: SessionManager
    var api = ApiClient.instance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        progressDialog = ProgressDialog(this)
        sessionManager = SessionManager(this)
        btnsignin.setOnClickListener {
            login(it)
        }

    }


    fun login(view : View){
        val username = edtusername.text.toString()
        val password = edtpassword.text.toString()

        if (username.isNotEmpty() && password.isNotEmpty()){
            progressDialog.setTitle("Loading...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            api.login(username,password).enqueue(object : Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    try {

                        if (response.isSuccessful){
                            if (response.body()!!.data==1){
                                if (response.body()!!.message =="admin"){
                                    progressDialog.dismiss()
                                    sessionManager.setLoginadmin(true)
                                    startActivity<HomeAdminActivity>()
                                    finish()
                                }else if (response.body()!!.message =="user"){
                                    progressDialog.dismiss()
                                    sessionManager.setLogin(true)
                                    startActivity<MainActivity>()
                                    finish()
                                }
                            }else{
                                progressDialog.dismiss()
                                Snackbar.make(view, "username atau password salah",3000).show()
                            }

                        }else{
                            progressDialog.dismiss()
                            toast("kesalahan aplikasi")
                        }
                    }catch (e :Exception){
                        progressDialog.dismiss()
                        info { "dinda ${e.message}" }
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    progressDialog.dismiss()
                    toast("kesalahan server / jaringan anda")
                    info { "dinda ${t.message}" }


                }

            })
        }else{
            Snackbar.make(view, "Jangan kosongi kolom",3000).show()
        }

    }


}