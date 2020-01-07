package com.example.managertransactio.Fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.managertransactio.MainActivity

import com.example.managertransactio.R
import com.example.managertransactio.data.Constants
import com.example.managertransactio.network.GetDataService
import com.example.managertransactio.network.RetrofitInstance
import com.example.managertransactio.util.Spref
import com.example.managervalueapplication.data.BaseModel
import com.google.firebase.database.*
import com.google.gson.JsonElement
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_total.*
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigDecimal
import java.util.*
import kotlin.collections.HashMap

/**
 * A simple [Fragment] subclass.
 */
class TotalFragment : Fragment() {

    val TAG = "total_fragment"
    var mapFirebase : HashMap<String, String> = HashMap()
    public lateinit var exchangeRateUSD : String
    public lateinit var investUSD : String
    public lateinit var imcomeUSD : String
    public lateinit var investVND : String
    public lateinit var imcomeVND : String
    public lateinit var interestVND : String
    public lateinit var interestUSD : String
    lateinit var tv_invest_usd : TextView
    lateinit var tv_imcome_usd : TextView
    lateinit var tv_invest_vnd : TextView
    lateinit var tv_imcome_vnd : TextView
    lateinit var tv_interest_usd : TextView
    lateinit var tv_interest_vnd : TextView



    public lateinit var database: DatabaseReference
    init {
        database = FirebaseDatabase.getInstance().reference

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_total, container, false)

        initView(view)
        getApiPrice()
        return view
    }

    fun initView(view : View){
        tv_invest_usd = view.findViewById<TextView>(R.id.tv_invest_usd)
        tv_imcome_usd = view.findViewById<TextView>(R.id.tv_imcome_usd)
        tv_invest_vnd = view.findViewById<TextView>(R.id.tv_invest_vnd)
        tv_imcome_vnd = view.findViewById<TextView>(R.id.tv_imcome_vnd)
        tv_interest_usd = view.findViewById<TextView>(R.id.tv_interest_usd)
        tv_interest_vnd = view.findViewById<TextView>(R.id.tv_interest_vnd)
    }

    fun setTextview(){
        tv_invest_usd.text = formatString(investUSD) + " USD"
        tv_imcome_usd.text = formatString(imcomeUSD) + " USD"
        tv_invest_vnd.text = formatString(investVND) + " VND"
        tv_imcome_vnd.text = formatString(imcomeVND) + " VND"
        tv_interest_usd.text = formatString(interestUSD) + " USD"
        tv_interest_vnd.text = formatString(interestVND) + " VND"
        Spref().setSharedPreferenceString(requireContext(),Constants().EXCHANGE, exchangeRateUSD)
        Spref().setSharedPreferenceString(requireContext(),Constants().IMCOME, imcomeUSD)
        Spref().setSharedPreferenceString(requireContext(),Constants().INVEST, investUSD)

    }



    fun readFireBase(){
        val postlistener = object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0 != null ){
                    for (data in p0.children){
                        mapFirebase.put(data.key.toString(), data.value.toString())
                    }
                    investUSD = mapFirebase.get("invest").toString()
                    imcomeUSD = mapFirebase.get("imcome").toString()
                    imcomeVND = BigDecimal(imcomeUSD.toDouble()*exchangeRateUSD.toDouble()).toString()
                    investVND = BigDecimal(investUSD.toDouble()*exchangeRateUSD.toDouble()).toString()
                    interestUSD = BigDecimal(imcomeUSD.toDouble() - investUSD.toDouble()).toString()
                    interestVND = BigDecimal(interestUSD.toDouble()*exchangeRateUSD.toDouble()).toString()
//                    investUSD = String.format("%.2f",mapFirebase.get("invest").toString().toDouble())
//                    imcomeUSD = String.format("%.2f",mapFirebase.get("imcome").toString().toDouble())
//                    imcomeVND = String.format("%.2f",(imcomeUSD.toDouble()*exchangeRateUSD.toDouble()))
//                    investVND = String.format("%.2f",(investUSD.toDouble()*exchangeRateUSD.toDouble()))
//                    interestUSD = String.format("%.2f",(imcomeUSD.toDouble() - investUSD.toDouble()))
//                    interestVND = String.format("%.2f",(interestUSD.toDouble()*exchangeRateUSD.toDouble()))
                    setTextview()

                }
            }

        }
        database.child("total").child("life").addValueEventListener(postlistener)
    }

    fun getApiPrice(){
//        val getDataService = RetrofitInstance().getRetrofitTempInstance()?.create(GetDataService ::class.java)
//        if (getDataService != null) {
//            getDataService.ChangPrice()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe({displayData(it)})
//        }

        val requestInterface = RetrofitInstance().getRetrofitTempInstance().create(GetDataService::class.java)
        requestInterface.ChangPrice()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({displayData(it)},
                {Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()})
    }

    private fun displayData(posts: Response<JsonElement>) {
        val data : JSONObject= JSONObject(posts.body().toString())
        val rates : JSONObject = JSONObject(data.get("rates").toString())
        exchangeRateUSD = rates.get("VND").toString()
        readFireBase()

    }
    fun formatString(text:String) : String{
        try {
            return text.substring(0, text.indexOf(".") + 3)
        }catch (e : Exception){
            return text
        }

    }



}