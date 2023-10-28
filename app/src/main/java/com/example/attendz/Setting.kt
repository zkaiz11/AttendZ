package com.example.attendz

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat.recreate
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.util.Locale


class Setting : Fragment() {
    private val auth = Firebase.auth
    private val user = auth.currentUser
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        val signOutButton = view.findViewById<Button>(R.id.logOutButton)
        val profileImg = view.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.profileImg)
        val displayName = view.findViewById<TextView>(R.id.displayName)
        displayName.text = user?.displayName
        Picasso.get().load(user?.photoUrl).into(profileImg)
        signOutButton.setOnClickListener {
            signOutAndStartSignInActivity()
        }
        return view
    }

    //navigate to khmer language
    fun onLanguageSwitchClick(view: View?) {
        // Change the app's locale to the second language
        val newLocale = Locale("km")
        Locale.setDefault(newLocale)

        // Update the configuration and resources for the new locale
        val config = Configuration()
        config.setLocale(newLocale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // Restart the activity to apply the new language
        recreate()
    }

    private fun recreate() {
        TODO("Not yet implemented")
    }


    private fun signOutAndStartSignInActivity() {
        Firebase.auth.signOut()
        val intent = Intent(activity, SignInActivity::class.java)
        startActivity(intent)
    }

}