package com.raffa.admin.catalogo.infrastructure
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MainTest {

    @Test
    fun testMain(){
        Assertions.assertNotNull(Main())
        Main.main()
    }
}