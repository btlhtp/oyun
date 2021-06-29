package com.example.oyunyapimi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_oyun_ekrani.*
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.floor

class OyunEkraniActivit : AppCompatActivity() {
    //pozisyonlar
    private var anaKarakterX=0.0f
    private var anaKarakterY=0.0f
    private var siyahCisimX=0.0f
    private var siyahCisimY=0.0f
    private var sariDaireX=0.0f
    private var sariDaireY=0.0f
    private var kirmiziUcgenX=0.0f
    private var kirmiziUcgenY=0.0f


    //boyutlar
    private var ekranGenisligi=0
    private var ekranYuksekligi=0
    private var anaKarakterGenisligi=0
    private var anaKarakterYuksekligi=0


    //KONTROLLER
    private  var dokunmaKontrol=false
    private  var baslamaKontrol=false

    private val timer=Timer()

    private var skor=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oyun_ekrani)
        siyahCisim.x=-800.0f
        siyahCisim.y=-800.0f
        sariDaire.x=-800.0f
        sariDaire.y=-800.0f
        kirmiziUcgen.x=-800.0f
        kirmiziUcgen.y=-800.0f

        cl.setOnTouchListener(object:View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                if(baslamaKontrol){

                    if(event?.action==MotionEvent.ACTION_DOWN){
                        Log.e("NotionEvent","ACTİON_DOWN:Ekrana dokundu")
                        dokunmaKontrol=true
                    }
                    if(event?.action==MotionEvent.ACTION_UP){
                        Log.e("NotionEvent","ACTİON_DOWN:Ekrana BIRAKTI")
                        dokunmaKontrol=false
                    }
                }
                else{
                    baslamaKontrol=true
                    textViewOyunBasla.visibility=View.INVISIBLE
                    anaKarakterX=anaKarakter.x
                    anaKarakterY=anaKarakter.y

                    anaKarakterGenisligi=anaKarakter.width
                    anaKarakterYuksekligi=anaKarakter.height

                    ekranGenisligi=cl.width
                    ekranYuksekligi=cl.height

                    timer.schedule(0,20){
                        Handler(Looper.getMainLooper()).post{
                            anaKarakterHareketEttirme()
                            cisimleriHAREKETeTTİRME()
                            carpismaKontrol()

                        }
                    }
                }
                return true
            }
        })
    }
    fun anaKarakterHareketEttirme(){
        val anaKarakterHiz=ekranYuksekligi/60.0f
        if(dokunmaKontrol){
            anaKarakterY-=anaKarakterHiz
        }
        else{
            anaKarakterY+=anaKarakterHiz
        }
        if(anaKarakterY<=0.0f){
            anaKarakterY=0.0f
        }
        if(anaKarakterY>=ekranYuksekligi-anaKarakterYuksekligi){
            anaKarakterY=(ekranYuksekligi-anaKarakterYuksekligi).toFloat()
        }
        anaKarakter.y=anaKarakterY
    }
    fun cisimleriHAREKETeTTİRME(){


        siyahCisimX-=ekranGenisligi/44.0f
        sariDaireX-=ekranGenisligi/54.0f
        kirmiziUcgenX-=ekranGenisligi/36.0f

        if(siyahCisimX<0.0f){
            siyahCisimX=ekranGenisligi+20.0f
            siyahCisimY= floor(Math.random()*ekranYuksekligi).toFloat()
        }
        siyahCisim.x=siyahCisimX
        siyahCisim.y=siyahCisimY

        if(sariDaireX<0.0f){
            sariDaireX=ekranGenisligi+20.0f
            sariDaireY= floor(Math.random()*ekranYuksekligi).toFloat()
        }
        sariDaire.x=sariDaireX
        sariDaire.y=sariDaireY

        if(kirmiziUcgenX<0.0f){
            kirmiziUcgenX=ekranGenisligi+20.0f
            kirmiziUcgenY= floor(Math.random()*ekranYuksekligi).toFloat()
        }
        kirmiziUcgen.x=kirmiziUcgenX
        kirmiziUcgen.y=kirmiziUcgenY
    }

    fun carpismaKontrol(){
        val saridaireMerkezX=sariDaireX+sariDaire.width/2.0f
        val saridaireMerkezY=sariDaireY+sariDaire.height/2.0f

        if(0.0f <=saridaireMerkezX && saridaireMerkezX <=anaKarakterGenisligi && anaKarakterY<=saridaireMerkezY && saridaireMerkezY<=anaKarakterY+anaKarakterYuksekligi){
            skor+=20
            sariDaireX=-10.0f
        }

        val kirmiziucgenMerkezX=kirmiziUcgenX+kirmiziUcgen.width/2.0f
        val kirmiziucgenMerkezY=kirmiziUcgenY+kirmiziUcgen.height/2.0f

        if(0.0f <=kirmiziucgenMerkezX && kirmiziucgenMerkezX <=anaKarakterGenisligi && anaKarakterY<=kirmiziucgenMerkezY && kirmiziucgenMerkezY<=anaKarakterY+anaKarakterYuksekligi){
            skor+=20
            kirmiziUcgenX=-10.0f
        }
        val siyahcisimMerkezX=siyahCisimX+siyahCisim.width/2.0f
        val siyahcisimMerkezY=siyahCisimY+siyahCisim.height/2.0f

        if(0.0f <=siyahcisimMerkezX && siyahcisimMerkezX <=anaKarakterGenisligi && anaKarakterY<=siyahcisimMerkezY && siyahcisimMerkezY<=anaKarakterY+anaKarakterYuksekligi){

            kirmiziUcgenX=-10.0f
            timer.cancel()

            var intent=Intent(this@OyunEkraniActivit,SonucActivity::class.java)
            intent.putExtra("skor",skor)
            startActivity(intent)
            finish()
        }
       textViewSkor.text=skor.toString()
    }
}
