class Player(val color: String = "white", val name: String) {

    companion object {
        val whitePawns = mutableSetOf<Pair<Int, Int>>()
        val blackPawns = mutableSetOf<Pair<Int, Int>>()
        val whiteSteps = mutableSetOf<Pair<Int, Int>>()
        val blackSteps = mutableSetOf<Pair<Int, Int>>()
        init {
            for (i in 1..8) {
                whitePawns += Pair(i, 2); blackPawns += Pair(i, 7)
                whiteSteps += Pair(i, 3); blackSteps += Pair(i, 6)
            }
        }
        var isWhite: Boolean = true
        var chance: Pair<Int, Int> = Pair(0, 0)
        var gameIsOver = false

        fun newPlayer(): Player {
            val color = if (isWhite) Players.COLOR.white else Players.COLOR.black
            println("${if (isWhite) Players.PLAYER.white else Players.PLAYER.black} Player's name:")
            val name = readLine()!!
            isWhite = !isWhite
            return Player(color, name)
        }

        fun convert(x: Char): Int {
            return when (x) {
                'a' -> 1; 'b' -> 2; 'c' -> 3; 'd' -> 4
                'e' -> 5; 'f' -> 6; 'g' -> 7; else -> 8
            }
        }
    }

    object Board {
        fun refresh() {
            val border = "  +---+---+---+---+---+---+---+---+"
            val file = " abcdefgh\n"
            val board = Array(10) { Array(10) { "   |" } }
            for (rank in board) {
                board[board.indexOf(rank)][0] = "${board.indexOf(rank)} |"
                board[0][board.indexOf(rank)] = " ${file[board.indexOf(rank)]}  "
                board[board.indexOf(rank)][board.lastIndex] = ""
                board[board.lastIndex][board.indexOf(rank)] = ""
            }
            board[0][0] = "   "
            whitePawns.forEach { board[it.second][it.first] = Players.PAWN.white }
            blackPawns.forEach { board[it.second][it.first] = Players.PAWN.black }
            var output = "$border\n"
            for (i in 1..9) output += if (i != board.lastIndex) {
                "${board.reversed()[i].joinToString("")}\n${border}\n"
            } else "${board.reversed()[i].joinToString("")}\n"
            println(output)
        }
    }

    object Pawns {
        fun move(input: String) {
            val player = Players.COLOR
            val startPosition = if (isWhite) 2 else 7
            val pawnSet = if (isWhite) whitePawns else blackPawns
            val opponentSet = if (isWhite) blackPawns else whitePawns
            val opponentPossibleSteps = if (!isWhite) whiteSteps else blackSteps
            val take = Pair(convert(input[0]), input[1].toString().toInt())
            val put = Pair(convert(input[2]), input[3].toString().toInt())
            val makeStep = {
                pawnSet.remove(take); pawnSet += put; opponentPossibleSteps.remove(put)
                Board.refresh(); isWhite = !isWhite
            }
            val pawnIsExist = pawnSet.contains(take)
            val direction = if (isWhite) 1 else -1
            val firstStep = if (take.second == if (isWhite) 2 else 7) 2 else 1
            val checkStep = (put.second - take.second) * direction in 1..firstStep && put != take &&
                    put.first in (take.first - 1)..(take.first + 1)
            val isFreeAhead = !(pawnSet + opponentSet).contains(put)
            val isCapture = opponentSet.contains(put) && (put.second - take.second) * direction == 1 && checkStep
            val enPassant = chance == Pair(put.first, put.second - 1 * direction) && opponentSet.contains(chance)

            when {
                !pawnIsExist -> player.noPawn(isWhite, input)

                checkStep && isFreeAhead && take.first == put.first -> {
                    if ((put.second - take.second) * direction == 2) {
                        chance = put
                        opponentPossibleSteps.add(chance)
                    } else chance = Pair(0, 0)
                    makeStep.invoke()
                }

                isCapture && checkStep && opponentSet.contains(put) && take.first != put.first ->  {
                    opponentSet.remove(put); makeStep.invoke(); chance = Pair(0, 0)
                }

                enPassant && checkStep -> {
                    opponentSet.remove(chance); makeStep.invoke(); chance = Pair(0, 0)
                }

                else -> println("Invalid Input")
            }

            opponentPossibleSteps.clear()
            for (pawn in opponentSet){
                val possibleStep = listOf(Pair(pawn.first,pawn.second - 1 * direction),
                    Pair(pawn.first - 1, pawn.second - 1 * direction),
                    Pair(pawn.first + 1, pawn.second - 1 * direction)
                )
                if (possibleStep[0] !in pawnSet) opponentPossibleSteps.add(possibleStep[0])
                if (possibleStep[1] in pawnSet) opponentPossibleSteps.add(possibleStep[1])
                if (possibleStep[2] in pawnSet) opponentPossibleSteps.add(possibleStep[2])
            }

            when {
                opponentSet.isEmpty() -> { player.playerWins(isWhite); gameIsOver = true }
                opponentPossibleSteps.isEmpty() -> { println("Stalemate!"); gameIsOver = true }
                else -> {
                    for (pawn in pawnSet) {
                        if (pawn.second == startPosition + 6 * direction) {
                            player.playerWins(isWhite); gameIsOver = true; break
                        }
                    }
                }
            }
        }
    }
}
