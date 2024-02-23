package com.example.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composetest.ui.theme.ComposeTestTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.systemuicontroller.rememberSystemUiController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var dataList = listOf(
            Data("test_card_01.lottie"),
            Data("test_card_02.lottie"),
            Data("test_card_03.lottie")
        )
        setContent {
            ComposeTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
//                    AnimatedImages()
//                    AnimateFullImage()
//                    AnimateLottie(dataList)
                    LottieByLazyColumn(dataList = dataList)
                }
            }
        }
    }
}

class Data(var animFileName: String) {
}

@Composable
fun LottieByLazyColumn(dataList: List<Data>) {
    val systemUiController = rememberSystemUiController()
    systemUiController.isSystemBarsVisible = false

    val duratioRotate = 500
    val angleLeft = -60f
    val angleRight = 60f
    val angleRatio by remember { mutableFloatStateOf(60f) }

    var visible by remember { mutableStateOf(true) }
    var selectedIndex by remember { mutableIntStateOf(0) }

    val angleToLeft by animateFloatAsState(
        targetValue = if (visible) 0f else angleLeft,
        animationSpec = tween(durationMillis = duratioRotate)
    )

    val angleFromRight by animateFloatAsState(
        targetValue = if (visible) angleRight else 0f,
        animationSpec = tween(durationMillis = duratioRotate)
    )

    var lottieCompositionLeft =
        rememberLottieComposition(LottieCompositionSpec.Asset("test_card_01.lottie"))

    var lottieCompositionCenter =
        rememberLottieComposition(LottieCompositionSpec.Asset("test_card_02.lottie"))

    var LottieCompositionRight =
        rememberLottieComposition(LottieCompositionSpec.Asset("test_card_03.lottie"))


    val lottieProgress by animateLottieCompositionAsState(
        composition = lottieCompositionLeft.value,
        iterations = LottieConstants.IterateForever
    )

    Box(modifier = Modifier
        .background(Color.White)
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                visible = !visible
                selectedIndex++
                if (selectedIndex >= dataList.size) selectedIndex = 0

            })
        }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
//                .background(Color.Gray)
        ) {
            LazyRow(content = {
                items(dataList.size) {
                    LottieAnimation(composition = lottieCompositionLeft.value, progress = {
                        lottieProgress
                    }, modifier = Modifier.graphicsLayer {
                        transformOrigin = TransformOrigin(0f, 10f)
                        translationY = 160.dp.toPx()
                        rotationZ = angleToLeft
                    })
                }
//                LottieAnimation(composition = lottieCompositionLeft.value, progress = {
//                    lottieProgress
//                }, modifier = Modifier.graphicsLayer {
//                    transformOrigin = TransformOrigin(0f, 10f)
//                    translationY = 160.dp.toPx()
//                    rotationZ = angleToLeft
//                })
//                LottieAnimation(composition = lottieCompositionCenter.value, progress = { /*TODO*/
//                    lottieProgress
//                }, modifier = Modifier.graphicsLayer {
//                    transformOrigin = TransformOrigin(0f, 10f)
//                    translationY = 160.dp.toPx()
//                    rotationZ = angleFromRight
//
//                })
//
//                LottieAnimation(composition = LottieCompositionRight.value, progress = { /*TODO*/
//                    lottieProgress
//                }, modifier = Modifier.graphicsLayer {
//                    transformOrigin = TransformOrigin(0f, 10f)
//                    translationY = 160.dp.toPx()
//                    rotationZ = angleFromRight
//
//                })
            })


//            LottieAnimation(composition = lottieCompositionLeft.value, progress = {
//                lottieProgress
//            }, modifier = Modifier.graphicsLayer {
//                transformOrigin = TransformOrigin(0f, 10f)
//                translationY = 160.dp.toPx()
//                rotationZ = angleToLeft
//            })
//            LottieAnimation(composition = lottieCompositionCenter.value, progress = { /*TODO*/
//                lottieProgress
//            }, modifier = Modifier.graphicsLayer {
//                transformOrigin = TransformOrigin(0f, 10f)
//                translationY = 160.dp.toPx()
//                rotationZ = angleFromRight
//
//            })
//
//            LottieAnimation(composition = LottieCompositionRight.value, progress = { /*TODO*/
//                lottieProgress
//            }, modifier = Modifier.graphicsLayer {
//                transformOrigin = TransformOrigin(0f, 10f)
//                translationY = 160.dp.toPx()
//                rotationZ = angleFromRight
//
//            })
        }
    }
}

@Composable
fun AnimateLottie(dataList: List<Data>) {
    val systemUiController = rememberSystemUiController()
    systemUiController.isSystemBarsVisible = false

    val duratioRotate = 500
    val angleLeft = -60f
    val angleRight = 60f
    val angleRatio by remember { mutableFloatStateOf(60f) }

    var visible by remember { mutableStateOf(true) }
    var selectedIndex by remember { mutableIntStateOf(0) }

    val angleToLeft by animateFloatAsState(
        targetValue = if (visible) 0f else angleLeft,
        animationSpec = tween(durationMillis = duratioRotate)
    )

    val angleFromRight by animateFloatAsState(
        targetValue = if (visible) angleRight else 0f,
        animationSpec = tween(durationMillis = duratioRotate)
    )

    var lottieCompositionLeft =
        rememberLottieComposition(LottieCompositionSpec.Asset("test_card_01.lottie"))

    var lottieCompositionCenter =
        rememberLottieComposition(LottieCompositionSpec.Asset("test_card_02.lottie"))

    var LottieCompositionRight =
        rememberLottieComposition(LottieCompositionSpec.Asset("test_card_03.lottie"))


    val lottieProgress by animateLottieCompositionAsState(
        composition = lottieCompositionLeft.value,
        iterations = LottieConstants.IterateForever
    )

    Box(modifier = Modifier
        .background(Color.White)
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                visible = !visible
                selectedIndex++
                if (selectedIndex >= dataList.size) selectedIndex = 0

            })
        }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
//                .background(Color.Gray)
        ) {


            LottieAnimation(composition = lottieCompositionLeft.value, progress = {
                lottieProgress
            }, modifier = Modifier.graphicsLayer {
                transformOrigin = TransformOrigin(0f, 10f)
                translationY = 160.dp.toPx()
                rotationZ = angleToLeft
            })
            LottieAnimation(composition = lottieCompositionCenter.value, progress = { /*TODO*/
                lottieProgress
            }, modifier = Modifier.graphicsLayer {
                transformOrigin = TransformOrigin(0f, 10f)
                translationY = 160.dp.toPx()
                rotationZ = angleFromRight

            })

            LottieAnimation(composition = LottieCompositionRight.value, progress = { /*TODO*/
                lottieProgress
            }, modifier = Modifier.graphicsLayer {
                transformOrigin = TransformOrigin(0f, 10f)
                translationY = 160.dp.toPx()
                rotationZ = angleFromRight

            })
        }
    }
}

@Composable
fun AnimateFullImage() {
    val durationTrans = 500
    val duratioRotate = 500
    val angle = -60f
    val angle2 = 60f
//    var visible by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(true) }

    val bgColor: Color by animateColorAsState(
        if (visible) Color.Red else Color.Gray, animationSpec = tween(500, easing = LinearEasing)
    )
    val animatedAlpha by animateFloatAsState(
        targetValue = if (visible) 1.0f else 0f,
        label = "alpha",
        animationSpec = tween(durationMillis = duratioRotate, easing = LinearEasing)
    )
    val animatedAlpha2 by animateFloatAsState(
        targetValue = if (visible) 0f else 1.0f,
        label = "alpha",
        animationSpec = tween(durationMillis = duratioRotate)
    )
    val animatedOffset by animateIntOffsetAsState(
        targetValue = if (visible) IntOffset(0, 0) else IntOffset(-9436, 1396),
        animationSpec = tween(
            durationMillis = durationTrans,
        ),
        label = "offset"
    )

    val animatedOffset2 by animateIntOffsetAsState(
        targetValue = if (visible) IntOffset(9436, 1396) else IntOffset(0, 0),
        animationSpec = tween(
            durationMillis = durationTrans,
        ),
        label = "offset"
    )
    val angleOffset by animateFloatAsState(
        targetValue = if (visible) 0f else angle,
        animationSpec = tween(durationMillis = duratioRotate)
    )

    val angleOffset2 by animateFloatAsState(
        targetValue = if (visible) angle2 else 0f,
        animationSpec = tween(durationMillis = duratioRotate)
    )


    val card1Image = ImageBitmap.imageResource(id = R.drawable.card_01_1)
    val card2Image = ImageBitmap.imageResource(id = R.drawable.card_02_1)
//    val card1Image = ImageBitmap.imageResource(id = R.drawable.card_01)
//    val card2Image = ImageBitmap.imageResource(id = R.drawable.card_02)
    Box(modifier = Modifier
        .background(Color.White)
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                visible = !visible
            })
        }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
//                .background(Color.Gray)
        ) {
            Canvas(modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
//                    transformOrigin = TransformOrigin(0f, 10f)
                    transformOrigin = TransformOrigin(0.5f, 5f)
                    translationX = -148.dp.toPx()
                    translationY = 90.dp.toPx()
                    rotationZ = angleOffset
//                    alpha = animatedAlpha
                }, onDraw = {
                drawImage(image = card1Image)
            })
            Canvas(modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
//                    transformOrigin = TransformOrigin(0f, 10f)
                    transformOrigin = TransformOrigin(0.5f, 5f)
                    translationX = -148.dp.toPx()
                    translationY = 90.dp.toPx()
                    rotationZ = angleOffset2
//                    alpha = animatedAlpha2
                }, onDraw = {
                drawImage(image = card2Image)
            })
        }
    }
}

@Composable
fun AnimatedImages() {
    val durationTrans = 5000
    val duratioRotate = 5000
    val angle = -10f
    val angle2 = 10f
//    var visible by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(true) }

    val bgColor: Color by animateColorAsState(
        if (visible) Color.Red else Color.Gray, animationSpec = tween(500, easing = LinearEasing)
    )
    val animatedAlpha by animateFloatAsState(
        targetValue = if (visible) 1.0f else 0f, label = "alpha"
    )
    val animatedOffset by animateIntOffsetAsState(
        targetValue = if (visible) IntOffset(0, 0) else IntOffset(-9436, 1396),
        animationSpec = tween(
            durationMillis = durationTrans,
        ),
        label = "offset"
    )

    val animatedOffset2 by animateIntOffsetAsState(
        targetValue = if (visible) IntOffset(9436, 1396) else IntOffset(0, 0),
        animationSpec = tween(
            durationMillis = durationTrans,
        ),
        label = "offset"
    )
    val angleOffset by animateFloatAsState(
        targetValue = if (visible) 0f else angle,
        animationSpec = tween(durationMillis = duratioRotate)
    )

    val angleOffset2 by animateFloatAsState(
        targetValue = if (visible) angle2 else 0f,
        animationSpec = tween(durationMillis = duratioRotate)
    )

    Box(modifier = Modifier
        .background(Color.White)
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                visible = !visible
            })
        }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
//                .background(Color.Gray)
        ) {
//            Spacer(modifier = Modifier.height(122.dp))
            Image(
                painter = painterResource(id = R.drawable.card_01),
                contentDescription = "card01",
                modifier = Modifier.graphicsLayer {
                    transformOrigin = TransformOrigin(0.5f, 50f)
                    rotationZ = angleOffset

                })
            Image(
                painter = painterResource(id = R.drawable.card_02),
                contentDescription = "card02",
                modifier = Modifier.graphicsLayer {
                    transformOrigin = TransformOrigin(0.5f, 50f)
                    rotationZ = angleOffset2
                })
        }
    }
}

@Preview(showBackground = true, widthDp = 1280, heightDp = 800)
@Composable
fun PreviewAnimation() {
    var dataList = listOf(
        Data("test_card_01.lottie"),
        Data("test_card_02.lottie"),
        Data("test_card_03.lottie")
    )
//    AnimateFullImage()
//    AnimatedImages()
//    AnimateLottie(dataList)
    LottieByLazyColumn(dataList = dataList)
}


