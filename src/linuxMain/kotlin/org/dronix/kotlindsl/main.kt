package org.dronix.kotlindsl

private const val hello = "Hello, Kotlin/Native!"

fun main(args: Array<String>) {
    println(hello)

    rPrint {
        out = "ciao"
    }

    r2Print {
        it.out = "ciao"
    }

    oneColoredPrint {
        blue = hello
    }

    oneColoredPrint {
        yellow = hello
    }

    errorLog {
        out = "$hello -> error log"
    }

    multiColorPrint {
        red = "Hello"
        orange = ","
        blue = "Kotlin"
        yellow = "/"
        grey = "Native!"
    }
}

fun rPrint(block: Print.() -> Unit){
    val p = Print("")
    p.block()
    println("\u001B[31m${p.out}\u001B[0m")
}

fun r2Print(block: (Print) -> Unit){
    val p = Print("")
    block(p)
    println("\u001B[31m${p.out}\u001B[0m")
}

fun errorLog(block: Print.() -> Unit){
    val p = Print("")
    block(p)
    oneColoredPrint {
        red = p.out
    }
}

data class Print(var out: String)
data class ColorPrint(var red: String?, var orange: String?, var blue: String?, var yellow: String?, var grey: String?)


fun oneColoredPrint(block: ColorPrint.() -> Unit){
    val p = ColorPrint(null, null,null, null, null)
    p.block()
    val out = when {
        p.red   != null -> "\u001B[31m${p.red}"
        p.orange!= null -> "\u001B[35m${p.orange}"
        p.blue  != null -> "\u001B[34m${p.blue}"
        p.yellow!= null -> "\u001B[32m${p.yellow}"
        p.grey  != null -> "\u001B[37m${p.grey}"
        else            -> ""
    }
    println("$out\u001B[0m")
}

fun multiColorPrint(block: ColorPrint.() -> Unit){
    val p = ColorPrint(null, null,null, null, null)
    p.block()
    var out = ""
    if(p.red   != null) out +="\u001B[31m${p.red}"
    if(p.orange!= null) out +="\u001B[35m${p.orange}"
    if(p.blue  != null) out +="\u001B[34m${p.blue}"
    if(p.yellow!= null) out +="\u001B[32m${p.yellow}"
    if(p.grey  != null) out +="\u001B[37m${p.grey}"

    println("$out\u001B[0m")
}
