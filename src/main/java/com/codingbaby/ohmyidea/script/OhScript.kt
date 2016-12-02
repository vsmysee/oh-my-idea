package com.codingbaby.ohmyidea.script


import com.intellij.openapi.util.io.FileUtil
import org.apache.commons.lang.StringUtils
import java.io.File
import java.io.IOException
import java.util.*
import java.util.regex.Pattern

/**
 * _ls|描述
 * List list = new ArrayList()
 *
 */
object OhScript {

    val OH_FILE = ".oh-my-idea"

    private val EOL_SPLIT_PATTERN = Pattern.compile("(\r\n|\n)")

    private var holder: LineHolder? = null

    private val codeGen = HashMap<String, String>()

    private fun putKey(key: String, value: String) {
        codeGen.put(key.trim { it <= ' ' }, value.trim { it <= ' ' })
    }


    fun getMapping(key: String): String? {
        return codeGen[key]
    }


    fun loadScriptFile() {

        parseTokens(loadContent())

        if (holder == null) {
            return
        }

        val codeKV = holder!!.codeKV
        for (kv in codeKV) {
            putKey(kv.key, kv.value)
        }
    }

    fun saveScript(text: String) {
        if (StringUtils.isNotBlank(text)) {
            val homeDirName = System.getProperty("user.home")
            if (homeDirName != null) {
                val file = File(homeDirName, OH_FILE)
                try {
                    FileUtil.writeToFile(file, text)
                    loadScriptFile()
                } catch (e: IOException) {
                }

            }
        }
    }

    fun loadContent(): String {
        val homeDirName = System.getProperty("user.home")
        if (homeDirName != null) {
            val file = File(homeDirName, OH_FILE)
            if (file.exists()) {
                try {
                    return file.readText(Charsets.UTF_8)
                } catch (e: IOException) {
                    return ""
                }
            }
        }
        return ""
    }

    val helpDesc: Map<String, String>
        get() = holder!!.descMap

    private fun parseTokens(content: String) {
        if (StringUtils.isBlank(content)) {
            return
        }
        val lines = EOL_SPLIT_PATTERN.split(content)
        holder = LineHolder(lines)
        holder!!.buildCodeQuick(holder!!.next())
    }

    private class LineHolder(private val lines: Array<String>) {
        private var begin = 0
        private val keys = ArrayList<String>()
        private val descriptions = ArrayList<String>()
        private val values = ArrayList<String>()

        val codeKV: List<CodeKV>
            get() {
                val list = ArrayList<CodeKV>()
                for (i in keys.indices) {
                    list.add(CodeKV(keys[i], values[i]))
                }
                return list
            }

        val descMap: Map<String, String>
            get() {
                val map = HashMap<String, String>()
                for (i in keys.indices) {
                    map.put(keys[i], descriptions[i])
                }
                return map
            }

        fun buildCodeQuick(line: String) {
            var line = line
            if (StringUtils.isNotBlank(line) && line.startsWith("_")) {

                val key = line.trim { it <= ' ' }.substring(1)
                val split = key.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                keys.add(split[0])
                if (split.size == 2) {
                    descriptions.add(split[1])
                } else {
                    descriptions.add("")
                }

                val sb = StringBuilder()
                line = next()

                while (!line.startsWith("_")) {
                    sb.append(line)
                    sb.append("\n")
                    if (done()) {
                        break
                    }
                    line = next()
                }

                values.add(sb.toString())
            } else {
                line = next()
            }

            if (!done()) {
                buildCodeQuick(line)
            }
        }


        operator fun next(): String {
            val line = lines[begin]
            begin++
            return line
        }

        fun done(): Boolean {
            return begin == lines.size
        }
    }

    private class CodeKV constructor(internal var key: String, internal var value: String)

}
