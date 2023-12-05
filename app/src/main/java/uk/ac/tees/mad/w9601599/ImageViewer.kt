package uk.ac.tees.mad.w9601599

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.ByteArrayOutputStream

class ImageViewer : AppCompatActivity() {

    private val CAMERA_REQUEST_CODE = 100
    private val IMAGE_CAPTURE_CODE = 101
    private lateinit var imagesRecyclerView: RecyclerView
    private lateinit var imagesAdapter: ImagesAdapter
    private var imagesList = mutableListOf<Bitmap>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer)

        imagesRecyclerView = findViewById(R.id.imagesRecyclerView)
        imagesRecyclerView.layoutManager = LinearLayoutManager(this)
        imagesAdapter = ImagesAdapter(imagesList)
        imagesRecyclerView.adapter = imagesAdapter

        fetchImagesFromDatabase()

        findViewById<Button>(R.id.openCameraButton).setOnClickListener {
            openCamera()
        }
    }

    private fun openCamera() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
        } else {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            Toast.makeText(this, "Camera permission is required", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_CAPTURE_CODE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val imageString = convertToBase64(imageBitmap)
            saveImageToDatabase(imageString)
        }
    }

    private fun convertToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun saveImageToDatabase(imageString: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("images")
        val imageId = databaseReference.push().key

        imageId?.let {
            databaseReference.child(it).setValue(imageString).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Image saved to database", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchImagesFromDatabase() {
        val databaseReference = FirebaseDatabase.getInstance().getReference("images")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                imagesList.clear()
                for (imageSnapshot in snapshot.children) {
                    val imageString = imageSnapshot.getValue(String::class.java)
                    imageString?.let {
                        val imageBitmap = decodeBase64(it)
                        imagesList.add(imageBitmap)
                    }
                }
                imagesAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error fetching images", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun decodeBase64(input: String): Bitmap {
        val decodedBytes = Base64.decode(input, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

}