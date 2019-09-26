package com.stingandroid.colourmyviews

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.stingandroid.colourmyviews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private var viewSelected : ImageView? = null

    private var indexShape : Int = -1

    private var red : Int = 0
    private var green : Int = 0
    private var black : Int = 0
    private var white : Int = 0
    private var yellow : Int = 0
    private var blue : Int = 0
    private var transparent : Int = 0
    private var selected : Int = 0
    private var unselected : Int = 0

    private val viewsColored = mutableListOf<Boolean>()
    private val colorPerView = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        initializeColors()
        setListeners()
    }

    private fun initializeColors() {
        red = ContextCompat.getColor(this, R.color.red_painting)
        green = ContextCompat.getColor(this, R.color.green_painting)
        black = ContextCompat.getColor(this, android.R.color.black)
        white = ContextCompat.getColor(this, android.R.color.white)
        yellow = ContextCompat.getColor(this, R.color.yellow_painting)
        blue = ContextCompat.getColor(this, R.color.blue_painting)
        transparent = ContextCompat.getColor(this, android.R.color.transparent)
        selected = ContextCompat.getColor(this,R.color.view_selected)
        unselected = ContextCompat.getColor(this,R.color.view_not_selected)
    }

    private fun setListeners(){
        val buttonList = mutableListOf<Button>()
        val viewsList = mutableListOf<ImageView>()

        binding.apply {
            buttonList.add(redButton)
            buttonList.add(greenButton)
            buttonList.add(yellowButton)
            buttonList.add(blackButton)
            buttonList.add(blueButton)
            buttonList.add(whiteButton)
            viewsList.add(shape1ImageView)
            viewsList.add(shape2ImageView)
            viewsList.add(shape3ImageView)
            viewsList.add(shape4ImageView)
            viewsList.add(shape5ImageView)
            viewsList.add(shape6ImageView)
            viewsList.add(shape7ImageView)
            viewsList.add(shape8ImageView)
            viewsList.add(shape9ImageView)
            viewsList.add(shape10ImageView)
            viewsList.add(shape11ImageView)
            viewsList.add(shape12ImageView)
            viewsList.add(shape13ImageView)
            viewsList.add(shape14ImageView)
            viewsList.add(shape15ImageView)
        }

        for (button in buttonList){
            button.setOnClickListener {
                colourView(it as Button, viewsList)
            }
        }

        for (view in viewsList){
            viewsColored.add(false)
            colorPerView.add(0)
            val index = viewsList.indexOf(view)
            view.setOnClickListener {
                unSelectAll(viewsList)
                selectUnselectView(it as ImageView,index)
            }
        }
    }

    private fun unSelectAll(viewsList: MutableList<ImageView>) {
        for ((i,view) in viewsList.withIndex()) {
            view.background = if (viewsColored[i])
                getColoredDrawable(colorPerView[i], colorPerView[i])
            else getColoredDrawable(white, unselected)
        }
    }

    private fun selectUnselectView(view : ImageView, index : Int) {
        view.background = if (viewsColored[index])
            getColoredDrawable(colorPerView[index], if (view != viewSelected) selected else colorPerView[index])
        else
            getColoredDrawable(white, if (view != viewSelected) selected else unselected)
        indexShape = if (view != viewSelected) index else -1
        viewSelected = if (view != viewSelected) view else null
    }

    private fun colourView(button: Button, viewsList: MutableList<ImageView>) {
        binding.apply{
            if (indexShape == -1){
                setCanvasColor(
                    when (button) {
                        redButton -> red
                        greenButton -> green
                        yellowButton -> yellow
                        blackButton -> black
                        blueButton -> blue
                        whiteButton -> white
                        else -> transparent
                    }
                )
            } else {
                val color : Int = when (button) {
                    redButton -> red
                    greenButton -> green
                    yellowButton -> yellow
                    blackButton -> black
                    blueButton -> blue
                    whiteButton -> white
                    else -> transparent
                }
                viewsList[indexShape].background = getColoredDrawable(color,color)
                viewsColored[indexShape] = true
                colorPerView[indexShape] = color
                viewSelected = null
                indexShape = -1
            }
        }
    }

    private fun setCanvasColor(backgroundColor : Int) {
        val layerDrawable: LayerDrawable = ContextCompat.getDrawable(this, R.drawable.canvas_background) as LayerDrawable
        layerDrawable.mutate()
        val background: GradientDrawable = layerDrawable.findDrawableByLayerId(R.id.backgrund_canvas) as GradientDrawable
        background.setColor(backgroundColor)
        binding.canvasConstraintLayout.background = layerDrawable
    }

    private fun getColoredDrawable(backgroundColor : Int, borderColor : Int) : LayerDrawable {
        val layerDrawable : LayerDrawable = ContextCompat.getDrawable(this,R.drawable.view_background) as LayerDrawable
        layerDrawable.mutate()
        val borderStroke : GradientDrawable = layerDrawable.findDrawableByLayerId(R.id.border_stroke) as GradientDrawable
        borderStroke.setColor(borderColor)
        val background : GradientDrawable = layerDrawable.findDrawableByLayerId(R.id.background_color) as GradientDrawable
        background.setColor(backgroundColor)
        return layerDrawable
    }
}
