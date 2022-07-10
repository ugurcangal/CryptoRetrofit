package com.ugurcangal.cryptoretrofit.view

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AndroidException
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ugurcangal.cryptoretrofit.R
import com.ugurcangal.cryptoretrofit.adapter.CryptoRecyclerAdapter
import com.ugurcangal.cryptoretrofit.databinding.ActivityMainBinding
import com.ugurcangal.cryptoretrofit.model.CryptoModel
import com.ugurcangal.cryptoretrofit.service.CryptoAPI
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), CryptoRecyclerAdapter.Listener {
    private lateinit var binding: ActivityMainBinding

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private var cryptoRecyclerAdapter: CryptoRecyclerAdapter? = null
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        compositeDisposable = CompositeDisposable()

        //RecyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        loadData()
    }

    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)

        compositeDisposable?.add(
            retrofit.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        )

//        val call = service.getData()
//
//        call.enqueue(object : Callback<List<CryptoModel>> {
//            override fun onResponse(
//                call: Call<List<CryptoModel>>,
//                response: Response<List<CryptoModel>>
//            ) {
//                if (response.isSuccessful) {
//                    response.body()?.let {
//                        cryptoModels = ArrayList(it)
//
//                        cryptoModels?.let {
//                            cryptoRecyclerAdapter = CryptoRecyclerAdapter(it, this@MainActivity)
//                            binding.recyclerView.adapter = cryptoRecyclerAdapter
//                        }
//
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
//                t.printStackTrace()
//            }
//
//        })
    }

    private fun handleResponse(cryptoList: List<CryptoModel>) {
        cryptoModels = ArrayList(cryptoList)
        cryptoModels?.let {
            cryptoRecyclerAdapter = CryptoRecyclerAdapter(it, this@MainActivity)
            binding.recyclerView.adapter = cryptoRecyclerAdapter
        }
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this, "Clicked : ${cryptoModel.currency}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}