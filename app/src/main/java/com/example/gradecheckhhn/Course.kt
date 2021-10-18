package com.example.gradecheckhhn

import java.util.*

data class Course (val id: UUID = UUID.randomUUID(),
        var courseName: String = "",
        var department: String = "",
        var sectionNumber: Number = 0)