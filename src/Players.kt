enum class Players(val white: String, val black: String) {
    PLAYER("First", "Second"),
    COLOR("white", "black"),
    PAWN(" W |", " B |");
    fun noPawn(isWhite: Boolean, input: String) {
        println(message = ("No ${if (isWhite) COLOR.white else COLOR.black} pawn at ${input.substring(0, 2)}"))
    }
    fun playerWins(isWhite: Boolean) {
        println("${ if (!isWhite) "White" else "Black" } Wins!")
    }
}
