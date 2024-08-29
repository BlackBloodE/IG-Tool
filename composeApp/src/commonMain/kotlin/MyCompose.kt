import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.alexzhirkevich.cupertino.CupertinoAlertDialog
import io.github.alexzhirkevich.cupertino.CupertinoAlertDialogNative
import io.github.alexzhirkevich.cupertino.CupertinoButton
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.default
import io.github.alexzhirkevich.cupertino.destructive
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun MyCompose() {
    var isShowAlert by remember { mutableStateOf(false) }
    var isShowNativeAlert by remember { mutableStateOf(false) }
    Row {
        CupertinoButton(
            onClick = {
                isShowAlert = true
            },
        ) {
            CupertinoText("ShowAlert")
        }
        CupertinoButton(
            onClick = {
                isShowNativeAlert = true
            },
        ) {
            CupertinoText("ShowNativeAlert")
        }
    }
    if (isShowAlert) {
        CupertinoAlertDialog(
            onDismissRequest = {
                isShowAlert = false
            },
            title = {
                CupertinoText("Alert")
            }
        ) {
            default(
                onClick = {
                    isShowAlert = false
                }
            ) {
                CupertinoText("default")
            }
            destructive(
                onClick = {
                    isShowAlert = false
                }
            ) {
                CupertinoText("destructive")
            }
        }
    }
    if (isShowNativeAlert) {
        CupertinoAlertDialogNative(
            onDismissRequest = {
                isShowNativeAlert = false
            },
            title = "Alert"
        ) {
            default(
                onClick = {
                    isShowNativeAlert = false
                },
                title = "default"
            )
            destructive(
                onClick = {
                    isShowNativeAlert = false
                },
                title = "destructive"
            )
        }
    }
}