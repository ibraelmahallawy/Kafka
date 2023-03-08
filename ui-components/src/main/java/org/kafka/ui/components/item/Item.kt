package org.kafka.ui.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kafka.data.entities.Item
import org.kafka.common.widgets.shadowMaterial
import org.kafka.ui.components.R
import ui.common.theme.theme.Dimens
import ui.common.theme.theme.textPrimary

@Composable
fun Item(item: Item, modifier: Modifier = Modifier, openItemDetail: (String) -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { openItemDetail(item.itemId) }
            .padding(vertical = Dimens.Spacing08, horizontal = Dimens.Spacing16),
        horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing16)
    ) {
        CoverImage(item)
        ItemDescription(item)
    }
}

@Composable
fun CoverImage(item: Item) {
    Box(
        modifier = Modifier.shadowMaterial(
            elevation = Dimens.Spacing08,
            shape = RoundedCornerShape(Dimens.RadiusSmall)
        )
    ) {
        AsyncImage(
            model = item.coverImage,
            placeholder = painterResource(id = R.drawable.ic_absurd_bulb),
            contentDescription = "Cover",
            modifier = Modifier
                .size(72.dp, 84.dp)
                .background(MaterialTheme.colorScheme.surface),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ItemDescription(item: Item) {
    Column {
        ItemTitle(item.title)
        Spacer(modifier = Modifier.height(Dimens.Spacing02))
        ItemCreator(item.creator?.name)
        Spacer(modifier = Modifier.height(Dimens.Spacing04))
        ItemType(item.mediaType)
    }
}

@Composable
fun ItemTitle(title: String?) {
    Text(
        text = title.orEmpty(),
        style = MaterialTheme.typography.bodyMedium,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.textPrimary
    )
}

@Composable
fun ItemCreator(creator: String?) {
    Text(
        text = creator.orEmpty(),
        style = MaterialTheme.typography.bodySmall,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.secondary
    )
}

@Composable
fun ItemType(mediaType: String?) {
    Text(
        text = mediaType.orEmpty(),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.tertiary
    )
}
