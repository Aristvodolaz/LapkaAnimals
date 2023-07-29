package com.example.newanimals.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference

class AdsAnimalsAddFragment: Fragment() {

    companion object{
        fun newInstance(): AdsAnimalsAddFragment = AdsAnimalsAddFragment()
    }

//    private val permissions  = arrayOf(
//        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//        Manifest.permission.READ_EXTERNAL_STORAGE,
//        Manifest.permission.CAMERA
//    )

    private val permissionRequestCode = 123
    private val pickImageRequestCode = 321
    private val takePhotoRequestCode = 231

    private lateinit var storageRef: StorageReference
    private lateinit var photosRef: DatabaseReference

    private val requestPermissionlauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
        permissions->
        val allPermissionGranted = permissions.values.all { it }
        if(allPermissionGranted){

        }else{

        }
    }

    private val pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result-> if(result.resultCode==pickImageRequestCode){
            val imgUri = result.data?.data
        imgUri?.let {
            uploadImageToFirebase(it)
        }
    }
    }

    private val takePhotoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result -> if(result.resultCode == takePhotoRequestCode){
//            val imgFile = File(current)
    }
    }

    private fun uploadImageToFirebase(it: Uri) {

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                adsAddPreviewView()
            }
        }
    }

    @Composable
    fun adsAddView(){

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == 123){
            if(data!=null){
//                file
            }
        }
    }

    @Composable
    @Preview
    fun adsAddPreviewView(){
        adsAddView()
    }
}