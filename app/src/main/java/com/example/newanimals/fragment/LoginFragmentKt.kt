package com.example.newanimals.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.newanimals.R
import com.example.newanimals.activity.MainActivity
import com.example.newanimals.activity.WelcomeActivity
import com.google.firebase.auth.FirebaseAuth

class LoginFragmentKt: Fragment() {
    companion object{
        @JvmStatic
        fun newInstance(): LoginFragmentKt = LoginFragmentKt()
    }
    private var auth: FirebaseAuth? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                loginView()
            }
        }
    }


    @Composable
    @Preview
    private fun loginView() {
        var valueLogin by remember {
            mutableStateOf("")
        }
        var showPassword by remember {
            mutableStateOf(false)
        }
        var valuePass by remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD9D9D9))
              ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xffE39D32))
            ) {
                IconButton(onClick = { activity?.supportFragmentManager?.popBackStack() }) {
                    Image(imageVector = Icons.Default.ArrowBack, contentDescription = "arrow back")

                }
            }

            Card(
                shape = RoundedCornerShape(bottomStart = 1000.dp, bottomEnd = 1000.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(Color(0xFFD9D9D9))
                    .padding(bottom = 15.dp)
            ) {    Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(Color(0xffE39D32)),
                contentAlignment = Alignment.Center,
            ){
                Image(painter = painterResource(R.drawable.logo), contentDescription ="logo",
                    modifier = Modifier
                        .height(86.dp)
                        .width(86.dp))
            }

            }

            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD9D9D9)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top) {

                TextField(
                    value = valueLogin,
                    onValueChange = { newText ->
                        valueLogin = newText
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Person Icon"
                        )
                    },
                    label = { Text(text = "Логин") },
                    placeholder = { Text(text = "Введите логин") },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(0xFFD9D9D9),
                        focusedLabelColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        textColor = Color.Black,
                        cursorColor = Color.Black
                    )
                )
                TextField(
                    value = valuePass,
                    onValueChange = { newText ->
                        valuePass = newText
                    },
                    visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    label = { Text(text = "Пароль") },
                    placeholder = { Text(text = "Введите пароль") },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color(0xFFD9D9D9),
                        focusedLabelColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        textColor = Color.Black,
                        cursorColor = Color.Black),
                    trailingIcon = {
                        IconButton(onClick = { showPassword = !showPassword }) {
                            Icon(
                                imageVector = if (showPassword) Icons.Default.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = if (showPassword) "Show Password" else "Hide Password"
                            )
                        }
                    },

                    )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(end = 65.dp, top = 8.dp), horizontalArrangement = Arrangement.End) {
                    Text("Забыли пароль?", fontSize = 14.sp, color = Color(0xffE39D32),
                        modifier = Modifier.clickable {
                            (activity as MainActivity?)!!.replaceFragment(
                                ForgetPassFragmentKt.newInstance(),
                                false
                            )
                        })
                }

                Button(
                    onClick = {
                        if (TextUtils.isEmpty(valueLogin) || TextUtils.isEmpty(valuePass)) {
                            Toast.makeText(context, "Заполните все поля", Toast.LENGTH_LONG).show()
                        } else {
                            auth(valueLogin.trim(), valuePass.trim())
                        }
                    },
                    modifier = Modifier
                        .padding(top = 16.dp, start = 50.dp, end = 50.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color(0xff47BFE5)),
                    shape = RoundedCornerShape(30.dp),
                ) {
                    Text("ВОЙТИ", modifier = Modifier.padding(5.dp),
                        style = MaterialTheme.typography.button.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ))
                }

                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(bottom = 12.dp), contentAlignment = Alignment.BottomCenter){
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text("Нет аккаунта?", Modifier.padding(end = 10.dp),
                        fontSize = 16.sp)
                        Text("Зарегистрироваться", fontSize = 16.sp, color = Color(0xffE39D32),
                        modifier = Modifier.clickable {
                            (activity as MainActivity?)!!.replaceFragment(
                                ChooseRegFragment.newInstance(),
                                false
                            )
                        })
                    }
                }
            }
        }
    }

    private fun auth(login: String, pass: String) {
        auth = FirebaseAuth.getInstance()
        if(login != "" && pass !=""){
            auth?.signInWithEmailAndPassword(login, pass)
                ?.addOnCompleteListener{task->
                    if(task.isSuccessful){
                        startActivity(Intent(activity, WelcomeActivity::class.java))
                    } else{
                        Toast.makeText(context, "Ошибка аунтефикации", Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}