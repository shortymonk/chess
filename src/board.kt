class Board {
    private val border = "  +---+---+---+---+---+---+---+---+"
    private val file = "0abcdefgh\n"; private var output = ""
    private val board = Array(10) { Array(10) { "   |" } }
    val whitePawns = mutableSetOf<Pair<Int, Int>>()
    val blackPawns = mutableSetOf<Pair<Int, Int>>()
    var turn = true; var chance = Pair(0, 0)
    init {
        for (i in 1..8) { whitePawns += Pair(i, 2); blackPawns += Pair(i, 7) }
        for (rank in board) {
            board[board.indexOf(rank)][0] = "${board.indexOf(rank)} |"
            board[0][board.indexOf(rank)] = " ${this.file[board.indexOf(rank)]}  "
            board[board.indexOf(rank)][board.lastIndex] = ""
            board[board.lastIndex][board.indexOf(rank)] = ""
        }
        board[0][0] = "   "
        refreshBoard()
    }

    override fun toString(): String = output
    fun refreshBoard() {
        whitePawns.forEach { board[it.second][it.first] = " W |" }
        blackPawns.forEach { board[it.second][it.first] = " B |" }
        for (y in 1..8) {
            for (x in 1..8) if (!(whitePawns + blackPawns).contains(Pair(x, y))) board[y][x] = "   |"
        }
        output = "$border\n"
        for (i in 1..9) output += if (i != board.lastIndex) {
            "${board.reversed()[i].joinToString("")}\n${border}\n"
        } else "${board.reversed()[i].joinToString("")}\n"
    }
}