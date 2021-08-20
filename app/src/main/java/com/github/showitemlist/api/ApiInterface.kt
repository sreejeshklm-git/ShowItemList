package  com.github.showitemlist.api


import com.github.showitemlist.model.Item
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*


interface ApiInterface {

  @GET("repos")
   fun getRepositoryDetails(
    ): Observable<List<Item?>>?


}
