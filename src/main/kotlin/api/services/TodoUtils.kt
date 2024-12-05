package api.services

import api.dto.TodosRequest
import utils.stringUtils
import kotlin.random.Random
import kotlin.random.nextULong

object TodoUtils {

//        todo Либо оставить как есть, тогда просто будут рандомные айдишники браться
//         либо использовать реализацию через атомик лонг и инкремент чтобы значения всегда были уникальными,
//         но все равно не будет гарантии что при запуске в сервисе не будте записей
//    private val id by lazy { AtomicLong() }
//    private val todoId get() = id.getAndIncrement()

    fun todoRequestData() = TodosRequest(
//        id = todoId.toULong(),
        id = Random.nextULong(),
        text = stringUtils.nextAlphabetic(100),
        completed = false
    )
}
