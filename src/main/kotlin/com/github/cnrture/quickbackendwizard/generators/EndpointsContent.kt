package com.github.cnrture.quickbackendwizard.generators

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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
import com.github.cnrture.quickbackendwizard.components.QBWOutlinedButton
import com.github.cnrture.quickbackendwizard.components.QBWText
import com.github.cnrture.quickbackendwizard.components.QBWTextField
import com.github.cnrture.quickbackendwizard.theme.QBWTheme

data class EndpointInfo(
    val name: String,
    val httpMethod: String,
)

@Composable
fun EndpointsContent(
    moduleBuilder: QBWSpringBootModuleBuilder,
) {
    var endpoints by remember {
        mutableStateOf(listOf(EndpointInfo("", "GET")))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(QBWTheme.colors.gray)
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

        // Plus button to add new endpoint
        QBWButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = "Add Endpoint",
            backgroundColor = QBWTheme.colors.purple,
            onClick = {
                endpoints = endpoints + EndpointInfo("", "GET")
            }
        )
    }
}

@Composable
private fun EndpointRow(
    endpoint: EndpointInfo,
    onEndpointChange: (EndpointInfo) -> Unit,
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
            // Endpoint name field
            QBWTextField(
                modifier = Modifier.weight(1f),
                value = endpoint.name,
                onValueChange = { newName ->
                    onEndpointChange(endpoint.copy(name = newName))
                },
                placeholder = "Endpoint name (e.g. users, orders)"
            )

            // HTTP method dropdown
            HttpMethodDropdown(
                selectedMethod = endpoint.httpMethod,
                onMethodSelected = { newMethod ->
                    onEndpointChange(endpoint.copy(httpMethod = newMethod))
                }
            )

            // Delete button (only show if onDelete is provided)
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

@Composable
private fun HttpMethodDropdown(
    selectedMethod: String,
    onMethodSelected: (String) -> Unit,
) {
    val httpMethods = listOf("GET", "POST", "PUT", "DELETE", "PATCH")
    var expanded by remember { mutableStateOf(false) }

    Box {
        QBWOutlinedButton(
            text = "$selectedMethod ▼",
            backgroundColor = QBWTheme.colors.purple,
            onClick = { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(QBWTheme.colors.black)
        ) {
            httpMethods.forEach { method ->
                DropdownMenuItem(
                    onClick = {
                        onMethodSelected(method)
                        expanded = false
                    }
                ) {
                    QBWText(
                        text = method,
                        color = QBWTheme.colors.white
                    )
                }
            }
        }
    }
}