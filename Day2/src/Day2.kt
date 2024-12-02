import java.io.File
import java.util.*
import kotlin.math.abs

class Day2 {
    private val file = File("input.txt")
    private val fileReader = Scanner(file)
    private val SAFE_DISTANCE = 3
    var safeCounter = 0
        private set
    
    private fun checkIfAllLevelsAreSafe(line: String): Boolean {
        val lineReader = Scanner(line)
        var previousLevel = lineReader.nextInt()
        var isIncreasing = true
        var isDecreasing = true
        var isSafe = true
        while (lineReader.hasNext() && isSafe) {
            val nextLevel = lineReader.nextInt()
            if (nextLevel > previousLevel) {
                isDecreasing = false
            }
            else if (nextLevel < previousLevel) {
                isIncreasing = false
            }

            if (!isIncreasing && !isDecreasing
                || nextLevel == previousLevel
                || abs(nextLevel - previousLevel) > SAFE_DISTANCE) {
                isSafe = false
            }
            previousLevel = nextLevel
        }
        if (isSafe) {
            return true
        }
        else {
            return false
        }
    }
    
    private fun processLine(line: String) {
        if (checkIfAllLevelsAreSafe(line)) { 
            safeCounter++
        }
        else {
            var lastWhiteSpaceIndex = 0
            for (characterIndex in line.indices) {
                if (line[characterIndex] == ' ') {
                    val start = line.substring(0, lastWhiteSpaceIndex)
                    val end = line.substring(characterIndex)
                    val lineWithoutNumber = start + end
                    if (checkIfAllLevelsAreSafe(lineWithoutNumber)) {
                        safeCounter++
                        break
                    }
                    lastWhiteSpaceIndex = characterIndex
                }
                if (characterIndex == line.length - 1) {
                    if (checkIfAllLevelsAreSafe(line.slice(0..lastWhiteSpaceIndex))) {
                        safeCounter++
                    }
                }
            }
        }
    }
    
    fun processInput() {
        while (fileReader.hasNextLine()) {
            processLine(fileReader.nextLine())
        }
    }
}

fun main() {
    val day2 = Day2()
    day2.processInput()
    println(day2.safeCounter)
}
