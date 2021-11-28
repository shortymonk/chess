class Board {
    private val border = "  +---+---+---+---+---+---+---+---+"
    private val file = " abcdefgh\n"; private var output = ""
    private val board = Array(10) { Array<String>(10) { "   |" } }

    init {
        for (rank in board) {
            board[board.indexOf(rank)][0] = "${board.indexOf(rank)} |"
            board[0][board.indexOf(rank)] = " ${this.file[board.indexOf(rank)]}  "
            board[board.indexOf(rank)][board.lastIndex] = ""
            board[board.lastIndex][board.indexOf(rank)] = ""
        }
        board[0][0] = "   "
    }

    override fun toString(): String = output

    fun refreshBoard() {
        Player.whitePawns.forEach { board[it.second][it.first] = " W |" }
        Player.blackPawns.forEach { board[it.second][it.first] = " B |" }
        for (y in 1..8) {
            for (x in 1..8) if (!(Player.whitePawns + Player.blackPawns).contains(Pair(x, y))) board[y][x] = "   |"
        }
        output = "$border\n"
        for (i in 1..9) output += if (i != board.lastIndex) {
            "${board.reversed()[i].joinToString("")}\n${border}\n"
        } else "${board.reversed()[i].joinToString("")}\n"
        println(output)
    }
}