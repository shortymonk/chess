fun main() {
    println("Pawns-only chess")
    val white = Player.newPlayer()
    val black = Player.newPlayer()
    var input: String
    val regex = Regex("[a-h][1-8][a-h][1-8]")
    Player.Board.refresh()
    while (true) {
        println("${if (Player.isWhite) white.name else black.name}'s turn:")
        input = readLine()!!
        when {
            input.matches(regex) -> Player.Pawns.move(input)
            input == "exit" -> {println("Bye!"); break}
            else -> println("Invalid Input")
        }
    }
}
