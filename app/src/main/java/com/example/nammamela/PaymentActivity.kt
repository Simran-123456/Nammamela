package com.example.nammamela

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val seatNo = intent.getStringExtra("seatNo") ?: "A1"
        val dramaName = intent.getStringExtra("dramaName") ?: "Nann Aase Nataka"
        val venue = intent.getStringExtra("venue") ?: "Shivamogga Town Hall"
        val address = intent.getStringExtra("address") ?: "Near Gandhi Square, Ward 5"
        val location = intent.getStringExtra("location") ?: "Shivamogga"
        val dateTime = intent.getStringExtra("dateTime") ?: "Tonight | 9:00 PM"
        val geoLocation = intent.getStringExtra("geoLocation") ?: "13.9299,75.5681"

        val tvPaymentDrama = findViewById<TextView>(R.id.tvPaymentDrama)
        val tvPaymentSeat = findViewById<TextView>(R.id.tvPaymentSeat)
        val etAmount = findViewById<EditText>(R.id.etAmount)
        val etTransactionId = findViewById<EditText>(R.id.etTransactionId)
        val btnVerify = findViewById<Button>(R.id.btnPaymentDone)
        val btnPayViaUPI = findViewById<Button>(R.id.btnPayViaUPI)
        val progressBar = findViewById<ProgressBar>(R.id.paymentProgress)

        tvPaymentDrama.text = "Drama: $dramaName"
        tvPaymentSeat.text = "Selected Seat: $seatNo"

        // Auto-open UPI apps
        openUPIPayment(dramaName, seatNo)

        btnPayViaUPI.setOnClickListener { openUPIPayment(dramaName, seatNo) }

        btnVerify.setOnClickListener {
            val amountStr = etAmount.text.toString().trim()
            val txnId = etTransactionId.text.toString().trim()

            if (amountStr.isEmpty() || (amountStr.toIntOrNull() ?: 0) < 100) {
                Toast.makeText(this, "Valid payment of ₹100 required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (txnId.length != 12) {
                Toast.makeText(this, "Enter 12-digit Ref Number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnVerify.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            
            Handler(Looper.getMainLooper()).postDelayed({
                // 1. GET LOGGED IN USER EMAIL
                val userPrefs = getSharedPreferences("users", Context.MODE_PRIVATE)
                val userEmail = userPrefs.getString("loggedInUser", "Guest") ?: "Guest"

                // 2. SAVE TO USER-SPECIFIC HISTORY (Private)
                val bookingPrefs = getSharedPreferences("booking", Context.MODE_PRIVATE)
                val historyKey = "history_$userEmail"
                val oldHistory = bookingPrefs.getString(historyKey, "") ?: ""
                val newEntry = "\n🎟️ $dramaName\nSeat: $seatNo | $dateTime\nRef: $txnId\n-------------------"
                bookingPrefs.edit().putString(historyKey, oldHistory + newEntry).apply()

                // 3. SAVE TO GLOBAL FIREBASE (So others see seat as booked)
                val globalSeatsDb = FirebaseDatabase.getInstance().getReference("booked_seats")
                globalSeatsDb.child(seatNo).setValue(userEmail)

                // 4. GO TO TICKET
                val intent = Intent(this, TicketActivity::class.java)
                intent.putExtra("seatNo", seatNo)
                intent.putExtra("dramaName", dramaName)
                intent.putExtra("venue", venue)
                intent.putExtra("address", address)
                intent.putExtra("location", location)
                intent.putExtra("dateTime", dateTime)
                intent.putExtra("geoLocation", geoLocation)
                startActivity(intent)
                finish()
            }, 2000)
        }
    }

    private fun openUPIPayment(dramaName: String, seatNo: String) {
        val upiUri = Uri.parse("upi://pay").buildUpon()
            .appendQueryParameter("pa", "8147838763@ybl") // Replace with actual UPI ID
            .appendQueryParameter("pn", "Namma Mela")
            .appendQueryParameter("tn", "Booking: $dramaName (Seat $seatNo)")
            .appendQueryParameter("am", "100.00")
            .appendQueryParameter("cu", "INR")
            .build()
        try {
            startActivityForResult(Intent.createChooser(Intent(Intent.ACTION_VIEW, upiUri), "Pay with"), 123)
        } catch (e: Exception) {
            Toast.makeText(this, "No UPI app found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            Toast.makeText(this, "Enter Ref Number to confirm booking", Toast.LENGTH_LONG).show()
        }
    }
}
