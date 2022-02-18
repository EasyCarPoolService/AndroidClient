package com.example.easycarpoolapp.fragment.post

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.easycarpoolapp.LocalUserData
import com.example.easycarpoolapp.NetworkConfig
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.databinding.FragmentPostHomeBinding
import com.example.easycarpoolapp.fragment.LoginDialogFragment
import com.example.easycarpoolapp.fragment.post.dto.PostDriverDto
import com.example.easycarpoolapp.fragment.post.dto.PostDto
import com.example.easycarpoolapp.fragment.post.dto.PostPassengerDto
import com.example.easycarpoolapp.fragment.post.dto.UserPostDto

import com.sothree.slidinguppanel.SlidingUpPanelLayout


class PostHomeFragment : Fragment() {

    interface CallBacks{
        public fun onAddPassengerSelected()
        public fun onAddDriverSelected()
        public fun onPassengerPostSelected(item : PostDto)
        public fun onDriverPostSelected(item : PostDto)
    }

    companion object{
        public fun getInstance() : PostHomeFragment{
            return PostHomeFragment()
        }
    }

    private var callbacks : PostHomeFragment.CallBacks? = null
    private lateinit var binding : FragmentPostHomeBinding
    private val viewModel : PostHomeViewModel by lazy {
        ViewModelProvider(this).get(PostHomeViewModel::class.java)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as CallBacks?
    }//onAttach


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PostRepository.init(requireContext())
        viewModel.getDriverAuth()

    }//onCreat()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_home, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textNickname.text = LocalUserData.getNickname().toString()

        setImageBtnProfile()
        viewModel.getUserPostData() //User가 작성한 게시글 혹은 진행중 게시글 정보 조회 -> 레이아웃 상단에 띄우기



        binding.imageBtnProfile.setOnClickListener{
            Toast.makeText(requireContext(), "imageView clicked", Toast.LENGTH_SHORT).show()
        }


        binding.btnAdd.setOnClickListener {
            val state = binding.slideLayout.panelState
            // 닫힌 상태일 경우 열기
            if (state == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                binding.slideLayout.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            }
            // 열린 상태일 경우 닫기
            else if (state == SlidingUpPanelLayout.PanelState.EXPANDED) {
                binding.slideLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            }
        }


        //driver post 작성하기
        binding.btnAddDriver.setOnClickListener {
            // 차량 등록 여부 판단 -> 미등록(차량등록 다이어로그 메시지 띄우기) / 등록(등록창으로 이동)
            //server로 부터 인증받아 LocalUserData Update수행

            //viewModel.getDriverAuth()

            if(LocalUserData.getDriverAuthentication()!=null&&LocalUserData.getDriverAuthentication() == true){ //운전자 등록이 되어있는 경우
                callbacks?.onAddDriverSelected()
            }else{ //운전자 등록이 되어있지 않은 경우
                RegisterCarDialogFragment().show(requireActivity().supportFragmentManager, "RegisterCarDialog")
            }

            //callbacks?.onAddDriverSelected()
        }

        //passenger 게시글 불러오기
        binding.btnPassengerPost.setOnClickListener {
            setButtonEffect(it as Button)
            viewModel.getPassengerPost()
        }//btnPassengerPost

        binding.btnDriverPost.setOnClickListener {
            setButtonEffect(it as Button)
            viewModel.getDriverPost()
        }//btnDriverPost


        binding.btnUserPost.setOnClickListener {
            setButtonEffect(it as Button)
            viewModel.getUserPost()
        }


        binding.btnAddPassenger.setOnClickListener {
            callbacks?.onAddPassengerSelected()
        }

        // 태워주세요 혹은 타세요 게시글 갱신
        viewModel.postItems.observe(viewLifecycleOwner, Observer {
            updatePost(it)
        })

        //user가 작성한 게시글 혹은 진행중 정보 갱신
        viewModel.userPostDto.observe(viewLifecycleOwner, Observer {
            updateUserPostData(it)
        })



    }// onViewCreated

    //==========================================================================================
    override fun onDetach() {
        super.onDetach()
        callbacks = null
        PostRepository.onDestroy()
    }


    //==========================================================================================
    private fun setImageBtnProfile() {
        Glide.with(this)
            .load("http://"+NetworkConfig.getIP()+":8080/api/image/profile?email="+LocalUserData.getEmail())
            .into(binding.imageBtnProfile)


    }//setImageBtnProfile()


    //==========================================================================================
    //현재 User가 작성한 혹은 진행중 게시글 갱신
    private fun updateUserPostData(userPostDto: UserPostDto){
        binding.userPostDriver.text = userPostDto.driver
        binding.userPostPassenger.text = userPostDto.passenger
        binding.userPostOngoing.text = userPostDto.ongoing
    }

    //==========================================================================================
    private fun setButtonEffect(selectedButton : Button){
        binding.btnDriverPost.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_color))
        binding.btnPassengerPost.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_color))
        binding.btnUserPost.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray_color))
        selectedButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_color))
    }



    //==========================================================================================
    // 타세요 혹은 태워주세요 게시글 갱신
    private fun updatePost(items : ArrayList<PostDto>){
        binding.recyclerView.adapter = PostAdapter(items = items)
    }

    //==========================================================================================
    // 임시로 PassengerPost로 item 구성

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        lateinit var item : PostDto
        val text_departure : TextView = itemView.findViewById(R.id.text_departure)
        val text_destination : TextView = itemView.findViewById(R.id.text_destination)
        val text_date : TextView = itemView.findViewById(R.id.text_date)
        val text_nickname : TextView = itemView.findViewById(R.id.text_nickname)
        val text_gender : TextView = itemView.findViewById(R.id.text_gender)
        val text_message : TextView = itemView.findViewById(R.id.text_message)
        var imageView : ImageView = itemView.findViewById(R.id.item_post_profile)

        init {
            itemView.setOnClickListener {
                if(item.type.equals("driver")){ //타세요 게시글의 경우
                    callbacks!!.onDriverPostSelected(item)
                }else{  //태워주세요 게시글의 경우
                    callbacks!!.onPassengerPostSelected(item)
                }


            }
        }//init

        //==========================================================================================

        public fun bind(item : PostDto){
            this.item = item

            text_departure.text = text_departure.text.toString()+item.departure
            text_destination.text = text_destination.text.toString()+item.destination
            text_date.text = "날짜 : " + item.departureDate + " 시간 : " + item.departureTime
            text_nickname.text = text_nickname.text.toString() + item.nickname
            text_message.text = item.message
            if(item.gender.equals("male")){
                text_gender.text = "성별 : 남"
            }else{
                text_gender.text = "성별 : 여"
            }
            Glide.with(this@PostHomeFragment)
                .load("http://"+NetworkConfig.getIP()+":8080/api/image/profile?email="+item.email)
                .into(imageView)

        }//bind

    }
        //==========================================================================================

    inner class PostAdapter(val items : ArrayList<PostDto>) : RecyclerView.Adapter<PostViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
            val view = layoutInflater.inflate(R.layout.post_item_layout, parent, false)
            return PostViewHolder(view)
        }

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            holder.bind(items.get(position))
        }

        override fun getItemCount(): Int {
            return items.size
        }

    }
}