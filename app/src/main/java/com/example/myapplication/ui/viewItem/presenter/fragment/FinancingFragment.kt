package com.example.myapplication.ui.viewItem.presenter.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R

class FinancingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_financing, container, false)
    }

    companion object {
        fun newInstance(): FinancingFragment {
            val fragment = FinancingFragment()
            return fragment
        }
    }
}