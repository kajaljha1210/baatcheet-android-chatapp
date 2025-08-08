package com.example.baatcheet.ui.screens


import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.baatcheet.R
import com.example.baatcheet.ui.components.AppButton
import com.example.baatcheet.ui.components.AppText
import com.example.baatcheet.ui.components.CircleImage
import com.example.baatcheet.ui.components.HeadingText
import com.example.baatcheet.ui.components.NormalTextField
import com.example.baatcheet.ui.navigation.NavigationItem
import com.example.baatcheet.ui.utils.Validator
import com.example.baatcheet.ui.viewmodel.AuthViewmodel
@Composable
fun ProfileScreen(
    navController: NavController? = null,
    viewModel: AuthViewmodel = hiltViewModel()
) {
    val context = LocalContext.current.applicationContext
    val name = viewModel.authState.name
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val isNameError = name.isNotEmpty() && !Validator.isNameValid(name)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp) // Reserve space for bottom button
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            HeadingText(
                stringResource(id = R.string.profileText),
                MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            AppText(
                stringResource(id = R.string.profile_subtext),
                MaterialTheme.typography.bodyLarge,
                TextAlign.Start
            )

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        launcher.launch("image/*")
                    },
                contentAlignment = Alignment.Center
            ) {
                if (selectedImageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(selectedImageUri),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    CircleImage(150.dp, 80.dp)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            NormalTextField(
                value = name,
                onChange = { viewModel.onNameChanged(it) },
                hint = "Enter your name",
                isError = isNameError,
                keyboardType = KeyboardType.Text
            )

            if (isNameError) {
                Text(
                    text = "Name cannot be empty",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
            }
        }

        // ✅ Bottom fixed button
        AppButton(
            text = stringResource(id = R.string.finish),
            onClick = {
                viewModel.uploadProfile(
                    context,
                    imageUri = selectedImageUri,
                    onSuccess = {
                        viewModel.completeProfileSetup(context) {
                            navController?.navigate(NavigationItem.Home.route) {
                                popUpTo(NavigationItem.Profile.route) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    },
                    onError = { message ->
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                )
            },
            enabled = name.trim().isNotEmpty() && !isNameError,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@Preview(
    name = "Small Phone",
    device = "spec:width=320dp,height=568dp",
    showSystemUi = true
)
@Preview(
    name = "Regular Phone",
    device = "spec:width=360dp,height=640dp",
    showSystemUi = true
)
@Preview(
    name = "Large Phone",
    device = "spec:width=400dp,height=800dp",
    showSystemUi = true
)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}