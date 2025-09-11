package com.github.cnrture.quickbackendwizard.generators

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.cnrture.quickbackendwizard.components.QBWButton
import com.github.cnrture.quickbackendwizard.components.QBWText
import com.github.cnrture.quickbackendwizard.components.QBWTextField
import com.github.cnrture.quickbackendwizard.theme.QBWTheme

@Composable
fun EndpointsContent(
    moduleBuilder: QBWSpringBootModuleBuilder,
) {
    var endpoints by remember {
        mutableStateOf(listOf(""))
    }

    LaunchedEffect(endpoints) {
        moduleBuilder.endpoints = endpoints.filter { it.isNotBlank() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        QBWText(
            text = "Endpoints",
            color = QBWTheme.colors.white,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        QBWText(
            text = "Define the REST endpoints you want to generate in your project.",
            color = QBWTheme.colors.lightGray,
            style = TextStyle(fontSize = 14.sp),
        )
        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = QBWTheme.colors.lightGray,
        )

        endpoints.forEachIndexed { index, endpoint ->
            EndpointRow(
                endpoint = endpoint,
                onEndpointChange = { updatedEndpoint ->
                    endpoints = endpoints.toMutableList().apply {
                        set(index, updatedEndpoint)
                    }
                },
                onDelete = if (endpoints.size > 1) {
                    {
                        endpoints = endpoints.toMutableList().apply {
                            removeAt(index)
                        }
                    }
                } else null
            )
        }

        QBWButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = "Add Endpoint",
            backgroundColor = QBWTheme.colors.purple,
            onClick = {
                endpoints = endpoints + ""
            }
        )
    }
}

@Composable
private fun EndpointRow(
    endpoint: String,
    onEndpointChange: (String) -> Unit,
    onDelete: (() -> Unit)? = null,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = QBWTheme.colors.black,
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            QBWTextField(
                modifier = Modifier.weight(1f),
                value = endpoint,
                onValueChange = { newName ->
                    onEndpointChange(newName)
                },
                placeholder = "Endpoint name (e.g. users, orders)"
            )
            onDelete?.let { deleteAction ->
                IconButton(
                    onClick = deleteAction,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete endpoint",
                        tint = QBWTheme.colors.red
                    )
                }
            }
        }
    }
}
