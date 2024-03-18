package com.kronst.demo.backend.service

import kotlinx.coroutines.delay
import org.springframework.stereotype.Service

@Service
class GreetingService {

    suspend fun greet(name: String): String {
        delay(2000) // simulate a long-running operation
        return "Hello, $name!"
    }
}
