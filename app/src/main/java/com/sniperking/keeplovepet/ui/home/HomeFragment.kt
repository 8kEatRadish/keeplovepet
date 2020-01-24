package com.sniperking.keeplovepet.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sniperking.libnavannotation.FragmentDestination
import com.sniperking.keeplovepet.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@FragmentDestination(pageUrl = "main/tabs/home", asStarter = true)
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private var n = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })
        root.findViewById<Button>(R.id.button).setOnClickListener {
            MainScope().launch {
                var ls = withContext(Dispatchers.Default){
                    1
                }
                n += ls
                button.text = (n+ls).toString()
                Log.d("suihw","ls = $ls")
            }
        }
        return root
    }
}