package com.example.gradecheckhhn

import java.util.*

data class SemesterClass (val id: UUID = UUID.randomUUID(),
        var className: String = "",
        var department: String = "",
        var sectionNumber: Number = 0)