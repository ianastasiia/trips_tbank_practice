package ru.kpfu.itis.android.t_bank_practice_trips.presentation.screens.trips

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.kpfu.itis.android.t_bank_practice_trips.R
import ru.kpfu.itis.android.tbank_design_system.components.buttons.BaseButton
import ru.kpfu.itis.android.tbank_design_system.components.buttons.SecondaryButton
import ru.kpfu.itis.android.tbank_design_system.components.inputs.InputSize
import ru.kpfu.itis.android.tbank_design_system.components.inputs.InputTextField
import ru.kpfu.itis.android.tbank_design_system.components.inputs.TuiTextArea
import ru.kpfu.itis.android.tbank_design_system.theme.AppTypography
import ru.kpfu.itis.android.tbank_design_system.theme.Dimensions
import ru.kpfu.itis.android.tbank_design_system.theme.LocalExtendedColorScheme

@Composable
fun CreateTripScreen(navController: NavController) {

    val tripName = remember { mutableStateOf("") }
    val startDate = remember { mutableStateOf("") }
    val endDate = remember { mutableStateOf("") }
    val budget = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = Dimensions.paddingHorizontal,
                vertical = Dimensions.paddingLarge
            )
    ) {
        Text(
            text = stringResource(R.string.create_trip_title),
            style = AppTypography.titleLarge,
            color = LocalExtendedColorScheme.current.text01,
            modifier = Modifier.padding(bottom = Dimensions.paddingLarge)
        )

        Spacer(modifier = Modifier.height(Dimensions.paddingLarge))

        InputTextField(
            fieldLabel = stringResource(R.string.new_trip_name),
            fieldValue = tripName.value,
            onValueChanged = { tripName.value = it },
            sizes = InputSize.L,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Dimensions.paddingLarge))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            InputTextField(
                fieldLabel = stringResource(R.string.new_trip_date_start),
                fieldValue = startDate.value,
                onValueChanged = { startDate.value = it },
                modifier = Modifier.weight(1f)
            )

            InputTextField(
                fieldLabel = stringResource(R.string.new_trip_date_end),
                fieldValue = endDate.value,
                onValueChanged = { endDate.value = it },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.paddingLarge))

        InputTextField(
            fieldLabel = stringResource(R.string.new_trip_budget),
            fieldValue = budget.value,
            onValueChanged = { tripName.value = it },
            sizes = InputSize.L,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Dimensions.paddingLarge))

        ParticipantsInput()

        Spacer(modifier = Modifier.height(Dimensions.paddingLarge))

        TuiTextArea(
            label = stringResource(R.string.new_trip_description),
            value = description.value,
            onValueChange = { description.value = it },
            placeholder = stringResource(R.string.new_trip_description_placeholder),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Dimensions.paddingLarge))

        ActionButtons(navController = navController)
    }
}

@Composable
private fun ParticipantsInput() {
//    val participants = remember { mutableStateOf(listOf("Андрей", "Кира", "Александр")) }

    Column {
        InputTextField(
            fieldLabel = "Добавить участника",
            fieldValue = "",
            onValueChanged = { /* Логика добавления */ },
            placeholder = "Введите имя участника",
            sizes = InputSize.L,
            modifier = Modifier.fillMaxWidth()
        )

//        Row(
//            modifier = Modifier.padding(top = 8.dp),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            participants.value.forEach { name ->
//                BaseButton(
//                    text = name,
//                    onClick = { /* Логика удаления */ },
//                    modifier = Modifier.padding(vertical = 4.dp)
//                )
//            }
//        }
    }
}

@Composable
private fun ActionButtons(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        BaseButton(
            text = stringResource(R.string.button_save),
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { /* Логика сохранения */ }
        )
    }

    Spacer(modifier = Modifier.height(Dimensions.paddingMedium))

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        SecondaryButton(
            text = stringResource(R.string.button_cancel),
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}