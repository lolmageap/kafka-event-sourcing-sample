package com.example.common.annotation

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Transactional(readOnly = false)
annotation class WriteService