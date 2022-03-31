package com.example.easycarpoolapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.core.view.GravityCompat
import com.example.easycarpoolapp.auth.AuthActivity
import com.example.easycarpoolapp.fragment.LoginDialogFragment
import com.example.easycarpoolapp.fragment.calendar.CalendarHomeFragment
import com.example.easycarpoolapp.fragment.chat.ChatFragment
import com.example.easycarpoolapp.fragment.chat.ChatHomeFragment
import com.example.easycarpoolapp.fragment.chat.dto.ChatRoomDto
import com.example.easycarpoolapp.fragment.home.HomeFragment
import com.example.easycarpoolapp.fragment.location.LocationHomeFragment
import com.example.easycarpoolapp.fragment.post.*
import com.example.easycarpoolapp.fragment.post.dto.PostDto
import com.example.easycarpoolapp.navigation.NavigationViewManager
import com.example.easycarpoolapp.navigation.car.RegisterCarFragment
import com.example.easycarpoolapp.navigation.car.RegisterCarImageFragment
import android.content.pm.PackageManager

import android.content.pm.PackageInfo
import android.util.Base64
import android.util.Log
import com.example.easycarpoolapp.databinding.ActivityMainBinding
import com.example.easycarpoolapp.navigation.profile.EditProfileFragment
import com.example.easycarpoolapp.navigation.profile.ProfileHomeFragment
import com.example.easycarpoolapp.navigation.profile.report.AccuseUserFragment
import com.example.easycarpoolapp.navigation.profile.report.ReportAdminFragment
import com.example.easycarpoolapp.navigation.profile.report.ReportHomeFragment
import com.example.easycarpoolapp.navigation.progress.ProgressDetailFragment
import com.example.easycarpoolapp.navigation.progress.ProgressHomeFragment
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity(), NavigationViewManager.Callback, LoginDialogFragment.Callbacks, PostHomeFragment.CallBacks,
RegisterCarDialogFragment.Callbacks, RegisterCarFragment.CallBacks, PostPassengerDetailFragment.Callbacks, ChatHomeFragment.Callbacks,
    PostDriverDetailFragment.Callbacks , ProfileHomeFragment.Callbacks, ProgressHomeFragment.Callbacks, ReportHomeFragment.Callbacks{

    private lateinit var binding : ActivityMainBinding
    private lateinit var actionBar : ActionBar
    private lateinit var navigationViewManager: NavigationViewManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setActionBar()
        navigationViewManager = NavigationViewManager(this, binding)
        setBottomNav()

        if(supportFragmentManager.findFragmentById(R.id.fragment_container)==null){
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, PostHomeFragment.getInstance()).commit()
        }
    }//onCreate

    override fun onResume() {
        super.onResume()

        navigationViewManager.setNavView()  //navigation 뷰 setup
        getHashKey()

        if (LocalUserData.getNickname()!=null){
            //Toast.makeText(applicationContext, "안녕하세요. "+LocalUserData.getNickname()+"님", Toast.LENGTH_SHORT).show()
        }

    }//onResume


    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }


    private fun setBottomNav(){

        binding.appBarMain.mainContentLayout.bottomNavigationView.setOnItemSelectedListener{

            when(it.itemId){
                R.id.activity_main_bottom_nav_calendar->{
                    if(LocalUserData.getEmail()!=null){
                        val fragment = CalendarHomeFragment.getInstance()
                        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
                    }else{
                        LoginDialogFragment().show(supportFragmentManager, "LoginDialog")
                    }

                    true
                }

                R.id.activity_main_bottom_nav_location->{
                    if(LocalUserData.getEmail()!=null){
                        //지도 프래그먼트 띄우기
                        val fragment = LocationHomeFragment.getInstance()
                        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
                    }else{
                        LoginDialogFragment().show(supportFragmentManager, "LoginDialog")
                    }

                    true
                }

                R.id.activity_main_bottom_nav_posts->{
                    if(LocalUserData.getEmail()!=null){
                        val fragment = PostHomeFragment.getInstance()
                        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
                    }else{
                        LoginDialogFragment().show(supportFragmentManager, "LoginDialog")
                    }
                    true
                }
                R.id.activity_main_bottom_nav_chat->{
                    if(LocalUserData.getEmail()!=null){
                        val fragment = ChatHomeFragment.getInstance()
                        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
                    }else{
                        LoginDialogFragment().show(supportFragmentManager, "LoginDialog")
                    }
                    true
                }

            }
            true
        }
    }

    private fun setActionBar(){
        setSupportActionBar(binding.appBarMain.toolbar)
        actionBar = supportActionBar!!
        actionBar.setDisplayShowCustomEnabled(false)    //기존의 title지우기
        actionBar.setDisplayHomeAsUpEnabled(true)   //뒤로가기 버튼이 생기게 하기 위해 True설정
        actionBar.setHomeAsUpIndicator(R.drawable.icon_menu_delete)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home ->{   //home키 - toolbar에 추가된 외쪽 상단의 버튼을 의미 따로 id를 지정하지 않음
                binding.drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteTokenSharedPreference(){
        val sharedPreference = this.getSharedPreferences("UserInfo", MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("token", "")
        //commit 의 경우 동기적으로 수행 되기에 write를 수행하는 크기가 크다면 UI coroutine 등을 통해 비 동기적으로 수행할것을 권장
        editor.commit()
    }   //logout 수행시 sharedPreference에 저장되어있는 토큰값 지우기

    override fun onLogoutSelected() {
        LocalUserData.logout()
        deleteTokenSharedPreference()
    }   //NavigationViewManager 에서 로그아웃 시도시 로그아웃 수행


    override fun onLoginSelected() {
        startActivity(Intent(this, AuthActivity::class.java))
    }//NavigationViewManager 에서 로그인 시도


    override fun onProfileSelected() {

        if(LocalUserData.getEmail()!=null){
            val fragment = ProfileHomeFragment.getInstance()
            supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragment_container, fragment).commit()
            binding.drawerLayout.close()    //navigation drawer 닫기
        }else{
            LoginDialogFragment().show(supportFragmentManager, "LoginDialog")
        }

    }//NavigationViewManager 에서 프로필 보기 시도   -> ProfileHomeFragment()

    override fun onProgressSelected() {
        if(LocalUserData.getEmail() !=null){
            val fragment = ProgressHomeFragment.getInstance()
            supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragment_container, fragment).commit()
            binding.drawerLayout.close()    //navigation drawer 닫기
        }else{
            LoginDialogFragment().show(supportFragmentManager, "LoginDialog")
        }

    } // NavigationViewManager Callback   -> 진행현황 보이기


    override fun onAddPassengerSelected() {
        val fragment = PostPassengerFormFragment.getInstance()
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
            .addToBackStack(null).replace(R.id.fragment_container, fragment).commit()
    }

    override fun onAddDriverSelected() {
        val fragment = PostDriverFormFragment.getInstance()
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
            .addToBackStack(null).replace(R.id.fragment_container, fragment).commit()
    }


    // 태워주세요 클릭시 이동
    override fun onPassengerPostSelected(item: PostDto) {
        val fragment = PostPassengerDetailFragment.getInstance(item)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    // 타세요 클릭시 이동 -> PostDriverDetailFragment생성
    override fun onDriverPostSelected(item: PostDto) {
        val fragment = PostDriverDetailFragment.getInstance(item)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }


    override fun onConfirmSelectedFromRegisterCar() {
        val fragment = RegisterCarFragment.getInstance()
        supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragment_container, fragment).commit()
    }

    override fun onConfirmSelectedFromLoginDialog() {
        startActivity(Intent(this, AuthActivity::class.java))
    }

    override fun onNextSelectedFromRegisterCar(
        carNumber: String,
        manufacturer: String,
        model: String
    ) {
        val fragment = RegisterCarImageFragment.getInstance(
            carNumber,
            manufacturer,
            model)
        supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragment_container, fragment).commit()
    }

    //채팅방 생성 -> 채팅방으로 이동
    override fun onSendMesageSelected(dto : ChatRoomDto) {
        val fragment = ChatFragment.getInstance(dto)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
            .addToBackStack(null)
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onChatRoomSelected(dto: ChatRoomDto) {
        val fragment = ChatFragment.getInstance(dto)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
            .addToBackStack(null).replace(R.id.fragment_container, fragment).commit()
    }

    override fun onEditProfileSelected() {
        val fragment = EditProfileFragment.getInstance()
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
            .addToBackStack(null).replace(R.id.fragment_container, fragment).commit()
    }   //ProfileHomeFragment Callback method -> 프로필 편집창으로 이동

    //신고창으로 이동
    override fun onReportUserSelected() {
        val fragment = ReportHomeFragment.getInstance()
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
            .addToBackStack(null).replace(R.id.fragment_container, fragment).commit()
    }   //ProfileHomeFragment Callback method -> 신고창으로 이동


    override fun onProgressItemSelected(postDto : PostDto) {
        val fragment = ProgressDetailFragment.getInstance(postDto)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
            .addToBackStack(null).replace(R.id.fragment_container, fragment).commit()
    }   //ProgressHomeFragment Callback method -> ProgressDetailFragment()

    override fun onReportSelected() {
        val fragment = ReportAdminFragment.getInstance()
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
            .addToBackStack(null).replace(R.id.fragment_container, fragment).commit()
    }   //ReportHomeFragment Callback method

    override fun onAccuseSelected() {
        val fragment = AccuseUserFragment.getInstance()
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
            .addToBackStack(null).replace(R.id.fragment_container, fragment).commit()
    }   //ReportHomeFragment Callback method

}


