package com.saidov.news2022.modules.main.ui.vm

import androidx.lifecycle.MutableLiveData
import com.saidov.news2022.core.viewmodel.BaseViewModel
import com.saidov.news2022.modules.main.ui.model.NewsResponse
import com.saidov.news2022.repository.networkrepository.event.Event

/**
 * Created by MUHAMMADJON SAIDOV on 31,январь,2022
 * saidov.developer@gmail.com
 * http://muhammad.com/
 *
 */
class ActivityViewModel : BaseViewModel() {

    // Создаем лайвдату для нашего списка юзеров
    val simpleLiveData = MutableLiveData<Event<NewsResponse>>()

    // Получение юзеров. Обращаемся к функции  requestWithLiveData
    // из BaseViewModel передаем нашу лайвдату и говорим,
    // какой сетевой запрос нужно выполнить и с какими параметрами
    // В данном случае это api.getUsers
    // Теперь функция сама выполнит запрос и засетит нужные
    // данные в лайвдату
//    fun getNews() {
//        requestWithLiveData(simpleLiveData) {
//           // api.getBreakingNews()
//        }
//    }

    // Здесь аналогично, но вместо лайвдаты используем котлиновский колбек
    // UPD Полученный результат мы можем обработать здесь перед отправкой во вью
//    fun getUsersError(page: Int, callback: (data: Event<Users>) -> Unit) {
//        requestWithCallback({
//            api.getUsersError(
//                page = page
//            )
//        }) {
//            callback(it)
//        }
//    }
}