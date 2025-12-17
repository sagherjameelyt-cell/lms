package com.example.template_lms

data class Grade(
    val subject: String,
    val marks: Int,
    val grade: String
)

data class PerformanceSummary(
    val gpa: Float,
    val percentage: Float,
    val status: String
)

val performanceSummary = PerformanceSummary(3.8f, 85f, "Excellent")

val grades = listOf(
    Grade("Mobile Application Development", 88, "A"),
    Grade("Human Computer Interaction", 92, "A+"),
    Grade("Analysis of Algorithms", 76, "B"),
    Grade("Professional Practices", 81, "A-"),
    Grade("Compiler Construction", 70, "B-")
)
