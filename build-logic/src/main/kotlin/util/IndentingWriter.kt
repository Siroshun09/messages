package util

import java.io.IOException
import java.io.Writer


class IndentingWriter(private val writer: Writer, private val indentSpaces: Int = 4) {
    private val LINE_SEPARATOR = System.getProperty("line.separator", "\n")
    private var indent = " ".repeat(indentSpaces)
    private var depth = 0

    fun increaseIndent() {
        depth++
    }

    fun decreaseIndent() {
        depth--
    }

    fun writeLine(line: String) {
        writeIndent()
        write(line)
        write(LINE_SEPARATOR)
    }

    fun writeEmptyLine() {
        write(LINE_SEPARATOR)
    }

    fun flush() {
        writer.flush()
    }

    private fun writeIndent() {
        if (depth == 0) {
            return
        }
        for (i in 1..depth) {
            write(indent)
        }
    }

    private fun write(str: String) {
        try {
            writer.write(str)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }
}