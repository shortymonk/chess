fun main() {
    println("Pawns-only chess")
    val white = Player.newPlayer()
    val black = Player.newPlayer()
    var input: String
    val regex = Regex("[a-h][1-8][a-h][1-8]")
    Player.Board.refresh()
    while (true) {
        if (Player.gameIsOver) {
            println("Bye!")
            break }
        println("${if (Player.isWhite) white.name else black.name}'s turn:")
        input = readLine()!!
        if (input == "exit") { println("Bye!"); break }
        if (input.matches(regex)) Player.Pawns.move(input) else println("Invalid Input")
    }
}
