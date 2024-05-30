package com.ardakazanci.composablespreviews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.ardakazanci.composablespreviews.ui.theme.ComposablesPreviewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposablesPreviewsTheme {

            }
        }
    }
}

data class CustomButtonConfig(
    val content: @Composable RowScope.(Modifier) -> Unit,
    val bgColor: Color,
    val textColor: Color,
)

fun interface CustomButtonData {
    @Composable
    fun createConfig(): CustomButtonConfig
}

private val primaryIconButton = CustomButtonData {
    CustomButtonConfig(
        content = {
            IconButtonContent(text = "Primary Icon Button")
        },
        bgColor = MaterialTheme.colorScheme.primary,
        textColor = MaterialTheme.colorScheme.onPrimary
    )
}

private val secondaryIconButton = CustomButtonData {
    CustomButtonConfig(
        content = {
            IconButtonContent(text = "Secondary Icon Button")
        },
        bgColor = MaterialTheme.colorScheme.secondary,
        textColor = MaterialTheme.colorScheme.onSecondary
    )
}

private val primaryTextButton = CustomButtonData {
    CustomButtonConfig(
        content = {
            TextButtonContent(text = "Primary Text Button")
        },
        bgColor = MaterialTheme.colorScheme.primary,
        textColor = MaterialTheme.colorScheme.onPrimary
    )
}

private val secondaryTextButton = CustomButtonData {
    CustomButtonConfig(
        content = {
            TextButtonContent(text = "Secondary Text Button")
        },
        bgColor = MaterialTheme.colorScheme.secondary,
        textColor = MaterialTheme.colorScheme.onSecondary
    )
}

class CustomButtonConfigProvider : PreviewParameterProvider<CustomButtonData> {
    override val values: Sequence<CustomButtonData> = sequenceOf(
        primaryIconButton, secondaryIconButton, primaryTextButton, secondaryTextButton
    )
}

@Composable
fun IconButtonContent(text: String) {
    Icon(
        imageVector = Icons.Outlined.Star,
        contentDescription = null,
        modifier = Modifier.padding(end = 8.dp)
    )
    Text(text = text)
}

@Composable
fun TextButtonContent(text: String) {
    Text(text = text)
}

@Composable
fun CustomButton(
    bgColor: Color,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (RowScope, Modifier) -> Unit,
) {
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = bgColor,
        contentColor = textColor,
    )
    Button(colors = buttonColors, onClick = onClick, modifier = modifier) {
        content(this, Modifier)
    }
}

@Preview
@Composable
private fun PreviewCustomButton() {
    ComposablesPreviewsTheme{
        Text("Sample")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomButtons(
    @PreviewParameter(CustomButtonConfigProvider::class) buttonData: CustomButtonData
) {
    ComposablesPreviewsTheme{
        val config = buttonData.createConfig()
        CustomButton(
            bgColor = config.bgColor,
            textColor = config.textColor,
            onClick = { /* Perform button click action */ },
            content = config.content
        )
    }

}
