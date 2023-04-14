package com.assignment.jumboshop.ui.cart.parts

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.assignment.design_system.components.buttons.JumboButton
import com.assignment.design_system.components.texts.JumboSubtitle
import com.assignment.design_system.components.texts.JumboTitle

@Composable
fun CheckOutCard(cartItemsCount:Int, totalCost: Double, currency: String) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .border(
                    border = BorderStroke(1.dp, Color.White),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(10.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            JumboSubtitle(text = "Checkout ($cartItemsCount)")
            Spacer(modifier = Modifier.weight(1f))
            JumboTitle(text = "$currency $totalCost")
            Spacer(modifier = Modifier.width(8.dp))
            JumboButton(
                text = "Pay",
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {}
        }
    }
}
