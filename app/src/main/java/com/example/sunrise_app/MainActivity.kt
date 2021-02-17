package com.example.sunrise_app

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    protected fun GetSunset(view: View)
    {
       val city: String = edittextcity.text.toString()
        val url="https://----------------.com"
        //we are sending only one instance
        //1st string will be our url
        asynctask().execute(url)      //will directly fire up the doInBackground
    }

    inner class asynctask:AsyncTask<String,String,String>()
    {
        override fun onPreExecute() {
            //
            super.onPreExecute()
        }
        override fun doInBackground(vararg p0: String?): String {
            try {
                //1st instance is url
                val url=URL(p0[0])

                val urlConnect=url.openConnection() as HttpURLConnection

                //waiting for 4s , if it doesn't come under this time then fire up catch block
                urlConnect.connectTimeout=4000

                var ipstream=StreamtoString(urlConnect.inputStream)

                //will call onProgress
                publishProgress(ipstream)
            }catch (ex:Exception)
            {
            }

            return " "
        }

        override fun onProgressUpdate(vararg values: String?) {
            // values will be the ipstream received from doinbg

            var json=JSONObject(values[0]!!)
            var query=json.getJSONObject("sunrise")

            textView.text=query.toString()
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
        }
    }

    fun StreamtoString(stream:InputStream):String{
        val bufferereader=BufferedReader(InputStreamReader(stream))
        var line:String
        var allString:String=" "

        do{
            line=bufferereader.readLine()
            if(line!=null)
                allString+=line
        }while (line!=null)
        stream.close()

        return allString
    }
}