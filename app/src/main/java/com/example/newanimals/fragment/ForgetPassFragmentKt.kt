package com.example.newanimals.fragment

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.newanimals.R
import com.example.newanimals.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth

class ForgetPassFragmentKt:Fragment() {
    private var auth: FirebaseAuth? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                forgetPassView()
            }
        }
    }

    @Composable
    @Preview
    fun forgetPassView() {
        var valueLogin by remember {
            mutableStateOf("")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFD9D9D9)),
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
            ) {
                Box(

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .background(Color(0xffE39D32)),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(R.drawable.logo), contentDescription = "logo",
                        modifier = Modifier
                            .height(86.dp)
                            .width(86.dp)
                    )
                }

            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFD9D9D9)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Text("Забыли пароль?", fontSize = 18.sp, modifier = Modifier.padding(15.dp))
                TextField(
                    value = valueLogin,
                    onValueChange = { newText ->
                        valueLogin = newText
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Person Icon"
                        )
                    },
                    label = { Text(text = "E-mail") },
                    placeholder = { Text(text = "Введите e-mail") },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color(0xFFD9D9D9),
                        focusedLabelColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        textColor = Color.Black,
                        cursorColor = Color.Black
                    )
                )

                Button(
                    onClick = {
                        if (TextUtils.isEmpty(valueLogin)) {
                            Toast.makeText(context, "Введите e-mail", Toast.LENGTH_LONG).show()
                        } else {
                            resetPass(valueLogin)
                        }
                    },
                    modifier = Modifier
                        .padding(top = 16.dp, start = 50.dp, end = 50.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color(0xff47BFE5)),
                    shape = RoundedCornerShape(30.dp),
                ) {
                    Text("ВОССТАНОВИТЬ", modifier = Modifier.padding(5.dp),
                        style = MaterialTheme.typography.button.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        ))
                }
            }
        }
    }

    private fun resetPass(login: String){
        auth = FirebaseAuth.getInstance()
        if (login != "" && login !=null) {
            auth!!.sendPasswordResetEmail(login)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Email sent.")
                        Toast.makeText(activity, "Инструкции по восстановлению пароля\nотправлены на почту", Toast.LENGTH_LONG).show()
                        activity?.supportFragmentManager?.popBackStack()
                    } else {
                        Toast.makeText(activity, "Произошла ошибка, повторите попытку позднее", Toast.LENGTH_LONG).show()

                    }
                }
        } else Toast.makeText(context, "Введите почту!", Toast.LENGTH_LONG).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(): ForgetPassFragmentKt = ForgetPassFragmentKt()
    }
}