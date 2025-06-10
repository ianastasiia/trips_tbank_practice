package ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.authentication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.kpfu.itis.android.t_bank_practice_trips.R
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
    var phoneState: TextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var password: TextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
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
                fieldLabel = stringResource(R.string.phone),
                value = phoneState,
                onValueChanged = { newValue ->
                    val cleanText = newValue.text.filter { it.isDigit() }.take(10)
                    phoneState = newValue.copy(text = cleanText)
                },
                placeholder = stringResource(R.string.phone_hint),
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
                )
//                keyboardActions = KeyboardActions(
//                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
//                )
            )

            InputTextField(
                value = password,
                onValueChanged = { password = it },
                fieldLabel = stringResource(R.string.password),
                placeholder = stringResource(R.string.password),
                sizes = InputSize.L,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimensions.paddingMedium),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(Dimensions.paddingLarge))

            BaseButton(
                text = stringResource(R.string.login),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ButtonSize.L.height),
//                isLoading = loginState is Result.Loading,
                onClick = {
                    viewModel.login(
                        AuthRequest(
                            phone = phoneState.text,
                            password = password.text
                        )
                    )
                })

            Spacer(modifier = Modifier.height(Dimensions.paddingMedium))

            SecondaryButton(
                text = stringResource(R.string.register),
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate("register") })
        }
    }
}