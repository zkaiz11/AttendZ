package com.example.attendz

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.Date
import com.facebook.shimmer.ShimmerFrameLayout


class Home : Fragment() {
    private val auth = Firebase.auth
    private val user = auth.currentUser
    val db = Firebase.firestore
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get layout
        val shimmerFrameLayout: ShimmerFrameLayout = view.findViewById(R.id.shimmerView)
        val parentLyt: LinearLayout = view.findViewById(R.id.parentLyt)
        parentLyt.visibility = View.GONE
        shimmerFrameLayout.startShimmerAnimation()

        //get views
        val displayName = view.findViewById<TextView>(R.id.userName)
        val profileImg = view.findViewById<de.hdodenhof.circleimageview.CircleImageView>(R.id.profileImg)
        displayName.text = user?.displayName ?: "user"
        Picasso.get().load(user?.photoUrl).into(profileImg)
        val activeEventName = view.findViewById<TextView>(R.id.activeEventName)
        val activeEventHost = view.findViewById<TextView>(R.id.hostName)
        val activeEventDate = view.findViewById<TextView>(R.id.eventDate)
        val activeEventAttendee = view.findViewById<TextView>(R.id.attendee)

        //get current event from database
        val ref = db.collection("events").document("mmtfYkcjU4Yl4yOl3KD1")
        ref.get().addOnSuccessListener {
                document ->
            if (document != null) {

                //get event name
                val name = document.data?.get("name")?.toString()

                //get event date
                val eventTimestamp = document.getTimestamp("created_at")
                val date: Date? = eventTimestamp?.toDate()
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val dateString: String = dateFormat.format(date)

                //get event host information
                val hostName = document.data?.get("host_name")?.toString()

                //get number of attendee
//                val attendee: List<Map<String, Any>>? = document.get("participants") as? List<Map<String, Any>>
                activeEventName.text = name
                activeEventDate.text = "Date: " + dateString
                activeEventHost.text = "Host: " + hostName
//                activeEventAttendee.text = "Attendees: " + (attendee?.size ?: "error")
                activeEventAttendee.text = "Attendees: 1"
            } else {
                Toast.makeText(activity, "No Data", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            it.message?.let { it1 -> Log.d("error", it1) }
            Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
        }


        handler.postDelayed({
            shimmerFrameLayout.stopShimmerAnimation()
            shimmerFrameLayout.visibility = View.GONE
            parentLyt.visibility = View.VISIBLE
        }, 1500)

    }
}