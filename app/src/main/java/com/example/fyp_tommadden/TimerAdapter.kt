package com.example.fyp_tommadden

//import androidx.recyclerview.widget.RecyclerView
//
//
//private lateinit var recyclerView: RecyclerView
//private lateinit var adapter: TimerAdapter
//
//// Initialize the RecyclerView and its adapter in your onCreate() or onCreateView() method
//recyclerView = findViewById(R.id.activity_total) // Replace with the ID of your RecyclerView in XML layout
//adapter = TimerAdapter()
//recyclerView.adapter = adapter
//recyclerView.layoutManager = LinearLayoutManager(this) // Replace with the appropriate layout manager
//
//// Implement the TimerAdapter class for your RecyclerView
//class TimerAdapter : RecyclerView.Adapter<TimerAdapter.TimerViewHolder>() {
//
//    private val data = ArrayList<Pair<String, Int>>()
//
//    // Update the data in the adapter
//    fun updateData(newData: List<Pair<String, Int>>) {
//        data.clear()
//        data.addAll(newData)
//        notifyDataSetChanged()
//    }
//
//    // Inflate the view holder layout
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_timer, parent, false) // Replace with your item layout
//        return TimerViewHolder(view)
//    }
//
//    // Bind the data to the view holder
//    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
//        val item = data[position]
//        holder.bind(item)
//    }
//
//    // Return the number of items in the data
//    override fun getItemCount(): Int {
//        return data.size
//    }
//
//    // Define the view holder class
//    class TimerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView) // Replace with the ID of your date TextView in item layout
//        private val valueTextView: TextView = itemView.findViewById(R.id.valueTextView) // Replace with the ID of your value TextView in item layout
//
//        // Bind the data to the view holder
//        fun bind(item: Pair<String, Int>) {
//            val date = item.first
//            val value
