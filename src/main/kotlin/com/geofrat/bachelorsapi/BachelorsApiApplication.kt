package com.geofrat.bachelorsapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BachelorsApiApplication

fun main(args: Array<String>) {
    runApplication<BachelorsApiApplication>(*args)
}
