package com.kafka.ui.content

import android.text.Html
import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.surface.Card
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextOverflow
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp
import com.kafka.data.entities.ContentDetail
import com.kafka.ui.R

@Composable
fun ContentDetailItem(contentDetail: ContentDetail?, actioner: (ContentDetailAction) -> Unit) {
    Column {
        Card(
            modifier = LayoutSize(196.dp, 258.dp) + LayoutGravity.Center,
            shape = RoundedCornerShape(5.dp),
            elevation = 6.dp
        ) {
            DrawImage(image = imageResource(id = R.drawable.img_author_camus_latranger))
        }

        Spacer(modifier = LayoutPadding(top = 20.dp))

        Text(
            text = contentDetail?.title ?: "",
            style = MaterialTheme.typography().h2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = LayoutGravity.Center
        )
        Spacer(modifier = LayoutPadding(2.dp))
        Text(
            text = "by " + contentDetail?.creator ?: "",
            style = MaterialTheme.typography().h6,
            modifier = LayoutGravity.Center
        )
        Spacer(modifier = LayoutPadding(12.dp))

        Clickable(onClick = { actioner(ContentDetailAction.RatingWidgetClick()) }) {
            RatingWidget()
        }

        Spacer(modifier = LayoutPadding(12.dp))
        Text(
            text = contentDetail?.description?.let { Html.fromHtml(it)?.toString() } ?: "",
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography().body2.merge(
                TextStyle(
                    lineHeight = TextUnit.Companion.Em(1.3)
                )
            ),
            modifier = LayoutPadding(20.dp)
        )

        Row(modifier = LayoutPadding(20.dp)) {
            ProvideEmphasis(emphasis = EmphasisLevels().disabled) {
                Button(
                    modifier = LayoutFlexible(0.49f),
                    text = "DOWNLOAD",
                    style = ContainedButtonStyle(
                        backgroundColor = MaterialTheme.colors().surface,
                        shape = RoundedCornerShape(2.dp),
                        elevation = 16.dp
                    ).copy(paddings = EdgeInsets(16.dp))
                )
            }

            Container(modifier = LayoutFlexible(0.04f)) {}

            Button(
                modifier = LayoutFlexible(0.49f),
                text = "LISTEN",
                style = ContainedButtonStyle(
                    backgroundColor = MaterialTheme.colors().surface,
                    elevation = 24.dp,
                    shape = RoundedCornerShape(2.dp),
                    contentColor = MaterialTheme.colors().secondary
                ).copy(paddings = EdgeInsets(16.dp)),
                onClick = {}
            )
        }
    }
}