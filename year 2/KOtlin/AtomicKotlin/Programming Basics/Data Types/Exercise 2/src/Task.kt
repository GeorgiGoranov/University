// DataTypes/Task2.kt
package dataTypesExercise2

fun main() {
    val int: Int = 10
    val double: Double = 1.1
    val boolean: Boolean = false
    val string: String = "abc"
    val character: Char = 'a'


    val result: String = string + int;
    val result1: String = string + character;
    val result2: String = string + double;
    val result3: String = string + boolean;

//    val result4: Double = double + int;
//    val result5: Double = double + character;
//    val result6: String = string + boolean;


    println(
        "The type that can be combined " +
                "with every other type using '+':"
    )
    println("String")


}