package com.example.bookreaderapp.screens.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookreaderapp.R
import com.example.bookreaderapp.components.EmailInputField
import com.example.bookreaderapp.components.PasswordInputField
import com.example.bookreaderapp.navigation.BookReaderScreens
import com.example.bookreaderapp.ui.theme.SfProFont

@Composable
fun BookReaderLoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxHeight()
//                .background(
//                    color = colorResource(id = R.color.accent_color),
//                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
//                )
//        )

        FormInput(navController, viewModel)
    }

}

//@Preview(showBackground = true)
@Composable
fun FormInput(
    navController: NavController,
    viewModel: LoginViewModel
) {
    var emailAddress = remember {
        mutableStateOf("")
    }
    var password = remember {
        mutableStateOf("")
    }
    var confirmPassword = remember {
        mutableStateOf("")
    }
    var showPassword = remember {
        mutableStateOf(false)
    }
    var showConfirmPassword = remember {
        mutableStateOf(false)
    }
    var isCreateAccount = remember {
        mutableStateOf(false)
    }
    var valid = remember(emailAddress.value, password.value) {
        emailAddress.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    var loading = remember(valid) {
        mutableStateOf(false)
    }
    var keyboardController = LocalSoftwareKeyboardController.current
    var focusManager = LocalFocusManager.current

    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        if (isCreateAccount.value) {

            Text(
                text = "Create an Account",
                fontSize = 32.sp,
                fontFamily = SfProFont,
                color = colorResource(id = R.color.black),
                fontWeight = FontWeight.SemiBold
            )

            EmailInputField(
                emailAddressState = emailAddress
            )

            PasswordInputField(
                passwordState = password,
                showPassword = showPassword,
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                },
                imeAction = ImeAction.Next
            )

            PasswordInputField(
                passwordState = confirmPassword,
                showPassword = showConfirmPassword,
                labelId = "Confirm Password",
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            )

            FilledButton(
                onClickImpl = {
                    keyboardController?.hide()
                    focusManager.clearFocus()

                    if (password.value == confirmPassword.value) {
                        viewModel.createAccountWithEmailAndPassword(
                            emailAddress.value,
                            password.value
                        )
                        isCreateAccount.value = false

                        emailAddress.value = ""
                        password.value = ""
                        confirmPassword.value = ""

                    } else {
                        Toast.makeText(
                            context,
                            "Password and ConfirmPassword mismatch",
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }

                },
                isCreateAccount = isCreateAccount,
                enabled = emailAddress.value.trim().isNotEmpty() && password.value.trim()
                    .isNotEmpty() && confirmPassword.value.trim().isNotEmpty(),
                loading = loading,
                buttonText = if (isCreateAccount.value) {
                    "Create Account"
                } else {
                    "Login"
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Already have an account?",
                    fontSize = 14.sp,
                    fontFamily = SfProFont,
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = "Login",
                    fontSize = 14.sp,
                    fontFamily = SfProFont,
                    color = colorResource(id = R.color.primary_color),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        isCreateAccount.value = !isCreateAccount.value
                        emailAddress.value = ""
                        password.value = ""
                        confirmPassword.value = ""
                    }
                )

            }
        } else {

            Text(
                text = "Login",
                fontSize = 32.sp,
                fontFamily = SfProFont,
                color = colorResource(id = R.color.black),
                fontWeight = FontWeight.SemiBold
            )

            EmailInputField(
                emailAddressState = emailAddress
            )

            PasswordInputField(
                passwordState = password,
                showPassword = showPassword,
                onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            )

            FilledButton(
                onClickImpl = {
                    keyboardController?.hide()
                    focusManager.clearFocus()

                    viewModel.signInWithEmailAndPassword(emailAddress.value, password.value) {
                        navController.navigate(BookReaderScreens.HOME_SCREEN.name)
                    }
                },
                isCreateAccount = isCreateAccount,
                enabled = emailAddress.value.trim().isNotEmpty() && password.value.trim()
                    .isNotEmpty(),
                loading = loading,
                buttonText = if (isCreateAccount.value) {
                    "Create Account"
                } else {
                    "Login"
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Don't yet have an Account?",
                    fontSize = 14.sp,
                    fontFamily = SfProFont,
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = "Sign Up",
                    fontSize = 14.sp,
                    fontFamily = SfProFont,
                    color = colorResource(id = R.color.primary_color),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        isCreateAccount.value = !isCreateAccount.value
                        emailAddress.value = ""
                        password.value = ""
                    }
                )
            }
        }

    }
}

@Composable
fun FilledButton(
    onClickImpl: () -> Unit,
    isCreateAccount: MutableState<Boolean>,
    enabled: Boolean,
    loading: MutableState<Boolean>,
    buttonText: String,
    modifier: Modifier = Modifier
) {

    Button(
        shape = RoundedCornerShape(10.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primary_color)),
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        onClick = {
            onClickImpl()
        }
    ) {
        if (isCreateAccount.value) {
            Text(
                text = buttonText,
                fontSize = 16.sp,
                fontFamily = SfProFont,
                fontWeight = FontWeight.Medium
            )
        } else {
            Text(
                text = buttonText,
                fontSize = 16.sp,
                fontFamily = SfProFont,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        if (loading.value) {
            CircularProgressIndicator(
                strokeCap = StrokeCap.Round,
                color = colorResource(id = R.color.white),
                modifier = Modifier.size(18.dp)
            )
        } else {
            Box {}
        }

    }

}



