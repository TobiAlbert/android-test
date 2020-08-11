package ng.riby.androidtest

import android.Manifest
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_main.*
import ng.riby.androidtest.utils.hasRequiredPermissions

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            locationResult?.lastLocation?.let {
                Log.i(TAG, "Latitude: ${it.latitude}; Longitude: ${it.longitude}\n")
            }
        }
    }

    companion object {
        private const val LOCATION_REQUEST_INTERVAL = 10000L
        private const val LOCATION_REQUEST_FASTEST_INTERVAL = 5000L
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        captureBtn.setOnClickListener { onCaptureButtonClicked() }
    }

    private fun onCaptureButtonClicked() {
        // ensure location permission have been granted
        hasRequiredPermissions(this::beginLocationCapture) {}
    }

    private fun beginLocationCapture() {
        val locationRequest = LocationRequest.create().apply {
            interval = LOCATION_REQUEST_INTERVAL
            fastestInterval = LOCATION_REQUEST_FASTEST_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { locationSettingsResponse ->
            captureBtn.setOnClickListener { stopLocationCapture() }
            captureBtn.text = getString(R.string.stop_location_capture_label)

            Log.i(TAG, "$locationSettingsResponse")

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            val hasLocationPermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

            if (hasLocationPermission) {
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
            }
        }

        task.addOnFailureListener { exception ->
            Log.e(TAG, "Error connecting to location settings client $exception")
            if (exception is ResolvableApiException){
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
//                    exception.startResolutionForResult(this@MainActivity,
//                            REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    private fun stopLocationCapture() {
        if (::fusedLocationProviderClient.isInitialized) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback).addOnCompleteListener {
                captureBtn.setOnClickListener { beginLocationCapture() }
                captureBtn.text = getString(R.string.start_location_capture_label)
            }
        }
    }
}