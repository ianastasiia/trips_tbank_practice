package ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.AuthRequest
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel.AuthViewModel
import ru.kpfu.itis.android.tbank_design_system.components.buttons.BaseButton
import ru.kpfu.itis.android.tbank_design_system.components.buttons.ButtonSize
import ru.kpfu.itis.android.tbank_design_system.components.buttons.SecondaryButton
import ru.kpfu.itis.android.tbank_design_system.components.inputs.InputSize
import ru.kpfu.itis.android.tbank_design_system.components.inputs.InputTextField
import ru.kpfu.itis.android.tbank_design_system.theme.AppTypography
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel.Result

@Composable
fun LoginScreen(
    navController: NavController,
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState by viewModel.loginState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(loginState) {
        when (loginState) {
            is Result.Success -> onLoginSuccess()
            is Result.Error -> scope.launch { snackbarHostState.showSnackbar((loginState as Result.Error).message) }
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
            verticalArrangement = Arrangement.Center,
        ) {
            InputTextField(
                fieldLabel = "Телефон",
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

            Spacer(modifier = Modifier.height(Dimensions.paddingLarge))

            BaseButton(
                text = "Войти",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ButtonSize.L.height),
                isLoading = loginState is Result.Loading,
                onClick = {
                    viewModel.login(
                        AuthRequest(
                            phone = cleanPhoneNumber(phone), password = password
                        )
                    )
                })

            Spacer(modifier = Modifier.height(Dimensions.paddingMedium))

            SecondaryButton(
                text = "Зарегистрироваться",
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate("register") })
        }
    }
}