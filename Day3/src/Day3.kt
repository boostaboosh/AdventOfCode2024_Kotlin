import java.io.File
import java.util.*

class Day3 {
    private val file = File("testInput.txt")
    private val fileReader = Scanner(file)
    var total = 0
        private set
    private var mulEnabled = true
    val sequenceToMatchMul = "mul\\(\\d{1,3},\\d{1,3}\\)"
    val sequenceToMatchDo = "do\\(\\)"
    val sequenceToMatchDont = "don't\\(\\)"
    
    fun processInput() {
        while (fileReader.hasNextLine()) {
            val line = fileReader.nextLine()
            val lineWithoutDisabledMul = removeDisabledMul(line)
            val lineTotal = getMulInstructionsResultsSum(lineWithoutDisabledMul)
            this.total = this.total + lineTotal
        }
        fileReader.close()
    }
    
    private fun removeDisabledMul(line: String): String {
        var sequenceToMatch = sequenceToMatchDo
        if (this.mulEnabled) {
            sequenceToMatch = sequenceToMatchDont
        }
        val substrings = line.split(Regex(sequenceToMatch)).toMutableList().apply { this.removeAll { it == "" } }
        if (substrings.size == 1) {
            return if (this.mulEnabled) {
                substrings[0]
            } else {
                ""
            }
        }
        else {
            var lineWithoutDisabledParts = ""
            if (this.mulEnabled) {
                lineWithoutDisabledParts += substrings[0]
            }
            val restOfLine = substrings.subList(1, substrings.size).joinToString("")
            this.mulEnabled = !this.mulEnabled
            return lineWithoutDisabledParts + removeDisabledMul(restOfLine)
        }
    }
    
    private fun getMulInstructionsResultsSum(line: String): Int {
        var sumOfMultiplications = 0
        val lineReader = Scanner(line)
        var sequencesInLine = true
        while (sequencesInLine)
        {
            val mulInstruction = lineReader.findInLine(sequenceToMatchMul)
            if (mulInstruction == null) {
                sequencesInLine = false
            }
            else {
                val numberPart = mulInstruction.substring(4, mulInstruction.length - 1)
                val operands = numberPart.split(",")
                val firstOperand = operands[0].toInt()
                val secondOperand = operands[1].toInt()
                val result = firstOperand * secondOperand
                sumOfMultiplications = sumOfMultiplications + result
            }
        }
        lineReader.close()
        return sumOfMultiplications
    }
}

fun main() {
    val day3 = Day3()
    day3.processInput()
    print(day3.total)
}