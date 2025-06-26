package com.example.androidappfrontend.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidappfrontend.R
import com.example.androidappfrontend.model.Habit
import com.example.androidappfrontend.adapter.HabitAdapter
import com.example.androidappfrontend.adapter.OnHabitActionListener
import com.example.androidappfrontend.storage.HabitStorage
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

/**
 * Fragment for displaying and managing the list of habits.
 */
class HabitListFragment : Fragment(), OnHabitActionListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HabitAdapter
    private lateinit var addFab: FloatingActionButton
    private var habits: MutableList<Habit> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_habit_list, container, false)
        recyclerView = v.findViewById(R.id.recyclerHabits)
        addFab = v.findViewById(R.id.fab_add_habit)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        habits = HabitStorage.loadHabits(requireContext())
        adapter = HabitAdapter(habits, this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        addFab.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HabitEditFragment.newInstance(null, -1))
                .addToBackStack(null)
                .commit()
        }
        setHasOptionsMenu(false)
    }

    override fun onResume() {
        super.onResume()
        habits = HabitStorage.loadHabits(requireContext())
        // Defensive: Always clear and update the reference before passing to adapter
        adapter.updateList(habits)
    }

    override fun onEdit(habit: Habit, position: Int) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HabitEditFragment.newInstance(habit, position))
            .addToBackStack(null)
            .commit()
    }

    override fun onDelete(habit: Habit, position: Int) {
        if (position >= 0 && position < habits.size) {
            habits.removeAt(position)
            HabitStorage.saveHabits(requireContext(), habits)
            adapter.updateList(habits)
            Snackbar.make(requireView(), "Habit deleted", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(requireView(), "Error: Cannot delete habit", Snackbar.LENGTH_SHORT).show()
        }
    }
}
