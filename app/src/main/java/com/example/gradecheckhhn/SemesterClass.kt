package com.example.gradecheckhhn

import java.util.*

data class SemesterClass (val id: UUID = UUID.randomUUID(),
        val className: String = "",
        val department: String = "",
        val sectionNumber: Number = 0)