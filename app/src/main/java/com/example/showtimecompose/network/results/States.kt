import com.example.showtimecompose.network.results.ShowsListError

sealed class ApiResult<T>(val data:T?=null, val error:ShowsListError?=null){
    class Success<T>(data: T):ApiResult<T>(data = data)
    class Error<T>(error: ShowsListError):ApiResult<T>(error = error)
    class Loading<T>:ApiResult<T>()
}
