import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.kpfu.itis.android.t_bank_practice_trips.R
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.authentication.User
import ru.kpfu.itis.android.t_bank_practice_trips.domain.model.expenses.ExpenseCategory
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.viewmodel.AddExpenseViewModel
import ru.kpfu.itis.android.tbank_design_system.components.buttons.BaseButton
import ru.kpfu.itis.android.tbank_design_system.components.dropdown.Dropdown
import ru.kpfu.itis.android.tbank_design_system.components.inputs.InputState
import ru.kpfu.itis.android.tbank_design_system.components.inputs.InputTextField
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    onBack: () -> Unit,
    tripId: Long,
    viewModel: AddExpenseViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.new_expense)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                R.string.back
                            )
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(scrollState)
            ) {
                AmountField(state.amount, viewModel::setAmount)
                Spacer(modifier = Modifier.height(16.dp))

                TitleField(state.title, viewModel::setTitle)
                Spacer(modifier = Modifier.height(16.dp))

                CategoryDropdown(
                    selected = state.selectedCategory,
                    onSelected = viewModel::setCategory
                )
                Spacer(modifier = Modifier.height(16.dp))

                PayerDropdown(
                    participants = state.participants,
                    selected = state.selectedPayer,
                    onSelected = viewModel::setPayer
                )
                Spacer(modifier = Modifier.height(16.dp))

                PaidForMultiSelect(
                    participants = state.participants,
                    selected = state.paidForParticipants,
                    onToggle = viewModel::togglePaidFor
                )
                Spacer(modifier = Modifier.height(16.dp))

                state.error?.let { error ->
                    Text(
                        text = error,
                        color = Color.Red,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        },
        bottomBar = {
            BaseButton(
                text = stringResource(R.string.add),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                onClick = { viewModel.addExpense(onBack) }
            )
        }
    )
}

@Composable
private fun AmountField(
    value: String,
    onValueChange: (String) -> Unit
) {
    InputTextField(
        value = TextFieldValue(value),
        onValueChanged = { onValueChange(it.text) },
        placeholder = stringResource(R.string.enter_amount),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun CategoryDropdown(
    selected: ExpenseCategory?,
    onSelected: (ExpenseCategory) -> Unit
) {
    val categories = remember { ExpenseCategory.values().toList() }
    val categoryNames = categories.associateWith { getCategoryName(it) }

    val selectCategoryText = stringResource(R.string.select_category)
    val selectedText = selected?.let { categoryNames[it] } ?: selectCategoryText

    Dropdown(
        selectedOption = selectedText,
        options = categories.map { categoryNames[it] ?: it.name },
        onOptionSelected = { selectedName ->
            categories.firstOrNull { categoryNames[it] == selectedName }?.let(onSelected)
        }
    )
}

@Composable
private fun PayerDropdown(
    participants: List<User>,
    selected: User?,
    onSelected: (User) -> Unit
) {
    val selectPayerText = stringResource(R.string.select_payer)
    val selectedText = selected?.name ?: selectPayerText

    Dropdown(
        selectedOption = selectedText,
        options = participants.map { it.name },
        onOptionSelected = { name ->
            participants.firstOrNull { it.name == name }?.let(onSelected)
        }
    )
}

@Composable
private fun PaidForMultiSelect(
    participants: List<User>,
    selected: List<User>,
    onToggle: (User) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        InputTextField(
            value = TextFieldValue(selected.joinToString { it.name }.takeIf { it.isNotEmpty() }
                ?: stringResource(R.string.select_participants)),
            onValueChanged = {},
            state = InputState.ReadOnly,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            participants.forEach { participant ->
                DropdownMenuItem(
                    text = { Text(participant.name) },
                    onClick = { onToggle(participant) },
                    trailingIcon = {
                        if (selected.contains(participant)) {
                            Icon(Icons.Default.Check, contentDescription = null)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun TitleField(
    value: String,
    onValueChange: (String) -> Unit
) {
    InputTextField(
        value = TextFieldValue(value),
        onValueChanged = { onValueChange(it.text) },
        placeholder = stringResource(R.string.enter_title),
        modifier = Modifier.fillMaxWidth()
    )
}


@Composable
private fun getCategoryName(category: ExpenseCategory): String {
    return when (category) {
        ExpenseCategory.TICKETS -> stringResource(R.string.category_tickets)
        ExpenseCategory.LODGING -> stringResource(R.string.category_lodging)
        ExpenseCategory.FOOD -> stringResource(R.string.category_food)
        ExpenseCategory.ENTERTAINMENT -> stringResource(R.string.category_entertainment)
        ExpenseCategory.INSURANCE -> stringResource(R.string.category_insurance)
        ExpenseCategory.TRANSPORT -> stringResource(R.string.category_transport)
        ExpenseCategory.OTHER -> stringResource(R.string.category_other)
    }
}