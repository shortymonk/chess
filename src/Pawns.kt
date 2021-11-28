class Pawns(color: String, name: String, chess: Board) : Player(color = color,name = name, chess = chess) {

    fun move(input: String) {
        val take = Pair(convert(input[0]), input[1].toString().toInt())
        val put = Pair(convert(input[2]), input[3].toString().toInt())
        val makeStep = {
            pawnSet.remove(take); pawnSet += put
            chess.turn = !chess.turn; chess.refreshBoard(); println(chess)
        }
        val pawnIsExist = pawnSet.contains(take)
        val direction = if (chess.turn) 1 else -1
        val firstStep = if (take.second == initPosition) 2 else 1
        val checkStep = (put.second - take.second) * direction in 1..firstStep && put != take &&
                put.first in (take.first - 1)..(take.first + 1)
        val isFreeAhead = !(pawnSet + opponentSet).contains(put)
        val isCapture = opponentSet.contains(put) && (put.second - take.second) * direction == 1 && checkStep
        val enPassant = chess.chance == Pair(put.first, put.second - 1 * direction) && opponentSet.contains(chess.chance)
        when {
            !pawnIsExist -> { println("No $color pawn at ${input.substring(0, 2)}") }

            checkStep && isFreeAhead && take.first == put.first -> {
                if ((put.second - take.second) * direction == 2) chess.chance = put else {
                    chess.chance = Pair(0, 0)
                }
                makeStep.invoke()
            }

            isCapture && checkStep && opponentSet.contains(put) && take.first != put.first ->  {
                opponentSet.remove(put); makeStep.invoke(); chess.chance = Pair(0, 0)
            }

            enPassant && checkStep -> {
                opponentSet.remove(chess.chance)
                makeStep.invoke(); chess.chance = Pair(0, 0)
            }

            else -> println("Invalid Input")
        }
    }
}