package com.capstone.have.data

data class CustomClassifications(val label: String, val classifications: List<Classification>)

data class Classification(val className: String, val score: Float)


