package com.example.attendz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.example.attendz.databinding.FragmentHomeBinding
import com.squareup.picasso.Picasso

class Home : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val displayName = view.findViewById<TextView>(R.id.userName)
        val profileImg = view.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.profileImg)
        setFragmentResultListener("user") { requestKey, bundle ->
            displayName.text = bundle.getString("name");
            Picasso.get().load(bundle.getString("imgUrl")).into(profileImg)
        }

        return view
    }
}