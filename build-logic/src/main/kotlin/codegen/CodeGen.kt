package codegen

import org.gradle.api.file.Directory

interface CodeGen {

    fun gen(dir: Directory)

}
