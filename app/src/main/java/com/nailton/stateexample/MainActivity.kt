package com.nailton.stateexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nailton.stateexample.ui.theme.StateExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DemoScreen()
                    FunctionA()
                }
            }
        }
    }
}

/**
 * Utilizando funcoes composable e definindo elementos, implementando estado e modificando
 * valores dos elementos, FUNCOES DemoScreen e MyTextField. OBS: Sempre lembrar de usar elevacao
 * de estado assim poderemos reutilizar elementos, ao criarmos um estado sempre lembrar de usar o
 * rememberSaveable que permititra mudar confiugracoes do app como rotacao de tela e mantermos o valor
 * do estado atual entre as mudancas.
 */
@Composable
private fun DemoScreen() {
    var state by rememberSaveable { mutableStateOf("") }
    val onChange = {it: String ->
        state = it
    }
    Column(
        Modifier.fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterHorizontally
    ) {
        MyTextField(state, onChange)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyTextField(state: String, onChange: (String) -> Unit) {
    TextField(
        modifier = Modifier
            .padding(10.dp)
            .width(300.dp)
            .height(60.dp),
        value = state,
        onValueChange = onChange,
        placeholder = { Text(text = "Digite Algo")},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        textStyle = TextStyle(
            fontSize = 22.sp,
            fontFamily = FontFamily.Monospace
        )
    )
}

/**
 * Fluxo de dados Unidirecional é onde o estado armazenado em um elemento que pode ser composto,
 *  não deve ser alterado diretamente por nenhuma função filha que pode ser composta
 */

@Composable
private fun FunctionA() {
    var switchstate by rememberSaveable { mutableStateOf(true) }
    val onSwitchChange = { value : Boolean ->
        switchstate = value
    }

    FunctionB(state = switchstate, func = onSwitchChange )
}

@Composable
private fun FunctionB(state: Boolean, func: (Boolean) -> Unit) {
    Column(
        Modifier.fillMaxSize(),
        Arrangement.Top,
        Alignment.CenterHorizontally
    ) {
        Switch(checked = state, onCheckedChange = func)
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StateExampleTheme {
        DemoScreen()
        FunctionA()
    }
}