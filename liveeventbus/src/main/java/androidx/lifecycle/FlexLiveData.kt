package androidx.lifecycle

// TODO: customize this live data
// 调整这个LiveData的行为，来帮助下层LiveData实现粘性和非粘性Subject
open class FlexLiveData<T : Any> : LiveData<T>()