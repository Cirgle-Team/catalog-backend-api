package org.cirgle.catalog_backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CatalogBackendApplication

fun main(args: Array<String>) {
    runApplication<CatalogBackendApplication>(*args)
}
