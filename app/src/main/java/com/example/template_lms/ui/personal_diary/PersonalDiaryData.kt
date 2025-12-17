package com.example.template_lms.ui.personal_diary

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentNeutral
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.ui.graphics.vector.ImageVector

enum class Mood(val icon: ImageVector) {
    HAPPY(Icons.Default.SentimentVerySatisfied),
    NEUTRAL(Icons.Default.SentimentNeutral),
    SAD(Icons.Default.SentimentDissatisfied)
}

data class DiaryEntry(
    val id: Int,
    var title: String,
    var content: String,
    var date: String,
    var mood: Mood
)

val diaryEntries = mutableListOf(
    DiaryEntry(
        id = 1,
        title = "First Day of Semester",
        content = "Felt a mix of excitement and nervousness today. The new subjects seem challenging but interesting. Met a few new classmates.",
        date = "2024-08-18",
        mood = Mood.NEUTRAL
    ),
    DiaryEntry(
        id = 2,
        title = "Project Presentation",
        content = "The presentation went better than expected! The professor praised our work, which was a huge relief. All the hard work paid off.",
        date = "2024-08-15",
        mood = Mood.HAPPY
    ),
    DiaryEntry(
        id = 3,
        title = "Struggling with a Concept",
        content = "I'm finding the advanced calculus topic really difficult. Need to ask the professor for help during the next class.",
        date = "2024-08-12",
        mood = Mood.SAD
    )
)
