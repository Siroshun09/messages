package codegen.arguments

import codegen.CodeGen
import org.gradle.api.file.Directory
import util.IndentingWriter

class ArgumentsGenerator(private val context: Context) : CodeGen {

    override fun gen(dir: Directory) {
        val packageDir = dir.dir(context.packageName.replace(".", "/"))
        packageDir.asFile.mkdirs()
        for (n in 1..context.numberOfArguments) {
            createInterface(packageDir, n)
            createImpl(packageDir, n)
        }
    }

    private fun createInterface(dir: Directory, num: Int) {
        val className = context.className.replace("#N", num.toString())
        val writer = createWriterForInterface(dir, "$className.java")
        writeHeader(writer)
        writer.writeLine("import java.util.Objects;")
        writer.writeLine("import java.util.function.Function;")
        if (num == 2) writer.writeLine("import java.util.function.BiFunction;")
        writer.writeEmptyLine()
        writeInterfaceJavadoc(writer, num)
        writer.writeLine("@FunctionalInterface")
        writer.writeLine("public interface $className${type(num)} ${extendsFunction(num)}{")
        writer.writeEmptyLine()
        writer.increaseIndent()
        writeStaticMethodJavadoc(writer,num, className)
        writer.writeLine(contract(num))
        writer.writeLine("static ${type(num)} @NotNull $className${type(num)} arg$num(${argWithKey(num)}) {")
        writer.increaseIndent()
        nullChecks(writer, num)
        writer.writeLine("return new ${className}Impl<>(key, ${parameters(num)});")
        writer.decreaseIndent()
        writer.writeLine("}")
        writer.writeEmptyLine()
        writeApplyMethodJavadoc(writer, num)
        if (num == 1 || num == 2) writer.writeLine("@Override")
        writer.writeLine(contract(num - 1))
        writer.writeLine("@NotNull ${context.messageBaseClassName} apply(${toBaseArg(num)});")
        writer.decreaseIndent()
        writer.writeEmptyLine()
        writer.writeLine("}")
        writer.flush()
    }

    private fun createImpl(dir: Directory, num: Int) {
        val interfaceName = context.className.replace("#N", num.toString())
        val className = interfaceName + "Impl"
        val writer = createWriterForInterface(dir, "$className.java")
        writeHeader(writer)
        writer.writeLine("import java.util.function.Function;")
        writer.writeEmptyLine()
        writer.writeLine("record $className${type(num)}(${argWithKey(num)}) implements $interfaceName${type(num)} {")
        writer.writeEmptyLine()
        writer.increaseIndent()
        writer.writeLine("@Override")
        writer.writeLine(contract(num - 1))
        writer.writeLine("public @NotNull ${context.messageBaseClassName} apply(${toBaseArg(num)}) {")
        writer.increaseIndent()
        if (num == 1) {
            writer.writeLine(context.returnStatement.replace("#T", "this.key, this.a1.apply(a1)"))
        } else {
            writer.writeLine(context.returnStatement.replace("#T", "this.key, ${returnValueParameters(num)}"))
        }
        writer.decreaseIndent()
        writer.writeLine("}")
        writer.decreaseIndent()
        writer.writeEmptyLine()
        writer.writeLine("}")
        writer.flush()
    }

    private fun writeHeader(writer: IndentingWriter) {
        writer.writeLine("package ${context.packageName};")
        writer.writeEmptyLine()
        context.additionalImports.iterator().forEachRemaining { writer.writeLine("import $it;") }
        writer.writeLine("import org.jetbrains.annotations.Contract;")
        writer.writeLine("import org.jetbrains.annotations.NotNull;")
        writer.writeEmptyLine()
    }

    private fun writeInterfaceJavadoc(writer: IndentingWriter, num: Int) {
        writer.writeLine("/**")
        writer.writeLine(" * An interface to create {@link ${context.messageBaseClassName}} from ${javadocArgument(num)}.")
        writer.writeLine(" * ")
        writeTypeVariablesDesc(writer, num)
        writer.writeLine(" */")
    }

    private fun writeStaticMethodJavadoc(writer: IndentingWriter, num: Int, className: String) {
        writer.writeLine("/**")
        writer.writeLine(" * Creates {@link $className} from the message key and the function${if (num == 1) "" else "s"}.")
        writer.writeLine(" * ")
        writer.writeLine(" * @param key the message key")
        writeFunctionParams(writer, num)
        writeTypeVariablesDesc(writer, num)
        writer.writeLine(" * @return a new {@link $className}")
        writer.writeLine(" */")
    }

    private fun writeApplyMethodJavadoc(writer: IndentingWriter, num: Int) {
        writer.writeLine("/**")
        writer.writeLine(" * Creates {@link ${context.messageBaseClassName}} from ${javadocArgument(num)}.")
        writer.writeLine(" * ")
        writeArgumentParams(writer, num)
        writer.writeLine(" * @return the {@link ${context.messageBaseClassName}}")
        writer.writeLine(" */")
    }

    private fun writeTypeVariablesDesc(writer: IndentingWriter, num: Int) {
        for (n in 1..num) {
            writer.writeLine(" * @param <A$n> the type of the ${ordinal(n)} argument")
        }
    }

    private fun writeFunctionParams(writer: IndentingWriter, num: Int) {
        for (n in 1..num) {
            writer.writeLine(" * @param a$n the {@link Function} for the ${ordinal(n)} argument")
        }
    }

    private fun writeArgumentParams(writer: IndentingWriter, num: Int) {
        for (n in 1..num) {
            writer.writeLine(" * @param a$n the ${ordinal(n)} argument")
        }
    }

    private fun ordinal(num: Int): String {
        return when(num) {
            1 -> "1st"
            2 -> "2nd"
            3 -> "3rd"
            else -> if (num.toString().endsWith("1")) "${num}st" else "${num}th"
        }
    }

    private fun javadocArgument(num: Int) : String{
        return if (num == 1) "an argument" else "the $num arguments"
    }

    private fun extendsFunction(num: Int): String {
        return when (num) {
            1 -> "extends Function<A1, ${context.messageBaseClassName}> "
            2 -> "extends BiFunction<A1, A2, ${context.messageBaseClassName}> "
            else -> ""
        }
    }

    private fun returnValueParameters(num: Int): String {
        var result = ""
        for (n in 1..num) {
            if (n != 1) {
                result += ", "
            }
            result += "this.a${n}.apply(a${n})"
        }
        return result
    }

    private fun contract(num: Int): String {
        var result = "@Contract(\""

        for (n in 0..num) {
            result += if (n == 0) "_" else ", _"
        }

        return "$result -> new\")"
    }

    private fun type(num: Int): String {
        var result = ""

        for (n in 1..num) {
            if (n != 1) {
                result += ", "
            }
            result += "A$n"
        }

        return "<$result>"
    }

    private fun arg(num: Int): String {
        var result = ""

        for (n in 1..num) {
            if (n != 1) {
                result += ", "
            }
            result += "@NotNull Function<? super A$n, ? extends ${context.replacementBaseClassName}> a$n"
        }

        return result
    }

    private fun toBaseArg(num: Int): String {
        var result = ""

        for (n in 1..num) {
            if (n != 1) {
                result += ", "
            }
            result += "A$n a$n"
        }

        return result
    }

    private fun argWithKey(num: Int): String {
        return "@NotNull String key, ${arg(num)}"
    }

    private fun nullChecks(writer: IndentingWriter, num: Int) {
        writer.writeLine("Objects.requireNonNull(key);")

        for (n in 1..num) {
            writer.writeLine("Objects.requireNonNull(a$n);")
        }
    }

    private fun parameters(num: Int): String {
        var result = ""

        for (n in 1..num) {
            if (n != 1) {
                result += ", "
            }
            result += "a$n"
        }

        return result
    }

    private fun createWriterForInterface(dir: Directory, filename: String): IndentingWriter {
        return IndentingWriter(dir.file(filename).asFile.writer(), context.indentSpaces)
    }

    data class Context(val packageName: String, val className: String,
                       val indentSpaces: Int = 4, val numberOfArguments: Int,
                       val messageBaseClassName: String,
                       val replacementBaseClassName: String,
                       val returnStatement: String,
                       val additionalImports: Sequence<String> = emptySequence()) {
        fun create(): ArgumentsGenerator {
            return ArgumentsGenerator(this)
        }
    }
}
