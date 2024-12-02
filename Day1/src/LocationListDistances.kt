import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

class LocationListDistances {
    private val file: File = File("input.txt")
    private val inputFileReader = Scanner(file)
    private val locationIdListOne = ArrayList<Int>()
    private val locationIdListTwo = ArrayList<Int>()
    
    private fun readFile() {
        while (inputFileReader.hasNextLine()){
            val line = inputFileReader.nextLine()
            val lineReader = Scanner(line)
            locationIdListOne.add(lineReader.nextInt())
            locationIdListTwo.add(lineReader.nextInt())
        }
    }
    
    private fun computeDistances(): ArrayList<Int> {
        locationIdListOne.sort()
        locationIdListTwo.sort()
        val distances = ArrayList<Int>()
        for (index in 0..<locationIdListOne.size) {
            distances.add(abs(locationIdListOne[index] - locationIdListTwo[index]))
        }
        return distances
    }
    
    fun getListDistances(): Int {
        readFile()
        var sum = 0
        for (distance in computeDistances()){
            sum += distance
        }
        return sum
    }
    
    fun getSimilarityScore(): Int {
        readFile()
        var score = 0
        val counts: MutableMap<Int, Int> = mutableMapOf()
        for (number in locationIdListOne){
            var occurrences = 0
            var duplicate = false
            for (previousNumber in counts) {
                if (number == previousNumber.key)
                {
                    occurrences = previousNumber.value
                    duplicate = true
                }
            }
            if (!duplicate) {
                for (newNumber in locationIdListTwo){
                    if (number == newNumber){
                        occurrences++
                    }
                }
                counts[number] = occurrences
            }
            score = score + number * occurrences
        }
        return score
    }
}

fun main() {
    val distance = LocationListDistances().getListDistances()
    println(distance)
    val score = LocationListDistances().getSimilarityScore()
    println(score)
}