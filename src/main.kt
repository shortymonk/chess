fun main() {
    println("Pawns-only chess")
    val chess = Board()
    val white = Player.newPlayer()
    val black = Player.newPlayer()
    var input: String
    val regex = Regex("[a-h][1-8][a-h][1-8]")
    chess.refreshBoard()
    while (true) {
        println("${if (Player.isWhite) white.name else black.name}'s turn:")
        input = readLine()!!
        when {
            input.matches(regex) && Player.isWhite -> Player.Pawns.move(white, input, chess)
            input.matches(regex) && !Player.isWhite -> Player.Pawns.move(black, input, chess)
            input == "exit" -> {println("Bye!"); break}
            else -> println("Invalid Input")
        }
    }
}
