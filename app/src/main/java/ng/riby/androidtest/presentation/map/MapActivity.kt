package ng.riby.androidtest.presentation.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CustomCap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.android.synthetic.main.activity_map.*
import ng.riby.androidtest.R
import org.koin.androidx.viewmodel.ext.android.viewModel


class MapActivity : AppCompatActivity() , OnMapReadyCallback {

    private val viewmodel: MapViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onBackPressed() {}

    override fun onMapReady(googleMap: GoogleMap) {
        // start observing location live data when map is ready
        observeViewModel(googleMap)
        doneBtn.setOnClickListener {
            viewmodel.deleteNotes()
            finish()
        }
    }

    private fun observeViewModel(googleMap: GoogleMap) {
        viewmodel.getLocations().observe(this, Observer {
            it?.let {

                it.ifEmpty { return@Observer }

                val startCap = getBitmapFromVectorDrawable(R.drawable.ic_start_cap)
                val endCap = getBitmapFromVectorDrawable(R.drawable.ic_end_cap)

                val polylineOptions =
                        PolylineOptions().clickable(true)
                                .width(15F)
                                .color(Color.BLACK)
                                .addAll(it)
                                .geodesic(true)
                                .startCap(CustomCap(BitmapDescriptorFactory.fromBitmap(startCap)))
                                .endCap(CustomCap(BitmapDescriptorFactory.fromBitmap(endCap)))

                googleMap.addPolyline(polylineOptions)

                val boundsBuilder = LatLngBounds.Builder()
                boundsBuilder.include(it.first())
                boundsBuilder.include(it.last())

                googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 0))
            }
        })
    }

    private fun getBitmapFromVectorDrawable(drawableId: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(this, drawableId)
        val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}

