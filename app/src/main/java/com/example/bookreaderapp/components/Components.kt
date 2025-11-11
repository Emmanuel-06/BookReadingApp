package com.example.bookreaderapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bookreaderapp.R
import com.example.bookreaderapp.model.MBook
import com.example.bookreaderapp.ui.theme.SfProFont


@Composable
fun EmailInputField(
    emailAddressState: MutableState<String>,
    imeAction: ImeAction = ImeAction.Next,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = emailAddressState.value,
        onValueChange = { emailAddressState.value = it },
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
        label = {
            Text(
                text = "Email",
                fontSize = 14.sp,
                fontFamily = SfProFont,
                fontWeight = FontWeight.Normal
            )
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = SfProFont,
            fontWeight = FontWeight.Normal
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
    )
}


@Composable
fun PasswordInputField(
    passwordState: MutableState<String>,
    showPassword: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Done,
    labelId: String = "Password",
    onDone: () -> Unit,
    modifier: Modifier = Modifier
) {

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        shape = RoundedCornerShape(10.dp),
        singleLine = true,
//            placeholder = {
//                Text(
//                    text = "Password",
//                    fontSize = 14.sp,
//                    fontFamily = _root_ide_package_.com.example.bookreaderapp.ui.theme.SfProFont,
//                    fontWeight = FontWeight.Medium
//                )
//            },
        label = {
            Text(
                text = labelId,
                fontSize = 14.sp,
                fontFamily = SfProFont,
                fontWeight = FontWeight.Normal
            )
        },
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = SfProFont,
            fontWeight = FontWeight.Normal
        ),
        trailingIcon = {
            IconButton(onClick = {
                showPassword.value = !showPassword.value
            }) {
                if (showPassword.value) {
                    Icon(
                        imageVector = ImageVector
                            .vectorResource(id = R.drawable.view_off_slash),
                        contentDescription = "visible"
                    )
                } else {
                    Icon(
                        imageVector = ImageVector
                            .vectorResource(id = R.drawable.view),
                        contentDescription = "hide"
                    )
                }
            }
        },
        visualTransformation = if (showPassword.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onDone = {
            onDone()
        }
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentReadCard(
    book: MBook = MBook("", "", listOf("",""), ""),
    onClickDetails: (String) -> Unit = {}
) {

    val context = LocalContext.current
    val resources = context.resources

    val displayMetrics = resources.displayMetrics

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(14.dp),
        colors = CardDefaults.cardColors(colorResource(id = R.color.white)),
        modifier = Modifier
            .fillMaxWidth()
            .height(148.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.psychology_of_money),
                contentDescription = "null",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(height = 110.dp, width = 76.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Book Title",
                        fontFamily = SfProFont,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.black)
                    )

                    Text(
                        text = "Author's Name",
                        fontFamily = SfProFont,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(id = R.color.black)
                    )
                }
                Text(
                    text = "Continue Reading",
                    fontFamily = SfProFont,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.primary_color),
                )
            }
        }
    }
}

@Composable
fun ReadingListCard(
    book: MBook,
    modifier: Modifier,
    onClick: (String) -> Unit = {},
) {

    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(colorResource(id = R.color.white)),
        modifier = Modifier
            .size(height = 200.dp, width = 160.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.maketime),
                    contentDescription = "null",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(height = 110.dp, width = 76.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .padding(top = 4.dp)

                )

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.heart_outline),
                        contentDescription = "favourite",
                        modifier = Modifier
                            .size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = book.title ?: "",
                    fontFamily = SfProFont,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.black),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                book.authors?.forEach{ author ->
                    Text(
                        text = author,
                        fontFamily = SfProFont,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = R.color.black).copy(0.8f)
                    )
                }
                Text(
                    text = "rating",
                    fontFamily = SfProFont,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.black).copy(0.8f)
                )
            }
        }
    }
}

@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontFamily = SfProFont,
            color = colorResource(id = R.color.black),
            fontWeight = FontWeight.Bold,
        )
    }
}