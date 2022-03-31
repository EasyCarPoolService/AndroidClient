package com.example.easycarpoolapp.navigation.progress

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easycarpoolapp.R
import com.example.easycarpoolapp.fragment.calendar.CalendarHomeFragment
import com.example.easycarpoolapp.fragment.calendar.CalendarHomeFragment.CalendarAdapter
import com.example.easycarpoolapp.fragment.calendar.CalendarRepository
import com.example.easycarpoolapp.fragment.chat.RequestDriverDialogFragment
import com.example.easycarpoolapp.fragment.chat.RequestPassengerDialogFragment
import com.example.easycarpoolapp.fragment.chat.dto.ReservedPostDto
import com.example.easycarpoolapp.fragment.post.dto.PostDto
import javax.security.auth.callback.Callback

class ProgressHomeFragment : Fragment(){


    interface Callbacks{
        public fun onProgressItemSelected(posDto : PostDto)
    }

    companion object{
        fun getInstance() : ProgressHomeFragment = ProgressHomeFragment()
    }

    private lateinit var binding : com.example.easycarpoolapp.databinding.FragmentProgressHomeBinding
    private var callbacks : Callbacks? = null
    private val viewModel : ProgressHomeViewModel by lazy {
        ViewModelProvider(this).get(ProgressHomeViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }   //onAttach()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CalendarRepository.init(requireContext())
    }//onCreate()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_progress_home, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        //binding.recyclerView.adapter = ProgressAdapter(ArrayList<ReservedPostDto>())
        binding.recyclerView.adapter = ProgressAdapter(ArrayList<ReservedPostDto>())
        return binding.root
    }// onCreateView()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPostData() //reserved Post  조회하기

        viewModel.postItems.observe(viewLifecycleOwner, Observer {
            updateUI(it)    //reservedPostDto조회 -> recyclerView setting
        }) //observer
        viewModel.itemDetail.observe(viewLifecycleOwner, Observer {

            //reservedPostDto 에 type필요
            callbacks?.onProgressItemSelected(it)
            viewModel.itemDetail = MutableLiveData()

            /*if(it.type.equals("driver")){   //타세요 게시글
            }else{  //태워주세요 게시글
            }*/
        })


    }// onViewCreated()

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }//onDetach()



    private fun updateUI(items : ArrayList<ReservedPostDto>){
        binding.recyclerView.adapter = ProgressAdapter(items)
    } //updateUI()


    //==========================================================================================

    inner class ProgressHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var reservedPostDto : ReservedPostDto? = null
        val itemLayout : LinearLayout = itemView.findViewById(R.id.item_calendar_layout)
        val date : TextView = itemView.findViewById(R.id.item_calendar_date)
        val time : TextView = itemView.findViewById(R.id.item_calendar_time)
        val driver : TextView = itemView.findViewById(R.id.item_calendar_driver)
        val passenger : TextView = itemView.findViewById(R.id.item_calendar_passenger)

        public fun bind(item : ReservedPostDto){
            reservedPostDto = item
            date.text = date.text.toString() + item.date
            time.text = time.text.toString() + item.time
            driver.text = driver.text.toString() + item.driver
            passenger.text = passenger.text.toString() + item.passenger
        }//bind()

        init{
            itemLayout.setOnClickListener {
                viewModel.getItemDetail(reservedPostDto!!)
            }
        }//init
    }


    inner class ProgressAdapter(val items : ArrayList<ReservedPostDto>) : RecyclerView.Adapter<ProgressHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressHolder {
            val view = layoutInflater.inflate(R.layout.item_calendar_layout, parent, false)
            return ProgressHolder(view)
        }

        override fun onBindViewHolder(holder: ProgressHolder, position: Int) {
            holder.bind(items.get(position))
        }

        override fun getItemCount(): Int = items.size
    }

}