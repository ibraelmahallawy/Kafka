package com.kafka.homepage

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.kafka.common.image.Icons
import com.kafka.common.widgets.IconButton
import com.kafka.common.widgets.IconResource
import com.kafka.ui.components.material.TopBar
import ui.common.theme.theme.Dimens

@Composable
internal fun HomeTopBar(openProfile: () -> Unit) {
    TopBar(
        containerColor = Color.Transparent,
        actions = {
            IconButton(
                onClick = { openProfile() },
                modifier = Modifier
                    .padding(end = Dimens.Spacing24)
                    .size(Dimens.Spacing24)
            ) {
                IconResource(
                    imageVector = Icons.Profile,
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = stringResource(R.string.cd_profile)
                )
            }
        }
    )
}
