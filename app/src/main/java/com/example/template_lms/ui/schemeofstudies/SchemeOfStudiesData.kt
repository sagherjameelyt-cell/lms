package com.example.template_lms.ui.schemeofstudies

data class SchemeOfStudiesInfo(
    val program: String,
    val session: String,
    val pdf_url: String,
    val uploaded_on: String,
    val status: String,
    val fileType: String = "PDF"
)

// This would typically come from a ViewModel or API call
val schemeOfStudiesInfo = SchemeOfStudiesInfo(
    program = "BSIT",
    session = "2023–2027",
    pdf_url = "https://server.com/scheme_of_studies.pdf",
    uploaded_on = "2024-08-01",
    status = "Current"
)