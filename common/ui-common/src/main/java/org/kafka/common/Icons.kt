package org.kafka.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import compose.icons.FeatherIcons
import compose.icons.FontAwesomeIcons
import compose.icons.TablerIcons
import compose.icons.feathericons.File
import compose.icons.feathericons.Search
import compose.icons.feathericons.Send
import compose.icons.feathericons.User
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Video
import compose.icons.fontawesomeicons.solid.VolumeUp
import compose.icons.tablericons.ArrowLeft
import compose.icons.tablericons.ArrowRight
import compose.icons.tablericons.Book
import compose.icons.tablericons.Bookmark
import compose.icons.tablericons.BrandFirefox
import compose.icons.tablericons.ChevronLeft
import compose.icons.tablericons.ChevronRight
import compose.icons.tablericons.ChevronUpLeft
import compose.icons.tablericons.CircleX
import compose.icons.tablericons.ClipboardX
import compose.icons.tablericons.Download
import compose.icons.tablericons.Eye
import compose.icons.tablericons.EyeOff
import compose.icons.tablericons.Eyeglass
import compose.icons.tablericons.Heart
import compose.icons.tablericons.Home
import compose.icons.tablericons.Home2
import compose.icons.tablericons.Language
import compose.icons.tablericons.Message
import compose.icons.tablericons.Microphone
import compose.icons.tablericons.PlayerPause
import compose.icons.tablericons.PlayerPlay
import compose.icons.tablericons.Repeat
import compose.icons.tablericons.Search
import compose.icons.tablericons.Share
import compose.icons.tablericons.Sun
import compose.icons.tablericons.ThumbDown
import compose.icons.tablericons.ThumbUp
import compose.icons.tablericons.UserPlus
import compose.icons.tablericons.World
import compose.icons.tablericons.X

object Icons {
    val Home: ImageVector
        get() = TablerIcons.Home
    val HomeActive: ImageVector
        get() = TablerIcons.Home2
    val Search: ImageVector
        get() = TablerIcons.Search
    val SearchActive: ImageVector
        get() = FeatherIcons.Search
    val Library: ImageVector
        get() = TablerIcons.Bookmark
    val LibraryActive: ImageVector
        get() = TablerIcons.Book
    val Profile: ImageVector
        get() = FeatherIcons.User
    val Files: ImageVector
        get() = FeatherIcons.File
    val ProfileActive: ImageVector
        get() = FeatherIcons.User
    val GoToTop: ImageVector
        get() = TablerIcons.ChevronUpLeft

    val Language: ImageVector = TablerIcons.Language
    val Follow: ImageVector = TablerIcons.UserPlus
    val Web: ImageVector = TablerIcons.World

    val Sun
        @Composable get() = TablerIcons.Sun
    val Send
        @Composable get() = FeatherIcons.Send
    val Heart
        @Composable get() = TablerIcons.Heart
    val Download
        @Composable get() = TablerIcons.Download
    val Like
        @Composable get() = TablerIcons.ThumbUp
    val Dislike
        @Composable get() = TablerIcons.ThumbDown
    val Comment
        @Composable get() = TablerIcons.Message
    val HeartFilled
        @Composable get() = Icons.Default.Favorite
    val Share
        @Composable get() = TablerIcons.Share
    val Retweet
        @Composable get() = TablerIcons.Repeat
    val Glasses
        @Composable get() = TablerIcons.Eyeglass
    val Next
        @Composable get() = TablerIcons.ChevronRight
    val Previous
        @Composable get() = TablerIcons.ChevronLeft
    val Generic
        @Composable get() = painterResource(id = R.drawable.ic_feather)
    val PlayCircle
        get() = TablerIcons.PlayerPlay
    val Pause
        get() = TablerIcons.PlayerPause
    val X
        @Composable get() = TablerIcons.X
    val XCircle
        @Composable get() = TablerIcons.CircleX
    val Mic
        @Composable get() = TablerIcons.Microphone
    val Volume = FontAwesomeIcons.Solid.VolumeUp
    val Video = FontAwesomeIcons.Solid.Video
    val Clipboard
        @Composable get() = TablerIcons.ClipboardX
    val Eye
        @Composable get() = TablerIcons.Eye
    val EyeOff
        @Composable get() = TablerIcons.EyeOff
    val MoreHorizontal
        @Composable get() = painterResource(id = R.drawable.ic_more_horizontal)
    val Settings
        @Composable get() = painterResource(id = R.drawable.ic_settings)
    val User
        @Composable get() = painterResource(id = R.drawable.ic_settings)
    val PaperClip
        @Composable get() = painterResource(id = R.drawable.ic_settings)
    val Feedback
        @Composable get() = painterResource(id = R.drawable.ic_settings)
    val Book
        @Composable get() = painterResource(id = R.drawable.ic_settings)
    val Info
        @Composable get() = painterResource(id = R.drawable.ic_settings)
    val Logout
        @Composable get() = painterResource(id = R.drawable.ic_settings)
    val Blockquote
        @Composable get() = painterResource(id = R.drawable.ic_quote_top)
    val BrandFirefox = TablerIcons.BrandFirefox
    val School
        @Composable get() = painterResource(id = R.drawable.ic_editors_choice)

    val Back
        @Composable get() = if (LocalLayoutDirection.current == LayoutDirection.Ltr)
            TablerIcons.ArrowLeft
        else
            TablerIcons.ArrowRight
}
