package ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.RegisterRequest
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel.AuthViewModel
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel.Result
import ru.kpfu.itis.android.tbank_design_system.components.buttons.BaseButton
import ru.kpfu.itis.android.tbank_design_system.components.buttons.ButtonSize
import ru.kpfu.itis.android.tbank_design_system.components.buttons.SecondaryButton
import ru.kpfu.itis.android.tbank_design_system.components.inputs.InputSize
import ru.kpfu.itis.android.tbank_design_system.components.inputs.InputTextField
import ru.kpfu.itis.android.tbank_design_system.theme.AppTypography
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme

@Composable
fun RegisterScreen(
    navController: NavController,
    onRegisterSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var firstName by remember { mutableStateOf(TextFieldValue("")) }
    var lastName by remember { mutableStateOf(TextFieldValue("")) }
    var phone by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }
    val registerState by viewModel.registerState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(registerState) {
        when (registerState) {
            is Result.Success -> onRegisterSuccess()
            is Result.Error -> scope.launch { snackbarHostState.showSnackbar((registerState as Result.Error).message) }
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = Dimensions.paddingLarge),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(Dimensions.paddingLarge))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.paddingMedium)
            ) {
                InputTextField(
                    value = firstName,
                    onValueChanged = { firstName = it },
                    fieldLabel = "Имя",
                    placeholder = "Введите имя",
                    sizes = InputSize.L,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )

                InputTextField(
                    value = lastName,
                    onValueChanged = { lastName = it },
                    fieldLabel = "Фамилия",
                    placeholder = "Введите фамилию",
                    sizes = InputSize.L,
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )
            }

            Spacer(modifier = Modifier.height(Dimensions.paddingMedium))

            InputTextField(
                fieldLabel = "Номер телефона",
                value = phone,
                onValueChanged = { newValue ->
                    val cleanText = newValue.text.filter { it.isDigit() }.take(10)
                    phone = newValue.copy(text = cleanText)
                },
                placeholder = "(777) 777 77 77",
                sizes = InputSize.L,
                visualTransformation = PhoneTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimensions.paddingMedium),
                leadingIcon = {
                    Text(
                        text = "+7",
                        style = AppTypography.bodyMedium,
                        color = LocalExtendedColorScheme.current.text01,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                ),
//                keyboardActions = KeyboardActions(
//                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
//                )
            )

            InputTextField(
                value = password,
                onValueChanged = { password = it },
                fieldLabel = "Пароль",
                placeholder = "Введите пароль",
                sizes = InputSize.L,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimensions.paddingMedium),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                )
            )

            InputTextField(
                value = confirmPassword,
                onValueChanged = { confirmPassword = it },
                fieldLabel = "Подтвердите пароль",
                placeholder = "Повторите пароль",
                sizes = InputSize.L,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimensions.paddingLarge),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )

            BaseButton(
                text = "Зарегистрироваться",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ButtonSize.L.height),
//                isLoading = registerState is Result.Loading,
                onClick = {
                    if (password != confirmPassword) {
                        scope.launch { snackbarHostState.showSnackbar("Пароли не совпадают") }
                        return@BaseButton
                    }

                    viewModel.register(
                        RegisterRequest(
                            phone = phone.text,
                            password = password.text,
                            name = firstName.text,
                            surname = lastName.text
                        )
                    )
                })

            Spacer(modifier = Modifier.height(Dimensions.paddingMedium))

            SecondaryButton(
                text = "Уже есть аккаунт",
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.popBackStack() })
        }
    }
}