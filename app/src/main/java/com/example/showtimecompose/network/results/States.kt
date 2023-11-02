import com.example.showtimecompose.network.results.ShowError

sealed class ApiResult<T>(val data:T?=null, val error:ShowError?=null){
    class Success<T>(data: T):ApiResult<T>(data = data)
    class Error<T>(error: ShowError):ApiResult<T>(error = error)
    class Loading<T>:ApiResult<T>()
}
