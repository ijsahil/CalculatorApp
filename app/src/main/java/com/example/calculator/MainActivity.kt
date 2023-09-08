package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var displayView: TextView? = null
    var lastNumeric = false
    var lastDot = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        displayView = findViewById(R.id.tvId)
    }

    fun btnClick(view: View) {
        displayView?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun btnOnClear(view: View) {
        displayView?.text = ""
        lastNumeric = false
    }

    fun btnDot(view: View) {
        if (lastNumeric && !lastDot) {
            displayView?.append(".")
            lastDot = true
            lastNumeric = false
        }
    }

    fun btnOperators(view: View) {
        displayView?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                displayView?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun btnEqual(view: View) {
        var tvValue = displayView?.text.toString()
        if (lastNumeric && tvValue.contains("+") || tvValue.contains("-") || tvValue.contains("*")
            || tvValue.contains("/")
        ) {
            var tvValue = displayView?.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")) {
                    tvValue = tvValue.substring(1)
                    prefix = "-"
                }
                //Condition for minus operator
                if (tvValue.contains("-")) {
                    var splitValue = tvValue.split("-")
                    var zero = splitValue[0]
                    var one = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        zero = prefix + zero
                    }
                    displayView?.text = removeDotZero((zero.toDouble() - one.toDouble()).toString())
                } else if (tvValue.contains("+")) {
                    var splitValue = tvValue.split("+")
                    var zero = splitValue[0]
                    var one = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        zero = prefix + zero
                    }
                    displayView?.text = removeDotZero((zero.toDouble() + one.toDouble()).toString())
                } else if (tvValue.contains("*")) {
                    var splitValue = tvValue.split("*")
                    var zero = splitValue[0]
                    var one = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        zero = prefix + zero
                    }
                    displayView?.text = removeDotZero((zero.toDouble() * one.toDouble()).toString())
                } else {
                    var splitValue = tvValue.split("/")
                    var zero = splitValue[0]
                    var one = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        zero = prefix + zero
                    }
                    displayView?.text = removeDotZero((zero.toDouble() / one.toDouble()).toString())
                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }

        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") ||
                    value.contains("*") ||
                    value.contains("-") ||
                    value.contains("+")
        }
    }

    private fun removeDotZero(result: String): String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
            return value
        }
        return result
    }

    fun removeBtn(view: View) {
        if (displayView?.text.toString().length > 0) {
            displayView?.text?.let {
                var value = displayView?.text.toString()
                displayView?.text = ""
                var finalValue = value.substring(0, value.length - 1)
                displayView?.append(finalValue)
            }
        }
    }
}