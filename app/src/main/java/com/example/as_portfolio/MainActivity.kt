package com.example.as_portfolio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.as_portfolio.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var newOperation = true
        var lastNumeric = false
        var dot = false

        val numericButtons = listOf(binding.row4buttonOne, binding.row4buttonTwo, binding.row4buttonThree, binding.row3buttonOne, binding.row3buttonTwo, binding.row3buttonThree, binding.row2buttonOne, binding.row2buttonTwo, binding.row2buttonThree, binding.row1buttonTwo)
        val operatorButtons = listOf(binding.buttonFour, binding.row4buttonFour, binding.row3buttonFour, binding.row2buttonFour)

        numericButtons.forEach { button ->
            button.setOnClickListener {
                if (newOperation) {
                    binding.Question.text = ""
                    newOperation = false
                }
                binding.Question.append((it as Button).text)
                lastNumeric = true
            }
        }

        operatorButtons.forEach { button ->
            button.setOnClickListener {
                if (lastNumeric && !newOperation) {
                    binding.Question.append((it as Button).text)
                    lastNumeric = false
                    dot = false
                }
            }
        }

        // For the brackets buttons
        listOf(binding.buttonTwo, binding.buttonThree).forEach { button ->
            button.setOnClickListener {
                binding.Question.append((it as Button).text)
                lastNumeric = (it as Button).text == ")"
                dot = false // reset the DOT flag
            }
        }

        binding.row1buttonThree.setOnClickListener {
            if (lastNumeric && !dot) {
                binding.Question.append(".")
                lastNumeric = false
                dot = true
            }
        }

        binding.row1buttonOne.setOnClickListener {
            binding.Question.text = ""
            binding.Result.text = ""
            lastNumeric = false
            dot = false
            newOperation = true
        }

        binding.row1buttonFour.setOnClickListener {
            if (lastNumeric) {
                val txt = binding.Question.text.toString()
                val result = evaluateExpression(txt)
                binding.Result.text = result.toString()
                newOperation = true
            }
        }
    }

    // This function evaluates the string expression
    private fun evaluateExpression(txt: String): Double {
        val expression = ExpressionBuilder(txt).build()
        return expression.evaluate()
    }
}
