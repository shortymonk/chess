class Player(val color: String = "white", val name: String) {

    companion object {
        val whitePawns = mutableSetOf<Pair<Int, Int>>()
        val blackPawns = mutableSetOf<Pair<Int, Int>>()
        init { for (i in 1..8) { whitePawns += Pair(i, 2); blackPawns += Pair(i, 7) } }
        var isWhite: Boolean = true
        var chance: Pair<Int, Int> = Pair(0, 0)

        fun newPlayer(): Player {
            val color = if (isWhite) "white" else "black"
            println("${if (isWhite) "First" else "Second"} Player's name:")
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

    object Pawns {

        fun move(player: Player,input: String, chess: Board) {
            val pawnSet = if (isWhite) whitePawns else blackPawns
            val opponentSet = if (isWhite) blackPawns else whitePawns
            val take = Pair(convert(input[0]), input[1].toString().toInt())
            val put = Pair(convert(input[2]), input[3].toString().toInt())
            val makeStep = {
                pawnSet.remove(take); pawnSet += put
                chess.refreshBoard(); isWhite = !isWhite
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
                !pawnIsExist -> { println("No ${player.color} pawn at ${input.substring(0, 2)}") }

                checkStep && isFreeAhead && take.first == put.first -> {
                    if ((put.second - take.second) * direction == 2) chance = put else {
                        chance = Pair(0, 0)
                    }
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
//            println("direction: $direction; first step: $firstStep; checkStep: $checkStep; free ahead: $isFreeAhead; capture: $isCapture \n" +
//                    "Player ${player.color}: $whitePawns \n" +
//                    "Player ${player.color}: $blackPawns")
        }
    }
}