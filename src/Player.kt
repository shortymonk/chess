open class Player(val color: String = "white", val name: String,val chess: Board) {
    val pawnSet = if (color == "white") chess.whitePawns else chess.blackPawns
    val opponentSet = if (color == "white") chess.blackPawns else chess.whitePawns
    val initPosition = if (color == "white") 2 else 7
    fun convert(x: Char): Int {
        return when (x) {
            'a' -> 1; 'b' -> 2; 'c' -> 3; 'd' -> 4
            'e' -> 5; 'f' -> 6; 'g' -> 7; else -> 8
        }
    }
}