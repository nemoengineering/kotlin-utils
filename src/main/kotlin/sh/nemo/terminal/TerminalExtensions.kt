package sh.nemo.terminal

import com.github.ajalt.mordant.TermColors
import sh.nemo.terminal.Terminal.echo

val terminalColors = TermColors(TermColors.Level.ANSI256)

fun TermColors.deleteLastLine() {
    print(String.format("\u001b[%dA", 1)) // Move up
    print("\u001b[2K")
}

fun Any?.printAsError() {
    echo(terminalColors.red(this.toString()))
}

fun Any?.printAsSuccess() {
    echo(terminalColors.green(this.toString()))
}

fun Any?.printAsWarning() {
    echo(terminalColors.yellow(this.toString()))
}

fun Any?.printAsInfo() {
    echo(terminalColors.blue(this.toString()))
}

fun Any.asGray() = terminalColors.gray.invoke(this.toString())
fun Any.asYellow() = terminalColors.yellow.invoke(this.toString())
fun Any.asMagenta() = terminalColors.magenta.invoke(this.toString())
fun Any.asCyan() = terminalColors.cyan.invoke(this.toString())
fun Any.asBlue() = terminalColors.blue.invoke(this.toString())
