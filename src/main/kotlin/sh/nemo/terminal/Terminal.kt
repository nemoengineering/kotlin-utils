package sh.nemo.terminal

object Terminal {
    /**
     * Print the [message] to the screen.
     *
     * This is similar to [print] or [println], but converts newlines to the system line separator.
     *
     * @param message The message to print.
     * @param trailingNewline If true, behave like [println], otherwise behave like [print]
     * @param err If true, print to stderr instead of stdout
     * @param console The console to echo to
     * @param lineSeparator The line separator to use, defaults to the [console]'s `lineSeparator`
     */
    fun echo(
        message: Any?,
        trailingNewline: Boolean = true,
        err: Boolean = false,
        console: Console = defaultConsole(),
        lineSeparator: String = console.lineSeparator
    ) {
        val text = message?.toString()?.replace(Regex("\r?\n"), lineSeparator) ?: "null"
        console.print(if (trailingNewline) text + lineSeparator else text, err)
    }
}
