package com.on1k.caloriesmacroscalculater

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.on1k.caloriesmacroscalculater.databinding.ActivityMainBinding

data class NutritionProduct(val name: String, val prices: List<Double>)
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainProductList = mutableListOf<NutritionProduct>()
    private lateinit var mainListView: ListView
    private lateinit var secondListView: ListView
    private lateinit var thirdListView: ListView
    private lateinit var fourthListView: ListView
    private lateinit var totalTextView: TextView
    private lateinit var mainAdapter: ArrayAdapter<String>
    private val selectedProducts = mutableListOf<NutritionProduct>()
    private val protein = listOf(
        NutritionProduct("chicken (100g)", listOf(165.0, 31.0, 3.6, 0.0)),
        NutritionProduct("chicken (150g)", listOf(247.5, 46.5, 5.4, 0.0)),
        NutritionProduct("chicken (200g)", listOf(330.0, 62.0, 7.2, 0.0)),
        NutritionProduct("chicken (250g)", listOf(412.5, 77.5, 9.0, 0.0)),
        // add more here
    )
    private val yaglar = listOf(
        NutritionProduct("almond (50g)", listOf(106.0, 24.7, 10.9, 10.8)),
        NutritionProduct("almond (100g)", listOf(213.0, 49.4, 21.7, 12.5)),
        // add more here
    )
    private val karbonhidrat = listOf(
        NutritionProduct("rice (100g)", listOf(362.0, 7.0, 0.7, 77.0)),
        NutritionProduct("rice (150g)", listOf(543.0, 10.5, 1.05, 115.5)),
        NutritionProduct("rice (200g)", listOf(724.0, 14.0, 1.4, 154.0)),
        NutritionProduct("rice (250g)", listOf(905.0, 17.5, 1.75, 192.5)),
        // add more here
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainListView = findViewById(R.id.diyetLV)
        secondListView = findViewById(R.id.proteinLV)
        thirdListView = findViewById(R.id.karbonhLV)
        fourthListView = findViewById(R.id.yagLV)
        totalTextView = findViewById(R.id.totalTextView)

        mainAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mainProductList.map { it.name })
        mainListView.adapter = mainAdapter

        val secondAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, protein.map { it.name })
        secondListView.adapter = secondAdapter

        val thirdAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, karbonhidrat.map { it.name })
        thirdListView.adapter = thirdAdapter

        val fourthAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, yaglar.map { it.name })
        fourthListView.adapter = fourthAdapter

        val onItemClickListener = { productList: List<NutritionProduct>, position: Int ->
            val selectedProduct = productList[position]
            selectedProducts.add(selectedProduct)
            updateMainListView()
            updateTotalValues()
        }

        secondListView.setOnItemClickListener { _, _, position, _ ->
            onItemClickListener(protein, position)
        }

        thirdListView.setOnItemClickListener { _, _, position, _ ->
            onItemClickListener(karbonhidrat, position)
        }

        fourthListView.setOnItemClickListener { _, _, position, _ ->
            onItemClickListener(yaglar, position)
        }

        binding.temizle.setOnClickListener {
            selectedProducts.clear()
            updateMainListView()
            updateTotalValues()
            totalTextView.text = "total:\n  calories 0,\n  Protein 0,\n  fat 0,\n  carb 0"
        }

        binding.proteinBTN.setOnClickListener {
            if (binding.proteinFL.visibility == View.GONE) {
                binding.proteinFL.visibility = View.VISIBLE
            } else {
                binding.proteinFL.visibility = View.GONE
            }
        }
        binding.karbonhBTN.setOnClickListener {
            if (binding.karbonhFL.visibility == View.GONE) {
                binding.karbonhFL.visibility = View.VISIBLE
            } else {
                binding.karbonhFL.visibility = View.GONE
            }
        }
        binding.yagBTN.setOnClickListener {
            if (binding.yagFL.visibility == View.GONE) {
                binding.yagFL.visibility = View.VISIBLE
            } else {
                binding.yagFL.visibility = View.GONE
            }
        }

    }

    private fun updateMainListView() {
        mainProductList.clear()
        mainProductList.addAll(selectedProducts)
        mainAdapter.clear()
        mainAdapter.addAll(mainProductList.map { it.name })
        mainAdapter.notifyDataSetChanged()
    }

    private fun updateTotalValues() {
        var totalCalories = 0.0
        var totalProtein = 0.0
        var totalFat = 0.0
        var totalCarbs = 0.0

        for (product in selectedProducts) {
            totalCalories += product.prices[0]
            totalProtein += product.prices[1]
            totalFat += product.prices[2]
            totalCarbs += product.prices[3]
        }

        totalTextView.text = "Toplam:\n Kalori $totalCalories,\n Protein $totalProtein,\n Yağ $totalFat,\n Karbonhidrat $totalCarbs"
    }

}