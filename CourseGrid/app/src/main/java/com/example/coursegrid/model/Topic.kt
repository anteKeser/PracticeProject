package com.example.coursegrid.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic(
    @StringRes val topicName: Int,
    val numOfCourses: Int,
    @DrawableRes val topicImage: Int
)
