package ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.authentication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.RegisterRequest
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel.AuthViewModel
import ru.kpfu.itis.android.tbank_design_system.components.buttons.BaseButton
import ru.kpfu.itis.android.tbank_design_system.components.buttons.ButtonSize
import ru.kpfu.itis.android.tbank_design_system.components.buttons.SecondaryButton
import ru.kpfu.itis.android.tbank_design_system.components.inputs.InputTextField
import ru.kpfu.itis.android.tbank_design_system.components.inputs.InputSize
import ru.kpfu.itis.android.tbank_design_system.theme.AppTypography
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel.Result

@Composable
fun RegisterScreen(
    navController: NavController,
    onRegisterSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
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
                    fieldValue = firstName,
                    onValueChanged = { firstName = it },
                    fieldLabel = "Имя",
                    placeholder = "Введите имя",
                    sizes = InputSize.L,
                    modifier = Modifier.weight(1f)
                )

                InputTextField(
                    fieldValue = lastName,
                    onValueChanged = { lastName = it },
                    fieldLabel = "Фамилия",
                    placeholder = "Введите фамилию",
                    sizes = InputSize.L,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(Dimensions.paddingMedium))

            InputTextField(
                fieldLabel = "Номер телефона",
                fieldValue = phone,
                onValueChanged = {
                    val cleanValue = it.replace(Regex("[^0-9]"), "")
                    phone = formatPhoneNumber(cleanValue)
                },
                placeholder = "+7 (777) 777 77 77",
                sizes = InputSize.L,
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
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Phone,
//                    imeAction = ImeAction.Next
//                ),
//                keyboardActions = KeyboardActions(
//                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
//                )
            )

            InputTextField(
                fieldValue = password,
                onValueChanged = { password = it },
                fieldLabel = "Пароль",
                placeholder = "Введите пароль",
                sizes = InputSize.L,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimensions.paddingMedium),
            )

            InputTextField(
                fieldValue = confirmPassword,
                onValueChanged = { confirmPassword = it },
                fieldLabel = "Подтвердите пароль",
                placeholder = "Повторите пароль",
                sizes = InputSize.L,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimensions.paddingLarge),
            )

            BaseButton(
                text = "Зарегистрироваться",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ButtonSize.L.height),
                isLoading = registerState is Result.Loading,
                onClick = {
                    if (password != confirmPassword) {
                        scope.launch { snackbarHostState.showSnackbar("Пароли не совпадают") }
                        return@BaseButton
                    }

                    viewModel.register(
                        RegisterRequest(
                            phone = cleanPhoneNumber(phone),
                            password = password,
                            name = firstName,
                            surname = lastName
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