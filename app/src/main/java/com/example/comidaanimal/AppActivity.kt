package com.example.comidaanimal

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Range
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider

class AppActivity : AppCompatActivity() {

    lateinit var cvDog: CardView
    lateinit var cvCat: CardView
    lateinit var tvWeight: TextView
    lateinit var rsWeight: RangeSlider
    lateinit var tvAge: TextView
    lateinit var fabDecrementAge: FloatingActionButton
    lateinit var fabAddAge: FloatingActionButton
    lateinit var tvResult: TextView

    var animalSelected: String? = null
    var weightSelected: Int? = null
    var ageSelected: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        setContentView(R.layout.activity_app)
       //BUENA PRACTICA: initComponents() y initListeners(), donde inicializar los gets y los eventlistener
        initComponents()
        initListeners()
    }

    private fun initComponents(){
        cvDog = findViewById(R.id.cvDog)
        cvCat = findViewById(R.id.cvCat)
        tvWeight = findViewById(R.id.tvWeight)
        rsWeight = findViewById(R.id.rsWeight)
        fabAddAge = findViewById(R.id.fabAddAge)
        fabDecrementAge = findViewById(R.id.fabDecrementAge)
        tvAge = findViewById(R.id.tvAge)
        tvResult = findViewById(R.id.tvResult)
    }
    private fun initListeners() {
        cvDog.setOnClickListener {
            animalSelected="dog"
            //Seleccionar card dog
            var colorSeleccion : Int=ContextCompat.getColor(this,R.color.app_comida_mascotas_cardview_selected)
            cvDog.setBackgroundColor(colorSeleccion)
            //Quitar seleccion a card cat
            var colorNoSeleccion : Int=ContextCompat.getColor(this,R.color.app_comida_mascotas_cardview)
            cvCat.setBackgroundColor(colorNoSeleccion)
            calculateResult()
        }
        cvCat.setOnClickListener {
            //Seleccionar card cat
            animalSelected="cat"
            var colorSeleccion : Int=ContextCompat.getColor(this,R.color.app_comida_mascotas_cardview_selected)
            cvCat.setBackgroundColor(colorSeleccion)
            //Quitar seleccion a card dog
            var colorNoSeleccion : Int=ContextCompat.getColor(this,R.color.app_comida_mascotas_cardview)
            cvDog.setBackgroundColor(colorNoSeleccion)
            calculateResult()
        }
        rsWeight.addOnChangeListener { _,value,_->
            // val df = DecimalFormat("#.##")
            // var resulWeight = df.format(value).toInt()
            var resultWeight = value.toInt()
            weightSelected = resultWeight
            tvWeight.text="$resultWeight Kg"
            calculateResult()
        }
        fabDecrementAge.setOnClickListener {
            if(ageSelected==null||ageSelected==0){
                ageSelected=0
            }else{
                ageSelected = ageSelected!!-1
            }
            tvAge.text="$ageSelected "+getString(R.string.year)
            calculateResult()
        }
        fabAddAge.setOnClickListener {
            if(ageSelected==null){
                ageSelected=1
            }else if(ageSelected!! <70){
                ageSelected=ageSelected!! +1
            }
            tvAge.text="$ageSelected "+getString(R.string.year)
            calculateResult()
        }
    }
    private fun calculateResult(){
        if(animalSelected!=null && ageSelected!=null && weightSelected!=null){
            tvResult.text=calculateWeightFood().toString()
        }
    }
    private fun calculateWeightFood(): Float{
        var percentAge : Double = 0.0
        var percentWeight : Double = 0.0
        if(animalSelected.equals("dog")){
            percentWeight = ((weightSelected!!)+70).toDouble()
            percentAge = when {
                ageSelected!! <= 1 -> 1.20
                ageSelected!! > 8 -> 0.85
                else -> 1.0
            }
        }else{
            percentWeight = ((weightSelected!!)+50).toDouble()
            percentAge = when {
                ageSelected!! <= 1 -> 1.15
                ageSelected!! > 10 -> 0.90
                else -> 1.0
            }
        }
        var result : Float = (percentWeight*percentAge).toFloat()
        return result
    }


}