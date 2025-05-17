package ru.kpfu.itis.android.t_bank_practice_trips

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ru.kpfu.itis.android.t_bank_practice_trips.presentation.navigation.AppNavigation
import ru.kpfu.itis.android.tbank_design_system.theme.AppTheme
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                AppNavigation()
            }
        }
    }

//        setContent {
//            AppTheme {
//                AppNavigation()
//            }

//            MaterialTheme(

//                colorScheme = lightColorScheme(
////                    primary = Color(0xFF6750A4),
////                    onPrimary = Color.White,
////                    surface = Color.White,
////                    onSurface = Color(0xFF1C1B1F),
//                )
//            ) {
//
//                AppNavigation()
//                val navController = rememberNavController()
//
//                NavHost(
//                    navController = navController,
//                    startDestination = "main"
//                ) {
//                    composable("main") { MainScreen() }
//                }
//        }
//    }

//    override fun onResume() {
//        super.onResume()
//        recreate()
//    }

    override fun attachBaseContext(newBase: Context) {
        val prefs = newBase.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val lang = prefs.getString("language", "ru") ?: "ru"
        super.attachBaseContext(wrapContext(newBase, lang))
    }

    private fun wrapContext(context: Context, lang: String): Context {
        val config = Configuration(context.resources.configuration)
        config.setLocale(Locale(lang))
        return context.createConfigurationContext(config)
    }

}