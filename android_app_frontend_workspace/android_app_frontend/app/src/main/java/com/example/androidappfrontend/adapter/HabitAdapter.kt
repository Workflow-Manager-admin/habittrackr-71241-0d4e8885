package com.example.androidappfrontend.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidappfrontend.R
import com.example.androidappfrontend.model.Habit

// Listener for item actions
interface OnHabitActionListener {
    fun onEdit(habit: Habit, position: Int)
    fun onDelete(habit: Habit, position: Int)
}

class HabitAdapter(
    private var habits: List<Habit>,
    private val actionListener: OnHabitActionListener
) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    inner class HabitViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.habit_name)
        val descView: TextView = view.findViewById(R.id.habit_desc)
        val editBtn: ImageButton = view.findViewById(R.id.btn_edit)
        val deleteBtn: ImageButton = view.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_habit, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits[position]
        holder.nameView.text = habit.name
        holder.descView.text = habit.description

        holder.editBtn.setOnClickListener { actionListener.onEdit(habit, position) }
        holder.deleteBtn.setOnClickListener { actionListener.onDelete(habit, position) }
    }

    override fun getItemCount() = habits.size

    // PUBLIC_INTERFACE
    fun updateList(newHabits: List<Habit>) {
        habits = newHabits
        notifyDataSetChanged()
    }
}
