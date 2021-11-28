fun main() {
    println("Pawns-only chess")
    val chess = Board()
    println("First Player's name:")
    val white = Pawns("white", readLine()!!, chess)
    println("Second Player's name:")
    val black = Pawns("black", readLine()!!, chess)
    var input: String
    val regex = Regex("[a-h][1-8][a-h][1-8]")
    println(chess)
    while (true) {
        println("${if (chess.turn) white.name else black.name}'s turn:")
        input = readLine()!!
        when {
            input.matches(regex) && chess.turn -> white.move(input)
            input.matches(regex) && !chess.turn -> black.move(input)
            input == "exit" -> {println("Bye!"); break}
            else -> println("Invalid Input")
        }

    }
}
