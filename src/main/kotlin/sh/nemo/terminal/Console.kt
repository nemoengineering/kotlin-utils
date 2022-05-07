package sh.nemo.terminal

import java.io.IOException

/**
 * An object that is used by commands and parameters to show text to the user and read input.
 */
interface Console {
    /**
     * Show the [prompt] to the user, and return a line of their response.
     *
     * This function will block until a line of input has been read.
     *
     * @param prompt The text to display to the user
     * @param hideInput If true, the user's input should not be echoed to the screen. If the current console
     *   does not support hidden input, this argument may be ignored. Currently, this argument is
     *   ignored on JS and Native platforms.
     * @return A line of user input, or null if an error occurred.
     */
    fun promptForLine(prompt: String, hideInput: Boolean): String?

    /**
     * Show some [text] to the user.
     *
     * @param text The text to display. May or may not contain a tailing newline.
     * @param error If true, the [text] is an error message, and should be printed in an alternate stream or
     *   format, if applicable.
     */
    fun print(text: String, error: Boolean)

    /**
     * The line separator to use. (Either "\n" or "\r\n")
     */
    val lineSeparator: String
}

class InteractiveConsole(private val console: java.io.Console) : Console {
    override fun promptForLine(prompt: String, hideInput: Boolean) = when {
        hideInput -> console.readPassword(prompt)?.let { String(it) }
        else -> console.readLine(prompt)
    }

    override fun print(text: String, error: Boolean) {
        if (error) {
            System.err
        } else {
            System.out
        }.print(text)
    }

    override val lineSeparator: String get() = System.lineSeparator()
}

class NonInteractiveConsole : Console {
    override fun promptForLine(prompt: String, hideInput: Boolean) = try {
        print(prompt, false)
        readLine() ?: throw RuntimeException("EOF")
    } catch (err: IOException) {
        throw err
    }

    override fun print(text: String, error: Boolean) {
        if (error) {
            System.err
        } else {
            System.out
        }.print(text)
    }

    override val lineSeparator: String get() = System.lineSeparator()
}

fun defaultConsole(): Console {
    return System.console()?.let { InteractiveConsole(it) }
        ?: NonInteractiveConsole()
}
