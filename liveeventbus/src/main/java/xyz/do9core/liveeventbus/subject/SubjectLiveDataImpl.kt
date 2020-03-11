package xyz.do9core.liveeventbus.subject

// TODO: customize this live data
internal class SubjectLiveDataImpl<T : Any> : SubjectLiveData<T>() {

    override fun post(event: T) {
        TODO()
    }

    override fun postNow(event: T) {
        TODO()
    }
}