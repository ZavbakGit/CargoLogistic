package `fun`.gladkikh.cargologistic.common.ui.dialog

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.*
import javax.inject.Inject


abstract class DialogMVVM<STATE, RESULT> : DialogFragment() {

    /**
     * Фабрику инжектим
     */
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    /**
     * Диалог dialogViewModel храним в основной ViewModel
     *
     * пример
     *
     *  private lateinit var viewModel: TestFragmentViewModel
     *
     *  в диалоге при создании диалога инициируем dialogViewModel
     *
     *  override fun onCreate(savedInstanceState: Bundle?) {
     *   super.onCreate(savedInstanceState)
     *   App.appComponent.inject(this)
     *   viewModel = viewModel {}
     *   dialogViewModel = viewModel.testDialog2Contract
     *  }
     *
     */
    protected var dialogViewModel: DialogViewModel<STATE, RESULT>? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //Подписываемся на состояние
        dialogViewModel?.getStateLiveData()?.observe(this, Observer {
            setStateState(it)
        }) ?: throw IllegalStateException("dialogViewModel cannot be null")
    }

    /**
     * Перересовываем состояние
     */
    open fun setStateState(state: STATE?) {

    }

    /**
     * Срабатывает при отмене
     */
    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        dialogViewModel?.onCancel()
    }

    /**
     * Срабатывает при закрытии
     */
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dialogViewModel?.onClosed()
    }

    fun sendResult(result: RESULT) {
        dialogViewModel?.onResult(result)
    }

    /**
     * Можем подписаться в модельке
     */
    inline fun <reified T : ViewModel> viewModel(body: T.() -> Unit): T {
        val vm = ViewModelProviders.of(requireActivity(), viewModelFactory)[T::class.java]
        vm.body()
        return vm
    }

    /**
     * STATE - состояние, RESULT - возвращаемый результат
     */
    abstract class DialogViewModel<STATE, RESULT> {
        private val stateData = MutableLiveData<STATE>()

        /**
         * Сдесь храним открыт, закрыт
         */
        var isOpen = false
            private set

        /**
         * Передаем состояние диалога
         */
        fun getStateLiveData(): LiveData<STATE> = stateData


        /**
         * Отправляем результат в основную
         */
        open fun onResult(result: RESULT) {

        }

        /**
         * При отмене диалога
         */
        open fun onCancel() {

        }

        /**
         * При закрытии диалога
         */
        open fun onClosed() {
            isOpen = false
        }

        /**
         * При открытии диалога
         */
        open fun onOpen() {
            isOpen = true
        }

        fun setState(state: STATE) {
            stateData.postValue(state)
        }
    }
}