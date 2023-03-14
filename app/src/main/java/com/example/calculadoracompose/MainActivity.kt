package com.example.calculadoracompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoracompose.ui.theme.CalculadoraComposeTheme
import kotlin.math.pow

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}//class MainActivity


@Composable
fun MainScreen(){
    Scaffold(
        topBar = {MyTopBar()} ,
        bottomBar = { MyBottomBar()}) {
        MyScreenContent()
    }
}

@Composable
fun MyScreenContent(){
    var contadorExterno by remember{
        mutableStateOf(0)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally) {
            CalculatoraScreen()

        }
    }


}

@Composable
fun MyBottomBar(){
    BottomAppBar(modifier = Modifier
        .fillMaxWidth(),
        backgroundColor = Color(0xFF7B19FF )) {
    }
}
@Composable
fun MyTopBar(){
    TopAppBar(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color(0xFFBB86FC)),
    ) {
        Text(text = "Basic Calculator With Jetpack Compose", color = Color.Yellow, fontSize = 15.sp)
    }
}


@Composable
fun CalculatoraScreen() {
    var value1 by remember { mutableStateOf("") }
    var value2 by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var op by remember { mutableStateOf("") }
    var lastResult by remember { mutableStateOf("") }
    var parameter by remember { mutableStateOf(1) }

    // texts inputs
    Column(Modifier.padding(16.dp)) {
        TextField(
            value = value1,
            onValueChange = { value1 = it },
            label = { Text("Value 1") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = value2,
            onValueChange = { value2 = it },
            label = { Text("Value 2") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )


        // lines botons
        Row(
            Modifier
                .padding(vertical = 16.dp)
                .align(CenterHorizontally)) {
            Button(onClick = { operator = "+" }, modifier = Modifier.padding(1.dp)) {
                Text("+")
            }
            Button(onClick = { operator = "-" }, modifier = Modifier.padding(1.dp)) {
                Text("-")
            }
            Button(onClick = { operator = "*" }, modifier = Modifier.padding(1.dp)) {
                Text("*")
            }
            Button(onClick = { operator = "/" }, modifier = Modifier.padding(1.dp)) {
                Text("/")
            }






        }

        Row(
            Modifier
                .padding(vertical = 16.dp)
                .align(CenterHorizontally)){

            Button(onClick = { operator = "%" }, modifier = Modifier.padding(1.dp)
            ) {
                Text("%")
            }

            Button(onClick = { operator = "^" }, modifier = Modifier.padding(1.dp)) {
                Text("^")
            }

            Button(onClick = { operator = "√" }, modifier = Modifier.padding(1.dp)) {
                Text("√")
            }
        }

        // verify the operation
        Row(
            Modifier
                .padding(vertical = 16.dp)
                .align(CenterHorizontally)) {
            when (operator) {
                "+" -> Text(text = "Sum", color = Color(0xFF16c8b9))
                "-" -> Text(text = "Subtration", color = Color(0xFF4bebde))
                "*" -> Text(text = "Multiplication", color = Color(0xFFc7eca9))
                "/" -> Text(text = "Division", color = Color(0xFF1d481b))
                "%" -> Text(text = "Modulation", color = Color(0xFF6eb4da))
                "^" -> Text(text = "Exponenciation", color = Color(0xFF1999de))
                "√" -> Text(text = "Radiance", color = Color(0xFFe5f079))
            }
        }

        // calculate
        Row(
            Modifier
                .padding(vertical = 4.dp)
                .align(CenterHorizontally)){

            Button(modifier = Modifier.padding(2.dp),onClick = {
                if(value1.isNotEmpty() && value2.isNotEmpty() && operator.isNotEmpty()){
                    result = when(operator){
                        "+" -> (value1.toDouble() + value2.toDouble()).toString()
                        "-" -> (value1.toDouble() - value2.toDouble()).toString()
                        "*" -> (value1.toDouble() * value2.toDouble()).toString()
                        "/" -> Divisible(value1, value2)
                        "%" -> (value1.toDouble() % value2.toDouble()).toString()
                        "^" -> (Math.pow(value1.toDouble(), value2.toDouble())).toString()
                        "√" -> Rootable(value1, value2)
                        else -> ""
                    }

                }
            }){
                Text(text = "=")
            }

            Button(modifier = Modifier.padding(2.dp), onClick={
                value1 = ""
                value2 = ""
                operator = ""
                result = ""
            }){
                Text(text = "Clear")
            }
        }


        // prints the result

        if(result.isNotEmpty() && !"VLD".equals(result) && !"VLR".equals(result)){


            if(!op.equals(operator) && parameter == 1 ){
                parameter =0
                lastResult = result
                op = "$value1 $operator $value2"
            } else if(lastResult != result){
                lastResult = result
                op = "$value1 $operator $value2"
            }
            Text(text = "Result: $op  = $result ", color = Color.Green, modifier = Modifier
                .padding(vertical = 16.dp)
                .align(CenterHorizontally))
        } else if("VLD".equals(result)){
            Text(text = "Not Divisible", color = Color.Red, modifier = Modifier
                .padding(vertical = 16.dp)
                .align(CenterHorizontally))
        } else if("VLR".equals(result)){
            Text(text = "Not Radianceble", color = Color.Red, modifier = Modifier
                .padding(vertical = 16.dp)
                .align(CenterHorizontally))
        }



    }
}


fun Divisible(num1: String, num2: String): String{
    if(num2.toInt() == 0){
        return "VLD";
    } else{
        return (num1.toDouble() / num2.toDouble()).toString()
    }

}

fun Rootable(num1: String, num2: String): String{
    if(num1.toInt() == 0){
        return "VLR";
    } else{
        return (num1.toDouble().pow(1/num2.toDouble())).toString()
    }

}